package com.gabilheri.ilovemarshmallow.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;
import static com.gabilheri.ilovemarshmallow.data.DataContract.SearchResultEntry;
import static com.gabilheri.ilovemarshmallow.data.DataContract.AutoCompleteEntry;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/5/15.
 */
public class TestUriMatcher extends AndroidTestCase {

    static final Uri TEST_ALL_SEARCH_ITEMS = SearchResultEntry.CONTENT_URI;
    static final Uri TEST_SEARCH_ITEM_ASIN = SearchResultEntry.buildUriwithAsin(TestUtilities.TEST_ASIN);
    static final Uri TEST_AUTOCOMPLETE_ITEMS = AutoCompleteEntry.CONTENT_URI;

    public void testUriMatcher() {
        UriMatcher testMatcher = MarshmallowProvider.buildUriMatcher();

        assertEquals("Error: SearchResultEntry Uri was matched incorrectly", testMatcher.match(TEST_ALL_SEARCH_ITEMS), MarshmallowProvider.SEARCH_ALL);
        assertEquals("Error: SearchResultEntry Uri with asin was matched incorrectly", testMatcher.match(TEST_SEARCH_ITEM_ASIN), MarshmallowProvider.SEARCH_ASIN);
        assertEquals("Error: AutoCompleteEntry Uri was matched incorrectly", testMatcher.match(TEST_AUTOCOMPLETE_ITEMS), MarshmallowProvider.AUTO_COMPLETE);
    }
}
