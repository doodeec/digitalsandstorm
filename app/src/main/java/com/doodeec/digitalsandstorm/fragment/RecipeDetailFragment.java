package com.doodeec.digitalsandstorm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doodeec.digitalsandstorm.R;
import com.doodeec.digitalsandstorm.activity.RecipeDetailActivity;
import com.doodeec.digitalsandstorm.db.RecipeStepEntity;
import com.doodeec.digitalsandstorm.model.Recipe;
import com.doodeec.digitalsandstorm.model.RecipeStep;
import com.doodeec.digitalsandstorm.view.RecipeStepView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailFragment extends Fragment implements
        ManyQuery.ResultHandler<RecipeStep> {

    private static final String TAG = "RecipeDetailFragment";

    @Bind(R.id.recipe_detail_description)
    TextView mRecipeDescription;
    @Bind(R.id.recipe_detail_steps)
    LinearLayout mRecipeStepsLayout;

    public RecipeDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (getActivity().getIntent().hasExtra(RecipeDetailActivity.EXTRA_RECIPE)) {
            Recipe recipe = getActivity().getIntent().getParcelableExtra(RecipeDetailActivity.EXTRA_RECIPE);
            setRecipe(recipe);
        }
    }

    @Override
    public boolean handleResult(CursorList<RecipeStep> result) {
        List<RecipeStep> recipeSteps = result.asList();
        Log.d(TAG, "update RecipeSteps " + recipeSteps.size());
        invalidateSteps(recipeSteps);
        result.close();
        return false;
    }

    private void setRecipe(Recipe recipe) {
        mRecipeDescription.setText(recipe.getDescription());

        // load recipe steps
        //noinspection unchecked
        Query.many(RecipeStep.class,
                RecipeStepEntity.QUERY_STEPS,
                recipe.getId()
        ).getAsync(getLoaderManager(), this);
    }

    private void invalidateSteps(List<RecipeStep> recipeSteps) {
        mRecipeStepsLayout.removeAllViews();
        for (RecipeStep recipeStep : recipeSteps) {
            RecipeStepView view = new RecipeStepView(getContext());
            mRecipeStepsLayout.addView(view);
            view.setStep(recipeStep);
        }
    }
}
