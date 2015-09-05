package com.gabilheri.ilovemarshmallow.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import static com.gabilheri.ilovemarshmallow.data.DataContract.AutoCompleteEntry;
import static com.gabilheri.ilovemarshmallow.data.DataContract.SearchResultEntry;
/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/5/15.
 */
public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                SearchResultEntry.CONTENT_URI,
                null,
                null
        );

        mContext.getContentResolver().delete(
                AutoCompleteEntry.CONTENT_URI,
                null,
                null
        );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecordsFromProvider();
    }

    /**
        This test checks to make sure that the content provider is registered correctly.
     */
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(), MarshmallowProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: MarshmallowProvider failed to register with authority: " + providerInfo.authority +
                            " instead of authority: " + DataContract.CONTENT_AUTHORITY,
                    providerInfo.authority, DataContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: MarshmallowProvider not registered at " + mContext.getPackageName(), false);
        }
    }

    /**
     * This test doesn't touch the database.  It verifies that the ContentProvider returns
     * the correct type for each type of URI that it can handle.
     */
    public void testGetType() {
        String type = mContext.getContentResolver().getType(SearchResultEntry.CONTENT_URI);
        assertEquals("Error: the SearchResultEntry CONTENT_URI did not returned the expected SearchResultEntry.CONTENT_TYPE", SearchResultEntry.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(SearchResultEntry.buildUriwithAsin(TestUtilities.TEST_ASIN));
        assertEquals("Error: the SearchResultEntry CONTENT_URI with asin did not recorded SearchResultEntry.CONTENT_ITEM_TYPE", SearchResultEntry.CONTENT_ITEM_TYPE, type);

        type = mContext.getContentResolver().getType(AutoCompleteEntry.CONTENT_URI);
        assertEquals("Error: the AutoCompleteEntry CONTENT_URI did not returned the expected AutoCompleteEntry.CONTENT_TYPE", AutoCompleteEntry.CONTENT_TYPE, type);
    }

    public void testSearchResultItemQuery() {
        ContentValues itemValues = TestUtilities.createSearchResultItem();
        Uri contentUri = mContext.getContentResolver().insert(SearchResultEntry.CONTENT_URI, itemValues);

        assertTrue("Unable to insert SearchResultItem to content provider", contentUri != null);

        Cursor itemCursor = mContext.getContentResolver().query(
                SearchResultEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (itemCursor != null) {
            TestUtilities.validateCursor("Testing SearchResultItem Query", itemCursor, itemValues);
            itemCursor.close();
        }

        itemCursor = getCursorAsin();

        if(itemCursor != null) {
            TestUtilities.validateCursor("Testing SearchResultItem Asin Query", itemCursor, itemValues);
            itemCursor.close();
        }

        mContext.getContentResolver().delete(DataContract.SearchResultEntry.buildUriwithAsin(TestUtilities.TEST_ASIN), null, null);

        itemCursor = getCursorAsin();

        if(itemCursor != null) {
            assertTrue("SearchResultItem was not null", itemCursor.getCount() < 1);
        }
    }

    Cursor getCursorAsin() {
        return mContext.getContentResolver().query(
                SearchResultEntry.buildUriwithAsin(TestUtilities.TEST_ASIN),
                null,
                null,
                null,
                null
        );
    }
}
