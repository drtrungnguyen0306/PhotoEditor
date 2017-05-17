package com.fragmgia.photoeditor.ui.activity.subimage;

import android.app.Activity;

import com.fragmgia.photoeditor.data.source.local.DataSourceInSDCard;
import com.fragmgia.photoeditor.ui.activity.images.ImagesContract;

/**
 * Created by trungnguyens93gmail.com on 4/17/17.
 */
public class SubImagesPresenter implements ImagesContract.Presenter {
    private ImagesContract.View mView;

    public SubImagesPresenter(ImagesContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void checkReadExternalPermission() {
    }

    @Override
    public void loadImages() {
        mView.showImages(DataSourceInSDCard.getImages((Activity) mView));
    }
}
