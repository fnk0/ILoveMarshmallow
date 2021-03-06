package com.gabilheri.ilovemarshmallow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gabilheri.ilovemarshmallow.data.DataContract;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;
import com.gabilheri.ilovemarshmallow.ui.detail.DetailActivity;

import org.parceler.Parcels;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import timber.log.Timber;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/3/15.
 */
public class MarshmallowUtils {

    /**
     * Helper method to get a ContentValues representation of the AutoComplete item
     * @param searchTerm
     *      The term that was searched
     * @return
     *      A ContentValues that can be inserted into the content provider
     */
    public static ContentValues getAutoCompleteContentValues(String searchTerm) {
        ContentValues values = new ContentValues();
        values.put(DataContract.AutoCompleteEntry.SEARCH_TERM, searchTerm);
        return values;
    }

    /**
     * Helper method to open the detail Activity.
     * If the version is Lollipop or above a cool transition is performed
     *
     * @param activity
     *      The activity to start the intent
     * @param itemView
     *      The View that was clicked
     */
    public static void openProductDetail(Activity activity, View itemView) {
        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image);
        SearchResultItem item = (SearchResultItem) itemView.getTag(R.id.asin);
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(Const.ASIN, Parcels.wrap(item));
        if (Const.IS_LOLLIPOP) {
            performTransition(activity, imageView, intent);
        } else {
            activity.startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void performTransition(Activity activity, View v, Intent intent) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, v, Const.TRANSITION_IMAGE);
        activity.startActivity(intent, options.toBundle());
    }

    /**
     * Utility method that takes care of appending http://www.zappos.com to the
     * /data/... or /download/... urls found in the details of a product.
     * @param content
     *      The string containing the entire content of the details of a product
     * @return
     *      The details of the product with the sanitized URL's now able to be understood by the Android browser
     */
    public static String appendZapposBaseUrl(String content) {
        try {
            String returnString = content;
            Pattern regex = Pattern.compile("(?<=href=(\"|'))[^\"']+(?=(\"|'))");
            Matcher regexMatcher = regex.matcher(content);
            HashSet<String> replaced = new HashSet<>();
            while (regexMatcher.find()) {
                for (int i = 1; i <= regexMatcher.groupCount(); i++) {
                    Timber.d(regexMatcher.group());
                    String match = regexMatcher.group();
                    if (!replaced.contains(match)) {
                        replaced.add(match);
                        returnString = returnString.replace(match, Const.ZAPPOS_URL + match);
                    }
                }
            }
            return returnString;
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
            return null;
        }
    }

    /**
     * Utility method to colorize all the items on the Toolbar with a specified tint color
     * @param toolbar
     *      Toolbar to be colorized
     * @param toolbarIconsColor
     *      The color resource to be used to colorize the toolbar
     */
    public static void colorizeToolbar(Toolbar toolbar, @ColorRes int toolbarIconsColor) {

        Resources res = toolbar.getResources();

        int color = res.getColor(toolbarIconsColor);

        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            final View v = toolbar.getChildAt(i);

            //Step 1 : Changing the color of back button (or open drawer button).
            if (v instanceof ImageButton) {
                //Action Bar back button
                ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
            }

            if (v instanceof ActionMenuView) {
                for (int j = 0; j < ((ActionMenuView) v).getChildCount(); j++) {

                    //Step 2: Changing the color of any ActionMenuViews - icons that
                    //are not back button, nor text, nor overflow menu icon.
                    final View innerView = ((ActionMenuView) v).getChildAt(j);

                    if (innerView instanceof ActionMenuItemView) {
                        int drawablesCount = ((ActionMenuItemView) innerView).getCompoundDrawables().length;
                        for (int k = 0; k < drawablesCount; k++) {
                            if (((ActionMenuItemView) innerView).getCompoundDrawables()[k] != null) {
                                final int finalK = k;

                                //Important to set the color filter in seperate thread,
                                //by adding it to the message queue
                                //Won't work otherwise.
                                innerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ActionMenuItemView) innerView).getCompoundDrawables()[finalK].setColorFilter(colorFilter);
                                    }
                                });
                            }
                        }
                    }
                }
            }
            //Step 3: Changing the color of title and subtitle.
            toolbar.setTitleTextColor(color);
            toolbar.setSubtitleTextColor(color);
        }
    }
}
