package com.gabilheri.ilovemarshmallow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/15.
 */
public class MarshmallowDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "com.gabilheri.marshmallow.db";
    public static final int DB_VERSION = 1;

    public MarshmallowDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSearchItemTable());
        db.execSQL(createAutocompleteTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + DataContract.SearchResultEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DataContract.AutoCompleteEntry.TABLE_NAME + ";");
    }

    /**
     * Creates the AutoComplete table that will hold therms the user has already searched for
     *
     * I taught about using the Zappos Auto complete API from api.zappos.com but since we were only giving
     * 2 endpoints to work with.. I was not sure if I was able or not. (Didn't want to get disqualified for using
     * other endpoints)
     *
     * @return
     *      The SQL string with the statement to create the table
     */
    private static String createAutocompleteTable() {
        return "CREATE TABLE " + DataContract.AutoCompleteEntry.TABLE_NAME + "(" +
                DataContract.AutoCompleteEntry.SEARCH_TERM + " TEXT NOT NULL, " +
                "UNIQUE (" + DataContract.AutoCompleteEntry.SEARCH_TERM + ") ON CONFLICT REPLACE);";
    }

    /**
     * Creates the table that will hold the items favorite by the user
     *
     * @return
     *      The SQL string with the statement to create the table
     */
    private static String createSearchItemTable() {
        return "CREATE TABLE " + DataContract.SearchResultEntry.TABLE_NAME + "(" +
                DataContract.SearchResultEntry.BRAND_NAME + " TEXT, " +
                DataContract.SearchResultEntry.ORIGINAL_PRICE + " TEXT, " +
                DataContract.SearchResultEntry.PRICE + " TEXT, " +
                DataContract.SearchResultEntry.IMAGE_URL + " TEXT, " +
                DataContract.SearchResultEntry.ASIN + " TEXT NOT NULL, " +
                DataContract.SearchResultEntry.PRODUCT_URL + " TEXT, " +
                DataContract.SearchResultEntry.PRODUCT_RATING + " REAL, " +
                DataContract.SearchResultEntry.PRODUCT_NAME + " TEXT, " +
                "UNIQUE (" + DataContract.SearchResultEntry.ASIN + ") ON CONFLICT REPLACE);";
    }


}
