package com.doodeec.digitalsandstorm.db;

/**
 * RecipeStep related fields in the context of a database
 *
 * @author Dusan Bartos
 */
public class RecipeStepEntity {

    public static final String TABLE_NAME = "recipe_steps";

    public static final String COL_ID = "id";
    public static final String COL_POSITION = "position";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_RECIPE_ID = "recipe_id";

    // initialization sql script which will create a DB table
    public static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_POSITION + " INTEGER, " +
            COL_DESCRIPTION + " TEXT, " +
            COL_RECIPE_ID + " INTEGER" +
            ")";

    public static final String QUERY_STEPS = "SELECT * FROM " + TABLE_NAME
            + " WHERE " + COL_RECIPE_ID + " = ? ORDER BY " + COL_POSITION + " ASC";
}
