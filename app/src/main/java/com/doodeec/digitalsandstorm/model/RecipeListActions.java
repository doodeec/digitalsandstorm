package com.doodeec.digitalsandstorm.model;

import com.doodeec.digitalsandstorm.fragment.RecipeListFragment;

/**
 * Interface model for communication actions between
 * {@link com.doodeec.digitalsandstorm.activity.RecipeListActivity}
 * and
 * {@link RecipeListFragment}
 *
 * @author Dusan Bartos
 */
public interface RecipeListActions {
    /**
     * Called when item is clicked in recipe list
     *
     * @param recipe recipe
     */
    void openRecipeDetail(Recipe recipe);

    /**
     * Called when recipe list is swiped down (swipe to refresh pattern)
     */
    void refreshRecipeList();

    /**
     * Called when item is long-tapped in recipe list
     *
     * @param recipe recipe
     */
    void showRecipeDescription(Recipe recipe);
}