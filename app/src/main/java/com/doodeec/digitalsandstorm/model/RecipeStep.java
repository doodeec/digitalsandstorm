package com.doodeec.digitalsandstorm.model;

import android.support.annotation.NonNull;

import com.doodeec.digitalsandstorm.db.RecipeStepEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Model object for "Recipe Step" entity
 * Defines a single step of a certain recipe
 *
 * Each step has a specific description,
 * position in recipe and
 * GUID of the recipe it is linked to
 *
 * @author Dusan Bartos
 */
@Table(RecipeStepEntity.TABLE_NAME)
public class RecipeStep extends Model implements Comparable<RecipeStep> {

    @Key
    @Column(RecipeStepEntity.COL_ID)
    @Expose
    @SerializedName("id")
    long mId;

    // order number in the context of corresponding recipe
    @Column(RecipeStepEntity.COL_POSITION)
    @Expose
    @SerializedName("position")
    int mPosition;

    @Column(RecipeStepEntity.COL_DESCRIPTION)
    @Expose
    @SerializedName("description")
    String mDescription;

    @Column(RecipeStepEntity.COL_RECIPE_ID)
    @Expose
    @SerializedName("recipe_id")
    long mRecipeId;

    @SuppressWarnings("unused")
    public RecipeStep() {
        // empty method used in GSON deserialization
    }

    // constructor used specifically for Mocking REST service
    public RecipeStep(
            long id,
            String description,
            int position,
            long recipeId) {
        mId = id;
        mDescription = description;
        mPosition = position;
        mRecipeId = recipeId;
    }

    @Override
    public int compareTo(@NonNull RecipeStep another) {
        //Integer.compare is available only for API19+ so this is just a simple copy-paste
        //to make it available for older APIs
        return mPosition < another.mPosition ? -1 : (mPosition == another.mPosition ? 0 : 1);
    }

    public long getId() {
        return mId;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getDescription() {
        return mDescription;
    }
}
