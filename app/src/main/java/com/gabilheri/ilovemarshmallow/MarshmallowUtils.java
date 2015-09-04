package com.gabilheri.ilovemarshmallow;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gabilheri.ilovemarshmallow.ui.detail.DetailActivity;

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

    public static void openProductDetail(Activity activity, View itemView) {
        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image);
        Intent intent = new Intent(activity, DetailActivity.class);
        String picUrl = (String) imageView.getTag(R.id.item_image);
        intent.putExtra(Const.ASIN, (String) itemView.getTag(R.id.asin));
        intent.putExtra(Const.TRANSITION_IMAGE, picUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, imageView, Const.TRANSITION_IMAGE);
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    public static String appendZapposBaseUrl(String content) {
        try {
            String returnString = content;
            Pattern regex = Pattern.compile("(?<=href=(\"|'))[^\"']+(?=(\"|'))");
            Matcher regexMatcher = regex.matcher(content);
            HashSet<String> replaced = new HashSet<>();
            while (regexMatcher.find()) {
                for (int i = 1; i <= regexMatcher.groupCount(); i++) {
                    // matched text: regexMatcher.group(i)
                    // match start: regexMatcher.start(i)
                    // match end: regexMatcher.end(i)
                    Timber.d(regexMatcher.group());
                    String match = regexMatcher.group();
                    if (!replaced.contains(match)) {
                        replaced.add(match);
                        returnString = returnString.replace(match, "http://www.zappos.com" + match);
                    }
                }
            }
            return returnString;
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
            return null;
        }
    }

    public static Drawable getTintedDrawable(Resources res, @NonNull Drawable drawable, @ColorRes int colorResId) {
        int color = res.getColor(colorResId);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static void colorizeToolbar(Toolbar toolbarView, @ColorRes int toolbarIconsColor) {

        Resources res = toolbarView.getResources();

        int color = res.getColor(toolbarIconsColor);

        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);

        for (int i = 0; i < toolbarView.getChildCount(); i++) {
            final View v = toolbarView.getChildAt(i);

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
            toolbarView.setTitleTextColor(color);
            toolbarView.setSubtitleTextColor(color);
        }
    }
}
