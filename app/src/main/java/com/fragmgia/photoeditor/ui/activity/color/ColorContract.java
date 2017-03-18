package com.fragmgia.photoeditor.ui.activity.color;

import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

/**
 * Created by trungnguyens93gmail.com on 3/18/17.
 */
public interface ColorContract {
    interface View extends BaseView {
        void showImage();
    }

    interface Presenter extends BasePresenter {

    }
}
