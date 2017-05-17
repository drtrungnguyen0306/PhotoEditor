package com.fragmgia.photoeditor.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Option;

/**
 * Created by trungnguyens93gmail.com on 3/21/17.
 */
public class CropView extends android.support.v7.widget.AppCompatImageView {
    public CropView(Context context) {
        super(context);
    }

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Bitmap clip(RectF rectF) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        bitmap = Bitmap.createBitmap(bitmap, (int) rectF.left, (int) rectF.top, (int) rectF.width(),
            (int) rectF.height());
        return bitmap;
    }
}
