package com.fragmgia.photoeditor.ui.activity.video;

import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

/**
 * Created by trungnguyens93gmail.com on 5/7/17.
 */
public interface VideoContract {
    interface View extends BaseView {
        void shareVideo();
    }

    interface Presenter extends BasePresenter {
    }
}
