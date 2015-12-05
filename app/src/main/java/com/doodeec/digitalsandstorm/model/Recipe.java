package com.doodeec.digitalsandstorm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.doodeec.digitalsandstorm.db.RecipeEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Model object for "Recipe" entity
 * Stores basic information about the recipe
 * - title
 * - description
 * - images
 * - category
 * - difficulty level
 * - other info...
 *
 * @author Dusan Bartos
 */
@Table(RecipeEntity.TABLE_NAME)
public class Recipe extends Model implements
        Parcelable,
        Comparable<Recipe> {

    @Key
    @Column(RecipeEntity.COL_ID)
    @Expose
    @SerializedName("id")
    long mId;

    @Column(RecipeEntity.COL_TITLE)
    @Expose
    @SerializedName("title")
    String mTitle;

    // title used for list sorting
    @Column(RecipeEntity.COL_TITLE_SORTING)
    @Expose
    @SerializedName("title_for_sorting")
    String mSortTitle;

    @Column(RecipeEntity.COL_DESCRIPTION)
    @Expose
    @SerializedName("description")
    String mDescription;

    @Column(RecipeEntity.COL_OTHER)
    @Expose
    @SerializedName("other")
    String mOther;

    @Column(RecipeEntity.COL_TIME_IN_OVEN)
    @Expose
    @SerializedName("time_in_oven")
    Double mTimeInOven;

    @Column(RecipeEntity.COL_TEMPERATURE)
    @Expose
    @SerializedName("temperature")
    Double mTemperature;

    @Column(RecipeEntity.COL_IMG_PORT_PATH)
    @Expose
    @SerializedName("image_portrait_path")
    String mImgPathPortrait;

    @Column(RecipeEntity.COL_IMG_PORT_FILE)
    @Expose
    @SerializedName("image_portrait_file")
    String mImgFilePortrait;

    @Column(RecipeEntity.COL_IMG_LAND_PATH)
    @Expose
    @SerializedName("image_landscape_path")
    String mImgPathLandscape;

    @Column(RecipeEntity.COL_IMG_LAND_FILE)
    @Expose
    @SerializedName("image_landscape_file")
    String mImgFileLandscape;

    @Column(RecipeEntity.COL_ACTIVE)
    @Expose
    @SerializedName("active")
    boolean mActive = false;

    //TODO category

    @SuppressWarnings("unused")
    public Recipe() {
        // empty method used in GSON deserialization
    }

    // constructor used specifically for Mocking REST service
    public Recipe(
            long id,
            String title,
            String description) {
        mId = id;
        mTitle = title;
        mSortTitle = title;
        mDescription = description;
    }

    protected Recipe(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mSortTitle = in.readString();
        mDescription = in.readString();
        mOther = in.readString();
        mImgPathPortrait = in.readString();
        mImgFilePortrait = in.readString();
        mImgPathLandscape = in.readString();
        mImgFileLandscape = in.readString();
        mActive = in.readByte() != 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    @Override
    public int compareTo(@NonNull Recipe another) {
        if (mSortTitle == null || another.mSortTitle == null) return 0;

        return mSortTitle.compareTo(another.mSortTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mSortTitle);
        dest.writeString(mDescription);
        dest.writeString(mOther);
        dest.writeString(mImgPathPortrait);
        dest.writeString(mImgFilePortrait);
        dest.writeString(mImgPathLandscape);
        dest.writeString(mImgFileLandscape);
        dest.writeByte((byte) (mActive ? 1 : 0));
    }

    public long getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageSrc(boolean isPortrait) {
        //not quite sure where to look for images in an absolute path context
        //so using a placeholder
        /*if (isPortrait) {
            return mImgPathPortrait + mImgFilePortrait;
        } else {
            return mImgPathLandscape + mImgFileLandscape;
        }*/
        return "http://uploads2.wikiart.org/images/pablo-picasso/self-portrait-1907.jpg!Blog.jpg";
    }
}
