package com.fragmgia.photoeditor.ui.activity.selectedimage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.data.source.local.DataSourceInSDCard;
import com.fragmgia.photoeditor.ui.activity.images.ImagesActivity;
import com.fragmgia.photoeditor.ui.activity.images.ImagesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 5/17/17.
 */
public class SelectedImagePresenter implements SelectedImageContract.Presenter {
    private SelectedImageContract.View mView;

    public SelectedImagePresenter(SelectedImageContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void checkReadExternalPermission() {
        if (ActivityCompat.checkSelfPermission((Context) mView,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadImages();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mView,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                mView.showMessageDialog();
            } else {
                mView.showRequestPermission();
            }
        }
    }

    @Override
    public void loadImages() {
        List<ImageInfo> imageInfos = new ArrayList<>();
        imageInfos.addAll(DataSourceInSDCard.getImages((SeletedImageActivity) mView));
        mView.showImages(imageInfos);
    }
}
