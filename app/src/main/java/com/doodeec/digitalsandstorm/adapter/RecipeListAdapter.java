package com.doodeec.digitalsandstorm.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.digitalsandstorm.DigitalSandstormApp;
import com.doodeec.digitalsandstorm.R;
import com.doodeec.digitalsandstorm.model.Recipe;
import com.doodeec.digitalsandstorm.view.RecipeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for showing a list of recipes
 *
 * @author Dusan Bartos
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context mContext;
    private List<Recipe> mItems;

    public RecipeListAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.view_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mItems.get(position);

        holder.setTitle(recipe.getTitle());
        holder.setImage(recipe.getImageSrc(DigitalSandstormApp.isPortrait(mContext)));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Nullable
    public Recipe getItem(int position) {
        if (position < 0 || position >= mItems.size()) {
            Log.w("RecipeListAdapter", "invalid adapter position");
            return null;
        }

        return mItems.get(position);
    }

    public void setRecipes(List<Recipe> recipes) {
        mItems = recipes;
        notifyDataSetChanged();
    }
}
