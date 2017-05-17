package com.fragmgia.photoeditor.ui.activity.crop;

import android.graphics.Bitmap;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;

/**
 * Created by trungnguyens93gmail.com on 3/21/17.
 */
public class CropPresenter implements CropContract.Presenter {
    private CropContract.View mView;

    public CropPresenter(CropContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadImage() {
        Bitmap bitmap = FunctionActivity.sMainBitmap;
        mView.showImage(bitmap);
    }

    @Override
    public void save(Bitmap bitmap) {
        FunctionActivity.sMainBitmap = bitmap;
    }
}
