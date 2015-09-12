package com.gabilheri.ilovemarshmallow.ui;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/15.
 */
public interface OnScrolledCallback {

    /**
     * Default scroll callback with the current scrolled page
     * This method only gets called when the current page is bigger than the previous one
     *
     * @param page
     *      The current scrolled page
     */
    void onScrolled(int page);

}
