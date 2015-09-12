package com.gabilheri.ilovemarshmallow.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.gabilheri.ilovemarshmallow.data.DataContract.SearchResultEntry;
import static com.gabilheri.ilovemarshmallow.data.DataContract.AutoCompleteEntry;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/5/15.
 */
public class MarshmallowProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MarshmallowDbHelper mDbHelper;

    public static final int AUTO_COMPLETE = 100;
    public static final int SEARCH_ALL = 200;
    public static final int SEARCH_ASIN = 201;

    private static final SQLiteQueryBuilder searchQueryBuilder;
    private static final SQLiteQueryBuilder autoCompleteQueryBuilder;

    static {
        searchQueryBuilder = new SQLiteQueryBuilder();
        searchQueryBuilder.setTables(SearchResultEntry.TABLE_NAME);
        autoCompleteQueryBuilder = new SQLiteQueryBuilder();
        autoCompleteQueryBuilder.setTables(AutoCompleteEntry.TABLE_NAME);
    }

    private static final String itemWithAsin = SearchResultEntry.TABLE_NAME + "." + SearchResultEntry.ASIN + " = ?";

    @Override
    public boolean onCreate() {
        mDbHelper = new MarshmallowDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case SEARCH_ALL:
                retCursor = getSearchItems(projection, sortOrder);
                break;

            case SEARCH_ASIN:
                retCursor = getItemWithAsin(uri, projection, sortOrder);
                break;

            case AUTO_COMPLETE:
                retCursor = getAutoCompleteItems(projection, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (getContext() != null) {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case SEARCH_ASIN:
                return SearchResultEntry.CONTENT_ITEM_TYPE;
            case SEARCH_ALL:
                return SearchResultEntry.CONTENT_TYPE;
            case AUTO_COMPLETE:
                return AutoCompleteEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String tableName = getTableNameForURI(uri);
        long _id = db.insert(tableName, null, values);

        if (_id < 0) {
            throw new SQLException("Failed to insert row into " + uri);
        }

        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return insertValuesIntoTable(uri, values);
    }

    private int insertValuesIntoTable(Uri uri, @NonNull ContentValues[] values) {
        String tableName = getTableNameForURI(uri);
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int returnCount = 0;
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                long _id = db.insert(tableName, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        if(getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnCount;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        if (selection == null) {
            if (selectionArgs != null) {
                selection = itemWithAsin;
            } else {
                selection = "1";
            }

        }

        int rowsDeleted = db.delete(getTableNameForURI(uri), selection, selectionArgs);

        if (rowsDeleted != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        if (selection == null) {
            selection = "1";
        }

        int rowsUpdated = db.update(getTableNameForURI(uri), values, selection, selectionArgs);

        if (rowsUpdated != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * Helper method to Match a integer to a URI
     *
     * @return
     *      the matcher object with the association with the integers
     */
    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_SEARCH_RESULT_ITEM, SEARCH_ALL);
        matcher.addURI(authority, DataContract.PATH_SEARCH_RESULT_ITEM + "/*", SEARCH_ASIN);
        matcher.addURI(authority, DataContract.PATH_AUTO_COMPLETE, AUTO_COMPLETE);

        return matcher;
    }

    /**
     * Helper method to get the table name of a specific URI
     * @param uri
     *      The URI
     * @return
     *      The TableName associated with this URI
     */
    public static String getTableNameForURI(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case SEARCH_ALL:
            case SEARCH_ASIN:
                return SearchResultEntry.TABLE_NAME;
            case AUTO_COMPLETE:
                return AutoCompleteEntry.TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private Cursor getSearchItems(String[] projection, String sortOrder) {
        return searchQueryBuilder.query(mDbHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getAutoCompleteItems(String[] projection, String sortOrder) {
        return autoCompleteQueryBuilder.query(mDbHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getItemWithAsin(Uri uri, String[] projection, String sortOrder) {
        String asin = uri.getPathSegments().get(1);
        return searchQueryBuilder.query(mDbHelper.getReadableDatabase(),
                projection,
                itemWithAsin,
                new String[]{asin},
                null,
                null,
                sortOrder
        );
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mDbHelper.close();
        super.shutdown();
    }
}
