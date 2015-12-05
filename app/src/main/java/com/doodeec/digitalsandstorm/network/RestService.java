package com.doodeec.digitalsandstorm.network;

import com.doodeec.digitalsandstorm.network.response.RecipeListResponse;
import com.doodeec.digitalsandstorm.network.response.RecipeStepListResponse;

import retrofit.Call;
import retrofit.http.GET;

/**
 * REST interface describing server communication nodes
 *
 * @author Dusan Bartos
 */
public interface RestService {
    /**
     * Request for list of all recipes
     * Returns array of all available recipes
     *
     * @return cancellable call object
     */
    @GET("recipes")
    Call<RecipeListResponse> getRecipeList();

    /**
     * Request for list of all recipe steps
     * Returns array of recipe steps for all recipes
     *
     * @return cancellable call object
     */
    @GET("recipe_steps")
    Call<RecipeStepListResponse> getRecipeStepsList();
}
