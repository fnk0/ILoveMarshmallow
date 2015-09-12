package com.gabilheri.ilovemarshmallow.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/15.
 *
 * Handy method to add Rounded corners to a image that will be loaded with Picasso
 *
 */
public class RoundTransformation implements Transformation {

    private int mCornerRadius = 0;

    public RoundTransformation(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if (source.getConfig() == null) {
            return source;
        }

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap image = Bitmap.createBitmap(width, height, source.getConfig());
        Canvas canvas = new Canvas(image);
        canvas.drawARGB(0, 0, 0, 0);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0, 0, width, height);

        if (this.mCornerRadius == 0) {
            canvas.drawRect(rect, paint);
        } else {
            canvas.drawRoundRect(new RectF(rect),
                    this.mCornerRadius, this.mCornerRadius, paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);

        if (source != image) {
            source.recycle();
        }
        return image;
    }

    @Override
    public String key() {
        return String.format("RoundTransformation(cornerRadius=%d)", this.mCornerRadius);
    }
}
