package com.fragmgia.photoeditor.ui.activity.audio;

import android.app.Activity;

import com.fragmgia.photoeditor.data.model.AudioInfo;
import com.fragmgia.photoeditor.data.source.local.DataSourceInSDCard;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/14/17.
 */
public class AudioPresenter implements AudioContract.Presenter {
    private AudioContract.View mView;

    public AudioPresenter(AudioContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadAudios() {
        List<AudioInfo> audioInfos = DataSourceInSDCard.getAudios((AudioActivity) mView);
        mView.showAudios(audioInfos);
    }
}
