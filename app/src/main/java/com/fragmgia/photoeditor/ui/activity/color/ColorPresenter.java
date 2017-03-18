package com.fragmgia.photoeditor.ui.activity.color;

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
        mView.showImage();
    }
}
