package com.fragmgia.photoeditor.ui.activity.function;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public interface FunctionContract {
    interface View extends BaseView {
        void getImage();
        void showImage();
        void showFunctions(List<Function> functions);
        void selectFunction(Function function);
    }

    interface Presenter extends BasePresenter {
        void loadImage();
    }
}
