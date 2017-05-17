package com.fragmgia.photoeditor.ui.activity.adjust;

import android.graphics.Bitmap;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 5/6/17.
 */
public interface AdjustContract {
    interface View extends BaseView {
        void showImage(Bitmap bitmap);
        void showFunctions(List<Function> functions);
        void selectFunction(Function function);
    }

    interface Presenter extends BasePresenter {
        void loadImage();
        void loadFunctions();
        Bitmap changeContrast(Bitmap bmIn, double value);
        Bitmap changeHUE(Bitmap bmIn, int value);
        Bitmap changeBrightness(Bitmap bmIn, int value);
        void save(Bitmap bitmap);
    }
}
