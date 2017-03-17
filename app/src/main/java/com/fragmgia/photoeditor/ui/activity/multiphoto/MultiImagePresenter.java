package com.fragmgia.photoeditor.ui.activity.multiphoto;

import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.data.source.local.DataSourceInSDCard;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/12/17.
 */
public class MultiImagePresenter implements MultiImageContract.Presenter {
    private MultiImageContract.View mView;

    public MultiImagePresenter(MultiImageContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadImages() {
        List<ImageInfo> imageInfos =
            DataSourceInSDCard.getImages((MultiImageActivity) mView);
        mView.showImages(imageInfos);
    }
}
