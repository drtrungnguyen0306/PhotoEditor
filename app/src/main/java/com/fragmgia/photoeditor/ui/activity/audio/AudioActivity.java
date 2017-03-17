package com.fragmgia.photoeditor.ui.activity.audio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.AudioInfo;
import com.fragmgia.photoeditor.ui.adapter.AudioAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 3/14/17.
 */
public class AudioActivity extends BaseActivity implements AudioContract.View {
    @BindView(R.id.recycle_view_images)
    RecyclerView mAudioRecyclerView;
    private AudioContract.Presenter mPresenter;
    private AudioAdapter mAudioAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);
        mPresenter = new AudioPresenter(this);
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadAudios();
    }

    @Override
    public void start() {
        mAudioAdapter = new AudioAdapter(this, this);
        mAudioRecyclerView.setHasFixedSize(true);
        mAudioRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAudioRecyclerView.setAdapter(mAudioAdapter);
    }

    @Override
    public void showAudios(List<AudioInfo> audioInfos) {
        mAudioAdapter.setAudioInfos(audioInfos);
        mAudioAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectAudio(AudioInfo audioInfo) {
        Intent intent = new Intent();
        intent.putExtra(ConstantManager.EXTRA_AUDIO, audioInfo);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
