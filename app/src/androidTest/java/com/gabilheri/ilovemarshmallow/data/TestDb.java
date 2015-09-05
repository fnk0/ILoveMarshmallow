/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabilheri.ilovemarshmallow.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

import static com.gabilheri.ilovemarshmallow.data.DataContract.SearchResultEntry;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(MarshmallowDbHelper.DB_NAME);
    }

    /**
        This function gets called before each test is executed to delete the database.
        This makes sure that the tests always have a clean test.
     */
    @Override
    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {

        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(DataContract.SearchResultEntry.TABLE_NAME);
        tableNameHashSet.add(DataContract.AutoCompleteEntry.TABLE_NAME);
        mContext.deleteDatabase(MarshmallowDbHelper.DB_NAME);
        SQLiteDatabase db = new MarshmallowDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // Are the desirable tables created?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        assertTrue("Error: Database was created without both the auto complete entry and the search result entry tables", tableNameHashSet.isEmpty());
        c.close();
        // now, do the tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + DataContract.SearchResultEntry.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the database for table information.", c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> searchResultItemHasHSet = new HashSet<>();
        searchResultItemHasHSet.add(SearchResultEntry.BRAND_NAME);
        searchResultItemHasHSet.add(SearchResultEntry.ORIGINAL_PRICE);
        searchResultItemHasHSet.add(SearchResultEntry.PRODUCT_NAME);
        searchResultItemHasHSet.add(SearchResultEntry.PRICE);
        searchResultItemHasHSet.add(SearchResultEntry.IMAGE_URL);
        searchResultItemHasHSet.add(SearchResultEntry.ASIN);
        searchResultItemHasHSet.add(SearchResultEntry.PRODUCT_URL);
        searchResultItemHasHSet.add(SearchResultEntry.PRODUCT_RATING);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            searchResultItemHasHSet.remove(columnName);
        } while(c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required search result entry columns", searchResultItemHasHSet.isEmpty());
        c.close();
        db.close();
    }

    public void testSearchResultTable() {
        SQLiteDatabase db = new MarshmallowDbHelper(mContext).getWritableDatabase();

        ContentValues itemValues = TestUtilities.createSearchResultItem();

        long rowID = db.insert(SearchResultEntry.TABLE_NAME, null, itemValues);
        assertTrue(rowID != -1);

        Cursor itemCursor = db.query(
                SearchResultEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue("Error: could not retrieve the return item from SearchResultItems query", itemCursor.moveToFirst());

        TestUtilities.validateCurrentRecord("testingRead the search result db failed", itemCursor, itemValues);

        itemCursor.close();
    }


}
