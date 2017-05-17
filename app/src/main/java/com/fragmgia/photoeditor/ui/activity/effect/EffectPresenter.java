package com.fragmgia.photoeditor.ui.activity.effect;

import android.graphics.Bitmap;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.source.local.FunctionsDataSource;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.activity.function.FunctionContract;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/2/17.
 */
public class EffectPresenter implements EffectContract.Presenter {
    private EffectContract.View mView;

    public EffectPresenter(EffectContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadImage() {
        Bitmap bitmap = FunctionActivity.sMainBitmap;
        mView.showImage(bitmap);
    }

    @Override
    public void loadEffects() {
        List<Function> functions = FunctionsDataSource.getEffectsFunction();
        mView.showEffects(functions);
    }

    @Override
    public void save(Bitmap bitmap) {
        FunctionActivity.sMainBitmap = bitmap;
    }
}
