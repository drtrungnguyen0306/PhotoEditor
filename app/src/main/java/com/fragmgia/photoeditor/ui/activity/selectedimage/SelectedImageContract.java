package com.fragmgia.photoeditor.ui.activity.selectedimage;

import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 5/17/17.
 */
public interface SelectedImageContract {
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
