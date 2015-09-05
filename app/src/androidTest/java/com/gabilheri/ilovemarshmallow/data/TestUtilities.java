package com.gabilheri.ilovemarshmallow.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;

import java.util.Map;
import java.util.Set;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/5/15.
 */
public class TestUtilities extends AndroidTestCase {

    public static final String TEST_ASIN = "B00WRI1M5K";

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createSearchResultItem() {
        SearchResultItem item = new SearchResultItem();
        item.setBrandName("Kenneth Cole Reaction")
                .setOriginalPrice(TEST_ASIN)
                .setPrice("$119.00")
                .setImageUrl("http://ecx.images-amazon.com/images/I/517u8jMirIL._AA160_.jpg")
                .setAsin("B00WRI1M5K")
                .setProductRating(1.5f)
                .setProductUrl("/Kenneth-Cole-Reaction-Womens-Frida/dp/B00WRI1M5K/ref=sr_1_101?m=AMWRKCC3EWKYM&s=zappos&ie=UTF8&qid=1441490286&sr=1-101&keywords=boots")
                .setProductName("Kenneth Cole Reaction Frida World");

        return SearchResultItem.toContentValues(item);
    }

    static ContentValues createAutoCompleteValue() {
        ContentValues values = new ContentValues();
        values.put(DataContract.AutoCompleteEntry.SEARCH_TERM, "boots");
        return values;
    }
}
