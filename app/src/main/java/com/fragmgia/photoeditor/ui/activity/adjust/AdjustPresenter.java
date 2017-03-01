package com.fragmgia.photoeditor.ui.activity.adjust;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.source.local.FunctionsDataSource;
import com.fragmgia.photoeditor.ui.activity.function.FunctionContract;
import com.fragmgia.photoeditor.ui.activity.function.FunctionPresenter;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/26/17.
 */
public class AdjustPresenter extends FunctionPresenter {
    private FunctionContract.View mView;

    public AdjustPresenter(FunctionContract.View view) {
        super(view);
        mView = view;
    }

    @Override
    public void start() {
        mView.showImage();
        List<Function> functions = FunctionsDataSource.getAdjustFunctions();
        mView.showFunctions(functions);
        mView.start();
    }
}
