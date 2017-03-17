package com.fragmgia.photoeditor.ui.activity.combine;

/**
 * Created by trungnguyens93gmail.com on 3/10/17.
 */
public class CombinePresenter implements CombineContact.Presenter {
    private CombineContact.View mView;

    public CombinePresenter(CombineContact.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }
}
