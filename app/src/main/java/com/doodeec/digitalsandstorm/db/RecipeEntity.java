package com.doodeec.digitalsandstorm.db;

/**
 * Recipe related fields in the context of a database
 * @author Dusan Bartos
 */
public class RecipeEntity {

    public static final String TABLE_NAME = "recipes";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_TITLE_SORTING = "title_for_sorting";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_OTHER = "other";
    public static final String COL_TIME_IN_OVEN = "time_in_oven";
    public static final String COL_TEMPERATURE = "temperature";
    public static final String COL_IMG_PORT_PATH = "image_portrait_path";
    public static final String COL_IMG_LAND_PATH = "image_landscape_path";
    public static final String COL_IMG_PORT_FILE = "image_portrait_file";
    public static final String COL_IMG_LAND_FILE = "image_landscape_file";
    public static final String COL_ACTIVE = "active";
    //TODO store category id

    // initialization sql script which will create a DB table
    public static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_TITLE + " TEXT, " +
            COL_TITLE_SORTING + " TEXT, " +
            COL_DESCRIPTION + " TEXT, " +
            COL_OTHER + " TEXT, " +
            COL_TIME_IN_OVEN + " NUMERIC, " +
            COL_TEMPERATURE + " NUMERIC, " +
            COL_IMG_PORT_PATH + " TEXT, " +
            COL_IMG_LAND_PATH + " TEXT, " +
            COL_IMG_PORT_FILE + " TEXT, " +
            COL_IMG_LAND_FILE + " TEXT, " +

            COL_ACTIVE + " INTEGER" +
            ")";
}
