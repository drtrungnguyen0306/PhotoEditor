package com.fragmgia.photoeditor.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by trungnguyens93gmail.com on 4/10/17.
 */
public class CustomImageView extends android.support.v7.widget.AppCompatImageView {
    private static final int WIDTH_IMAGE = 800;
    private static final int HEIGHT_IMAGE = 1000;
    private static final String LEFT_LOCATION = "LEFT";
    private static final String RIGHT_LOCATION = "RIGHT";
    private Bitmap mLeftBitmap;
    private Bitmap mRightBitmap;
    private Paint mLeftImagePaint;
    private Paint mRightImagePaint;
    private Paint mTextPaint;
    private Shader mLeftShader;
    private Shader mRightShader;
    private String mLocation;
    private OnClickImageView mOnClickImageView;

    public CustomImageView(Context context,
                           @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageView();
    }

    public void setImageBitmap(Bitmap bitmap) {
        switch (mLocation) {
            case LEFT_LOCATION:
                mLeftBitmap = bitmap;
                mLeftShader = new BitmapShader(mLeftBitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
                mLeftShader.setLocalMatrix(getMatrix(mLeftBitmap));
                break;
            case RIGHT_LOCATION:
                mRightBitmap = bitmap;
                mRightShader = new BitmapShader(mRightBitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
                mRightShader.setLocalMatrix(getMatrix(mRightBitmap));
                break;
            default:
                break;
        }
        invalidate();
    }

    public void setOnClickImageView(OnClickImageView onClickImageView) {
        mOnClickImageView = onClickImageView;
    }

    public Matrix getMatrix(Bitmap bitmap) {
        RectF srcRectF, dstRectF;
        Matrix matrix;
        srcRectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dstRectF = new RectF(400, 400, 1200, 1000);
        matrix = new Matrix();
        matrix.setRectToRect(srcRectF, dstRectF, Matrix.ScaleToFit.CENTER);
        return matrix;
    }

    public void initImageView() {
        setBackgroundColor(Color.WHITE);
        mLeftImagePaint = new Paint();
        mRightImagePaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setTextSize(65);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(WIDTH_IMAGE, HEIGHT_IMAGE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final RectF haftCircle = new RectF();

        if (mLeftBitmap != null) mLeftImagePaint.setShader(mLeftShader);
        else mLeftImagePaint.setColor(Color.GRAY);
        haftCircle.set(150, 150, 600, 600);
        canvas.drawArc(haftCircle, 90, 180, true, mLeftImagePaint);

        if (mRightBitmap != null) mRightImagePaint.setShader(mRightShader);
        else mRightImagePaint.setColor(Color.GRAY);
        haftCircle.set(200, 200, 650, 650);
        canvas.drawArc(haftCircle, 270, 180, true, mRightImagePaint);

        canvas.drawText("Thank you!", 280, 850, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX, touchY;
        touchX = event.getX();
        touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if ((158 < touchX && touchX < 368) && (160 < touchY && touchY < 600))
                    mLocation = LEFT_LOCATION;
                else if ((430 < touchX && touchX < 660) && (203 < touchY && touchX < 640))
                    mLocation = RIGHT_LOCATION;
                if (mOnClickImageView != null) mOnClickImageView.onClick();
                break;
            default:
                break;
        }
        return true;
    }

    public interface OnClickImageView {
        void onClick();
    }
}
