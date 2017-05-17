package com.fragmgia.photoeditor.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by trungnguyens93gmail.com on 4/5/17.
 */
public class SubImageView extends RelativeLayout {
    private Context mContext;
    @BindView(R.id.image_test)
    ImageView mMainImageView;
    @BindView(R.id.image_close)
    ImageView mCloseImageView;
    @BindView(R.id.image_rotate)
    ImageView mRotateImageView;
    @BindView(R.id.image_resize)
    ImageView mResizeImageView;

    public SubImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        ButterKnife.bind(this);
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.partial_image_view, this);
    }

    public void setImageBitmap(Bitmap bitmap) {
        mMainImageView.setImageBitmap(bitmap);
    }

    @OnTouch({R.id.image_close, R.id.image_rotate, R.id.image_resize, R.id.image_test})
    public boolean onTouchEvent(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.image_close:
                closeEvent(event);
                break;
            case R.id.image_rotate:
                rotateEvent(event);
                break;
            case R.id.image_resize:
                resizeEvent(event);
                break;
            case R.id.image_test:
                dropEvent(event);
                break;
            default:
                break;
        }
        return true;
    }

    public void closeEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            default:
                break;
        }
    }

    public void rotateEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(mContext, "Is Rotated", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void resizeEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(mContext, "Is Resized", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void dropEvent(MotionEvent event) {
        int imageWidth, imageHeight;
        imageWidth = mMainImageView.getWidth();
        imageHeight = mMainImageView.getHeight();
        int[] location = new int[2];
        float imgX, imgY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                imgX = event.getRawX() - imageWidth;
                imgY = event.getRawY() - imageHeight;
                this.setX(imgX);
                this.setY(imgY);
                break;
            case MotionEvent.ACTION_UP:
                this.getLocationOnScreen(location);
                break;
            default:
                break;
        }
    }
}
