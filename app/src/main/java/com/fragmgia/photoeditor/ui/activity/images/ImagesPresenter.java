package com.fragmgia.photoeditor.ui.activity.images;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.data.source.local.DataSourceInSDCard;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.ArrayList;
import java.util.List;

public class ImagesPresenter implements ImagesContract.Presenter {
    private ImagesContract.View mView;
    public ImagesPresenter(ImagesContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadImages() {
        // Capture image
        ImageInfo info = new ImageInfo();
        info.setName(ConstantManager.CAPTURE_IMAGE);
        // Add capture image
        List<ImageInfo> imageInfos = new ArrayList<>();
        imageInfos.add(info);
        // Add all images in SDCard
        imageInfos.addAll(DataSourceInSDCard.getImages((ImagesActivity) mView));
        mView.showImages(imageInfos);
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
}
