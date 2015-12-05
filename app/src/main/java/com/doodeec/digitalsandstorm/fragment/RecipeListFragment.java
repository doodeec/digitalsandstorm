package com.doodeec.digitalsandstorm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.digitalsandstorm.DigitalSandstormApp;
import com.doodeec.digitalsandstorm.R;
import com.doodeec.digitalsandstorm.SyncObservable;
import com.doodeec.digitalsandstorm.adapter.RecipeListAdapter;
import com.doodeec.digitalsandstorm.model.Recipe;
import com.doodeec.digitalsandstorm.model.RecipeListActions;
import com.doodeec.digitalsandstorm.view.GridSpacesItemDecoration;
import com.doodeec.digitalsandstorm.view.RecyclerItemClickListener;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

/**
 * Fragment showing a list of available recipes
 */
public class RecipeListFragment extends Fragment implements
        RecyclerItemClickListener.IOnItemClickListener,
        Observer {

    private static final String TAG = "RecListFragment";

    @Bind(R.id.recipe_list)
    RecyclerView mRecipeList;
    @Bind(R.id.swipe_refresh_recipe_list)
    SwipeRefreshLayout mSwipeRefreshView;

    private RecipeListActions mListener;
    private RecipeListAdapter mAdapter;
    private boolean mResultHandled = false;

    private final SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.d(TAG, "refreshing recipes");
            mListener.refreshRecipeList();
            mSwipeRefreshView.setOnRefreshListener(mRefreshListener);
        }
    };

    public RecipeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new RecipeListAdapter(getContext());

        // use more columns in landscape
        int columns = DigitalSandstormApp.isPortrait(getContext()) ? 2 : 3;

        mRecipeList.setLayoutManager(
                new GridLayoutManager(getContext(), columns));
        mRecipeList.addItemDecoration(new GridSpacesItemDecoration(16, columns));
        mRecipeList.setHasFixedSize(true);
        mRecipeList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));
        mRecipeList.setAdapter(mAdapter);

        mSwipeRefreshView.setOnRefreshListener(mRefreshListener);
        mSwipeRefreshView.setRefreshing(true);

        //noinspection unchecked
        Query.all(Recipe.class).getAsync(getLoaderManager(), new ManyQuery.ResultHandler<Recipe>() {
            @Override
            public boolean handleResult(CursorList<Recipe> result) {
                List<Recipe> recipeList = result.asList();

                Log.d(TAG, "update RecipeList " + recipeList.size());
                mAdapter.setRecipes(recipeList);
                mSwipeRefreshView.setRefreshing(false);
                result.close();

                // if this is the first time and there are no stored recipes in DB,
                // kickoff initial refresh automatically
                if (!mResultHandled && recipeList.size() == 0) {
                    mRefreshListener.onRefresh();
                }

                mResultHandled = true;
                // return true for monitoring future changes in dataset
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DigitalSandstormApp) getActivity().getApplication()).getSyncObservable().addObserver(this);
    }

    @Override
    public void onPause() {
        ((DigitalSandstormApp) getActivity().getApplication()).getSyncObservable().deleteObserver(this);
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (RecipeListActions) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException(context.getClass().getSimpleName()
                    + " must implement RecipeListActions");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof SyncObservable) {
            boolean syncing = ((SyncObservable) observable).isSyncing();
            mSwipeRefreshView.setRefreshing(syncing);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Recipe recipe = mAdapter.getItem(position);
        if (recipe != null) {
            mListener.openRecipeDetail(recipe);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Recipe recipe = mAdapter.getItem(position);
        if (recipe != null) {
            mListener.showRecipeDescription(recipe);
        }
    }
}
