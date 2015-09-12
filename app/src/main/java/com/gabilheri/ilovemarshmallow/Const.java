package com.gabilheri.ilovemarshmallow;

import android.os.Build;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/3/15.
 */
public class Const {

    public static final String ZAPPOS_URL = "http://www.zappos.com";
    public static final String ASIN = "asin";
    public static final String TRANSITION_IMAGE = "imageTransition";
    public static final boolean IS_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

    // Loaders
    public static final int AUTO_COMPLETE_LOADER = 1000;
    public static final int FAVORITES_LOADER = 1001;
}
