package com.fragmgia.photoeditor.ui.activity.effect;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.source.local.FunctionsDataSource;
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
        mView.getImage();
        mView.showImage();
        List<Function> functions = FunctionsDataSource.getEffectsFunction();
        mView.showEffects(functions);
        mView.start();
    }
}
