package com.fragmgia.photoeditor.ui.activity.combine;

import com.fragmgia.photoeditor.data.model.AudioInfo;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/10/17.
 */
public interface CombineContact {
    interface View extends BaseView {
        void showDialog();
        void dismissDialog();
        void navigateToActivity(String string);
    }

    interface Presenter extends BasePresenter {
        void convertVideo(List<ImageInfo> imageInfos, AudioInfo audioInfo);
    }
}
