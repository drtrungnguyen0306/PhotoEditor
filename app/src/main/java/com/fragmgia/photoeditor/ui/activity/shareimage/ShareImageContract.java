package com.fragmgia.photoeditor.ui.activity.shareimage;

import android.content.Intent;
import android.graphics.Bitmap;

import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

/**
 * Created by trungnguyens93gmail.com on 5/9/17.
 */
public interface ShareImageContract {
    interface View extends BaseView {
        void showImage(Bitmap bitmap);
        void showDialog();
        void dismissDialog();
        void showResult(boolean result);
        void showShareIntent(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void loadImage();
        void save();
        void share(String path);
    }
}
