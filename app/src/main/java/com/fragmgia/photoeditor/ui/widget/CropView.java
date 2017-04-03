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

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Option;

/**
 * Created by trungnguyens93gmail.com on 3/21/17.
 */
public class CropView extends View {
    private static final int TOP_ICON_ACTION = 1;
    private static final int BOTTOM_ICON_ACTION = 2;
    private static final int ALL_ACTION = 3;
    private static final String COLOR_WHITE = "#FFFFFF";
    private static final String COLOR_BLACK = "#50000000";
    private int mBorderColor = Color.parseColor(COLOR_WHITE);
    private int mOutSideColor = Color.parseColor(COLOR_BLACK);
    private float mBorderWidth = 2;
    private float mLineWidth = 1;
    private Rect[] mRects = new Rect[2];
    private Paint mCutPaint;
    private Paint mOutSidePaint;
    private RectF mClipRectF;
    private Bitmap mBitmap;
    private int mIconOffset;
    private int mWidth;
    private int mHeight;
    private Option mOption;
    private int mAction = -1;
    private float mActionX;
    private float mActionY;
    private float mRecWidth;
    private float mRecHeight;

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mCutPaint = new Paint();
        mCutPaint.setColor(mBorderColor);
        mCutPaint.setStrokeWidth(mBorderWidth);
        mCutPaint.setStyle(Paint.Style.STROKE);
        mOutSidePaint = new Paint();
        mOutSidePaint.setAntiAlias(true);
        mOutSidePaint.setColor(mOutSideColor);
        mOutSidePaint.setStyle(Paint.Style.FILL);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_crop_point);
        mIconOffset = mBitmap.getWidth() / 2;
        mOption = new Option(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mClipRectF == null) {
            mClipRectF = new RectF((getWidth() - mOption.getClipHeight()) / 2,
                (getHeight() - mOption.getClipHeight()) / 2,
                (getWidth() + mOption.getClipHeight()) / 2,
                (getHeight() + mOption.getClipHeight()) / 2);
        }
        if (mWidth == 0) mWidth = getWidth();
        if (mHeight == 0) mHeight = getHeight();
        canvas.save();
        drawLine(canvas);
        drawRound(canvas);
        drawIcon(canvas);
        canvas.restore();
        super.onDraw(canvas);
    }

    private void drawIcon(Canvas canvas) {
        canvas.drawBitmap(mBitmap,
            mClipRectF.left - mIconOffset,
            mClipRectF.top - mIconOffset,
            null);
        canvas.drawBitmap(mBitmap,
            mClipRectF.right - mIconOffset,
            mClipRectF.bottom - mIconOffset,
            null);
        Rect rect = new Rect((int) (mClipRectF.left - mIconOffset) - Option.sIconClick,
            (int) (mClipRectF.top - mIconOffset) - Option.sIconClick,
            (int) (mClipRectF.left + mIconOffset) + Option.sIconClick,
            (int) (mClipRectF.top + mIconOffset) + Option.sIconClick);
        mRects[0] = rect;
        rect = new Rect((int) (mClipRectF.right - mIconOffset) - Option.sIconClick,
            (int) (mClipRectF.bottom - mIconOffset) - Option.sIconClick,
            (int) (mClipRectF.right + mIconOffset) + Option.sIconClick,
            (int) (mClipRectF.bottom + mIconOffset) + Option.sIconClick);
        mRects[1] = rect;
    }

    private void drawLine(Canvas canvas) {
        mCutPaint.setStrokeWidth(mLineWidth);
        float p = mClipRectF.top + mClipRectF.height() / 3;
        canvas.drawLine(mClipRectF.left, p, mClipRectF.right, p, mCutPaint);
        p = mClipRectF.top + mClipRectF.height() * 2 / 3;
        canvas.drawLine(mClipRectF.left, p, mClipRectF.right, p, mCutPaint);
        p = mClipRectF.left + mClipRectF.width() / 3;
        canvas.drawLine(p, mClipRectF.top, p, mClipRectF.bottom, mCutPaint);
        p = mClipRectF.left + mClipRectF.width() * 2 / 3;
        canvas.drawLine(p, mClipRectF.top, p, mClipRectF.bottom, mCutPaint);
    }

    private void drawRound(Canvas canvas) {
        mCutPaint.setStrokeWidth(mBorderWidth);
        canvas.drawRect(mClipRectF, mCutPaint);
        canvas.drawRect(0, 0, mClipRectF.left, mHeight, mOutSidePaint);
        canvas.drawRect(mClipRectF.right, 0, mWidth, mHeight, mOutSidePaint);
        canvas.drawRect(mClipRectF.left, 0, mClipRectF.right, mClipRectF.top, mOutSidePaint);
        canvas
            .drawRect(mClipRectF.left, mClipRectF.bottom, mClipRectF.right, mHeight, mOutSidePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mClipRectF.contains((int) event.getX(), (int) event.getY())) {
                    mAction = ALL_ACTION;
                    mRecWidth = mClipRectF.width();
                    mRecHeight = mClipRectF.height();
                }
                if (mRects[0].contains((int) event.getX(), (int) event.getY())) {
                    mAction = TOP_ICON_ACTION;
                }
                if (mRects[1].contains((int) event.getX(), (int) event.getY())) {
                    mAction = BOTTOM_ICON_ACTION;
                }
                mActionX = event.getX();
                mActionY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = mActionX - event.getX();
                float y = mActionY - event.getY();
                switch (mAction) {
                    case TOP_ICON_ACTION:
                        mClipRectF.top = mClipRectF.top - y;
                        mClipRectF.left = mClipRectF.left - x;
                        break;
                    case BOTTOM_ICON_ACTION:
                        mClipRectF.bottom = mClipRectF.bottom - y;
                        mClipRectF.right = mClipRectF.right - x;
                        break;
                    case ALL_ACTION:
                        if (mClipRectF.top > mIconOffset) mClipRectF.bottom -= y;
                        if (mClipRectF.bottom < mHeight - mIconOffset) mClipRectF.top -= y;
                        if (mClipRectF.left > mIconOffset) mClipRectF.right -= x;
                        if (mClipRectF.right < mWidth - mIconOffset) mClipRectF.left -= x;
                        break;
                    default:
                        break;
                }
                checkBroad();
                mActionX = event.getX();
                mActionY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mAction = -1;
                break;
            default:
                break;
        }
        return true;
    }

    private void checkBroad() {
        if ((mClipRectF.bottom - mClipRectF.top) < mOption.getMinHeight()) {
            switch (mAction) {
                case TOP_ICON_ACTION:
                    mClipRectF.top = mClipRectF.bottom - mOption.getMinHeight();
                    break;
                case BOTTOM_ICON_ACTION:
                    mClipRectF.bottom = mClipRectF.top + mOption.getMinHeight();
                    break;
                default:
                    break;
            }
        }
        if ((mClipRectF.right - mClipRectF.left) < mOption.getMinWidth()) {
            switch (mAction) {
                case TOP_ICON_ACTION:
                    mClipRectF.left = mClipRectF.right - mOption.getMinWidth();
                    break;
                case BOTTOM_ICON_ACTION:
                    mClipRectF.right = mClipRectF.left + mOption.getMinWidth();
                    break;
                default:
                    break;
            }
        }
        if (mClipRectF.top < mIconOffset) {
            mClipRectF.top = mIconOffset;
            if (mAction == ALL_ACTION) mClipRectF.bottom = mClipRectF.top + mRecHeight;
        }
        if (mClipRectF.bottom > mHeight - mIconOffset) {
            mClipRectF.bottom = mHeight - mIconOffset;
            if (mAction == ALL_ACTION) mClipRectF.top = mClipRectF.bottom - mRecHeight;
        }
        if (mClipRectF.left < mIconOffset) {
            mClipRectF.left = mIconOffset;
            if (mAction == ALL_ACTION) mClipRectF.right = mClipRectF.left + mRecWidth;
        }
        if (mClipRectF.right > mWidth - mIconOffset) {
            mClipRectF.right = mWidth - mIconOffset;
            if (mAction == ALL_ACTION) mClipRectF.left = mClipRectF.right - mRecWidth;
        }
    }

    public RectF getClipRectF() {
        return mClipRectF;
    }
}
