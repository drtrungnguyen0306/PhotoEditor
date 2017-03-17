package com.fragmgia.photoeditor.ui.activity.multiphoto;

import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/12/17.
 */
public interface MultiImageContract {
    interface View extends BaseView {
        void showImages(List<ImageInfo> imageInfos);
        void checkItem(ImageInfo imageInfo);
        void uncheckItem(ImageInfo imageInfo);
    }

    interface Presenter extends BasePresenter {
        void loadImages();
    }
}
