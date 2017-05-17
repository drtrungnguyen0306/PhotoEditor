package com.fragmgia.photoeditor.ui.activity.effect;

import android.graphics.Bitmap;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/3/17.
 */
public interface EffectContract {
    interface View extends BaseView {
        void showImage(Bitmap bitmap);
        void showEffects(List<Function> functions);
        void selectEffect(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter {
        void loadImage();
        void loadEffects();
        void save(Bitmap bitmap);
    }
}
