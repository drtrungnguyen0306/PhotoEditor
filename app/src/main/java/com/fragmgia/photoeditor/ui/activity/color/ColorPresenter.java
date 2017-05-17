package com.fragmgia.photoeditor.ui.activity.color;

import android.graphics.Bitmap;

import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.base.BasePresenter;

/**
 * Created by trungnguyens93gmail.com on 3/18/17.
 */
public class ColorPresenter implements ColorContract.Presenter {
    private ColorContract.View mView;

    public ColorPresenter(ColorContract.View view) {
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
