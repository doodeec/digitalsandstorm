package com.doodeec.digitalsandstorm.network.response;

import android.support.annotation.Nullable;

import com.doodeec.digitalsandstorm.model.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for server response with list of recipes
 * {@link com.doodeec.digitalsandstorm.model.Recipe}
 *
 * @author Dusan Bartos
 */
public class RecipeListResponse {

    @Expose
    @SerializedName("data")
    ResponseData mData = new ResponseData();

    @SuppressWarnings("unused")
    public RecipeListResponse() {
        // empty method used in GSON deserialization
    }

    /**
     * Helper constructor for filling mock request
     * TODO remove when not using mock REST service
     */
    public RecipeListResponse(Recipe[] recipes) {
        mData.mRecipes = recipes;
    }

    @Nullable
    public Recipe[] getRecipes() {
        if (mData == null) return null;
        return mData.mRecipes;
    }

    private class ResponseData {
        @Expose
        @SerializedName("recipes")
        Recipe[] mRecipes;
    }
}
