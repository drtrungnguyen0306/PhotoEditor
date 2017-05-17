package com.fragmgia.photoeditor.ui.activity.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.fragmgia.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 5/6/17.
 */
public class VideoActivity extends AppCompatActivity implements VideoContract.View,
    MediaPlayer.OnPreparedListener {
    private static final String TAG = "VideoActivity";
    @BindView(R.id.video_view)
    VideoView mVideoView;
    @BindView(R.id.text_video_path)
    TextView mPathTextView;
    @BindView(R.id.button_share_video)
    Button mShareButton;
    private VideoContract.Presenter mPresenter;
    private MediaController mMediaController;
    private String mVideoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        setUpActionBar();
        mVideoPath = getIntent().getStringExtra("path");
        mMediaController = new MediaController(this);
        mPresenter = new VideoPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
        mPathTextView.setText(mVideoPath);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void start() {
        mMediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                mMediaController.setAnchorView(mVideoView);
            }
        });
    }

    @Override
    public void shareVideo() {
        MediaScannerConnection.scanFile(this, new String[] { mVideoPath }, null, new
            MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("video/mp4");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Share Video");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Another Application"));
                }
            });
    }

    @OnClick(R.id.button_share_video)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_share_video:
                shareVideo();
                break;
            default:
                break;
        }
    }

    public void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
