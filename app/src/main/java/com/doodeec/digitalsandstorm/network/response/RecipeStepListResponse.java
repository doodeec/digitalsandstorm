package com.doodeec.digitalsandstorm.network.response;

import android.support.annotation.Nullable;

import com.doodeec.digitalsandstorm.model.RecipeStep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for server response with list of recipe steps
 * {@link com.doodeec.digitalsandstorm.model.RecipeStep}
 *
 * @author Dusan Bartos
 */
public class RecipeStepListResponse {

    @Expose
    @SerializedName("data")
    ResponseData mData = new ResponseData();

    @SuppressWarnings("unused")
    public RecipeStepListResponse() {
        // empty method used in GSON deserialization
    }

    /**
     * Helper constructor for filling mock request
     * TODO remove when not using mock REST service
     */
    public RecipeStepListResponse(RecipeStep[] recipeSteps) {
        mData.mRecipeSteps = recipeSteps;
    }

    @Nullable
    public RecipeStep[] getRecipeSteps() {
        if (mData == null) return null;
        return mData.mRecipeSteps;
    }

    private class ResponseData {
        @Expose
        @SerializedName("recipe_steps")
        RecipeStep[] mRecipeSteps;
    }
}
