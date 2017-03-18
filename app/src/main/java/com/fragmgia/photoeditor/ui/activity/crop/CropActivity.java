package com.fragmgia.photoeditor.ui.activity.crop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.ui.widget.CropView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 3/21/17.
 */
public class CropActivity extends BaseActivity implements CropContract.View {
    @BindView(R.id.image_crop)
    ImageView mCropImageView;
    @BindView(R.id.crop_view)
    CropView mCropView;
    private CropContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        mPresenter = new CropPresenter(this);
        mPresenter.start();
    }

    @Override
    public void start() {
    }

    @Override
    public void showImage() {
        mCropImageView.setImageBitmap(FunctionActivity.sMainBitmap);
    }
}
