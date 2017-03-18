package com.fragmgia.photoeditor.ui.activity.crop;

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
        mView.showImage();
    }
}
