package com.fragmgia.photoeditor.ui.activity.images;

import android.content.Context;

import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/23/17.
 */
public interface ImagesContract {
    interface View extends BaseView {
        void showImages(List<ImageInfo> imageInfos);
        void selectSingleImage(ImageInfo imageInfo);
        void showMessageDialog();
        void showRequestPermission();
    }

    interface Presenter extends BasePresenter {
        void checkReadExternalPermission();
        void loadImages();
    }
}
