package com.fragmgia.photoeditor.ui.activity.crop;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.ui.widget.CropBorderView;
import com.fragmgia.photoeditor.ui.widget.CropView;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by trungnguyens93gmail.com on 3/21/17.
 */
public class CropActivity extends BaseActivity implements CropContract.View {
    @BindView(R.id.crop_image)
    CropView mCropView;
    @BindView(R.id.crop_border_view)
    CropBorderView mCropBorderView;
    private CropContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        mPresenter = new CropPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadImage();
    }

    @Override
    public void start() {
    }

    @Override
    public void showImage(Bitmap bitmap) {
        mCropView.setImageBitmap(bitmap);
    }

    @Override
    public void accept() {
        cropImage();
        Bitmap bitmap = ((BitmapDrawable) mCropView.getDrawable()).getBitmap();
        mPresenter.save(bitmap);
        super.accept();
    }

    public void cropImage() {
        RectF clipRectF = mCropBorderView.getClipRectF();
        Bitmap bitmap = mCropView.clip(clipRectF);
        mCropView.setImageBitmap(bitmap);
        RelativeLayout.LayoutParams layoutParams =
            new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) clipRectF.left, (int) clipRectF.top, 0, 0);
        mCropView.setLayoutParams(layoutParams);
    }
}
