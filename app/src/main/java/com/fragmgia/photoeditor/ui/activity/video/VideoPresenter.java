package com.fragmgia.photoeditor.ui.activity.video;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;

/**
 * Created by trungnguyens93gmail.com on 5/7/17.
 */
public class VideoPresenter implements VideoContract.Presenter {
    private VideoContract.View mView;

    public VideoPresenter(VideoContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }
}
