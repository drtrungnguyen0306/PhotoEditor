package com.fragmgia.photoeditor.ui.activity.function;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.source.local.FunctionsDataSource;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class FunctionPresenter implements FunctionContract.Presenter {
    private FunctionContract.View mView;

    public FunctionPresenter(FunctionContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.getImage();
        mView.start();
        List<Function> functions = FunctionsDataSource.getFunctions();
        mView.showFunctions(functions);
    }

    @Override
    public void loadImage() {
        mView.showImage();
    }
}
