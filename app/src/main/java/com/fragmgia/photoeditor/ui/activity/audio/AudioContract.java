package com.fragmgia.photoeditor.ui.activity.audio;

import com.fragmgia.photoeditor.data.model.AudioInfo;
import com.fragmgia.photoeditor.ui.base.BasePresenter;
import com.fragmgia.photoeditor.ui.base.BaseView;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 3/14/17.
 */
public interface AudioContract {
    interface View extends BaseView {
        void showAudios(List<AudioInfo> audioInfos);
        void selectAudio(AudioInfo audioInfo);
    }

    interface Presenter extends BasePresenter {
        void loadAudios();
    }
}
