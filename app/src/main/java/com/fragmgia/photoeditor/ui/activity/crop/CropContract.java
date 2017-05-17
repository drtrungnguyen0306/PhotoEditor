package com.fragmgia.photoeditor.ui.activity.crop;

import android.graphics.Bitmap;

import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

/**
 * Created by trungnguyens93gmail.com on 3/21/17.
 */
public class CropContract {
    interface View extends BaseView {
        void showImage(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter {
        void loadImage();
        void save(Bitmap bitmap);
    }
}
