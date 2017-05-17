package com.fragmgia.photoeditor.ui.activity.merge;

import android.graphics.Bitmap;

import com.fragmgia.photoeditor.data.model.Control;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/29/17.
 */
public interface MergeContract {
    interface View extends BaseView {
        void showControls(List<Control> controls);
        void selectControl(int position);
    }

    interface Presenter extends BasePresenter {
        void loadControls();
        void handleSave(Bitmap bitmap);
    }
}
