package com.doodeec.digitalsandstorm.network;

import com.doodeec.digitalsandstorm.DigitalSandstormConfig;
import com.doodeec.digitalsandstorm.model.Recipe;
import com.doodeec.digitalsandstorm.model.RecipeStep;
import com.doodeec.digitalsandstorm.network.response.RecipeListResponse;
import com.doodeec.digitalsandstorm.network.response.RecipeStepListResponse;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.mock.Calls;

/**
 * Mocked REST service
 *
 * @author Dusan Bartos
 */
public final class MockRestService implements RestService {

    private Retrofit mRetrofit;

    public MockRestService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(DigitalSandstormConfig.REST_URL)
                .build();
    }

    @Override
    public Call<RecipeListResponse> getRecipeList() {
        Recipe[] recipes = new Recipe[7];
        recipes[0] = new Recipe(213, "Recept 1", "Recept na polievku");
        recipes[1] = new Recipe(22, "Recept 2", "Recept na puding");
        recipes[2] = new Recipe(563, "Recept 3", "Recept na veceru");
        recipes[3] = new Recipe(44, "Recept 4", "Recept na obed");
        recipes[4] = new Recipe(21, "Recept 5", "Recept na ranajky");
        recipes[5] = new Recipe(25, "Recept 6", "Recept na omacku");
        recipes[6] = new Recipe(75, "Recept 7", "Recept na prazenicu");

        RecipeListResponse response = new RecipeListResponse(recipes);
        return Calls.response(response, mRetrofit);
    }

    @Override
    public Call<RecipeStepListResponse> getRecipeStepsList() {
        RecipeStep[] recipeSteps = new RecipeStep[5];
        recipeSteps[0] = new RecipeStep(19, "Popis 1", 0, 213);
        recipeSteps[1] = new RecipeStep(54, "Popis 2", 1, 213);
        recipeSteps[2] = new RecipeStep(32, "Dalsi popis 1", 0, 44);
        recipeSteps[3] = new RecipeStep(55, "Dalsi popis 2", 1, 44);
        recipeSteps[4] = new RecipeStep(2, "Dalsi popis 3", 2, 44);

        RecipeStepListResponse response = new RecipeStepListResponse(recipeSteps);
        return Calls.response(response, mRetrofit);
    }
}
