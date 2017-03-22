package com.fragmgia.photoeditor.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by trungnguyens93gmail.com on 3/18/17.
 */
public class ColorImageView extends android.support.v7.widget.AppCompatImageView {
    static int ALPHA_COLOR = 150;
    static int WIDTH_STROKE_PAINT = 24;
    private ArrayList<Pair<Path, Paint>> mPairs;
    private Path mPath;

    public ColorImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPairs = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Pair<Path, Paint> pair : mPairs) {
            canvas.drawPath(pair.first, pair.second);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(touchX, touchY);
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public void setColorPaint(int r, int g, int b) {
        mPath = new Path();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(ALPHA_COLOR, r, g, b));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(WIDTH_STROKE_PAINT);
        mPairs.add(new Pair<Path, Paint>(mPath, paint));
    }
}
