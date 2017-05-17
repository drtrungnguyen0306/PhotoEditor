package com.fragmgia.photoeditor.ui.activity.merge;

import android.app.Activity;
import android.graphics.Bitmap;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Control;
import com.fragmgia.photoeditor.util.ConstantManager;
import com.fragmgia.photoeditor.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/29/17.
 */
public class MergePresenter implements MergeContract.Presenter {
    private MergeContract.View mView;

    public MergePresenter(MergeContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadControls() {
        List<Control> controls = new ArrayList<>();
        controls.add(new Control(R.drawable.ic_collage1, ConstantManager.Type.TYPE_ONE));
        controls.add(new Control(R.drawable.ic_collage2, ConstantManager.Type.TYPE_TWO));
        controls.add(new Control(R.drawable.ic_collage3, ConstantManager.Type.TYPE_THREE));
        controls.add(new Control(R.drawable.ic_collage4, ConstantManager.Type.TYPE_FOUR));
        controls.add(new Control(R.drawable.ic_collage5, ConstantManager.Type.TYPE_FIVE));
        mView.showControls(controls);
    }

    @Override
    public void handleSave(Bitmap bitmap) {
//        if (Util.saveImage(bitmap)) mView.saveSuccess();
//        else mView.saveError();
    }
}
