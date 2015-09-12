package com.gabilheri.ilovemarshmallow.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/5/15.
 *
 * Default contracts that will be used by the ContentProvider
 *
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.gabilheri.ilovemarshmallow.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_AUTO_COMPLETE = "auto_complete";
    public static final String PATH_SEARCH_RESULT_ITEM = "search_result_item";

    public static final class AutoCompleteEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_AUTO_COMPLETE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_AUTO_COMPLETE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_AUTO_COMPLETE;

        public static final String TABLE_NAME = PATH_AUTO_COMPLETE;
        public static final String SEARCH_TERM = "term";
    }

    public static final class SearchResultEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_RESULT_ITEM).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEARCH_RESULT_ITEM;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEARCH_RESULT_ITEM;

        public static final String TABLE_NAME = PATH_SEARCH_RESULT_ITEM;
        public static final String ASIN = "asin";
        public static final String BRAND_NAME = "brandName";
        public static final String ORIGINAL_PRICE = "originalPrice";
        public static final String PRICE = "price";
        public static final String IMAGE_URL = "imageUrl";
        public static final String PRODUCT_URL = "productUrl";
        public static final String PRODUCT_RATING = "productRating";
        public static final String PRODUCT_NAME = "productName";

        public static Uri buildUriwithAsin(String asin) {
            return CONTENT_URI.buildUpon().appendPath(asin).build();
        }


    }

}
