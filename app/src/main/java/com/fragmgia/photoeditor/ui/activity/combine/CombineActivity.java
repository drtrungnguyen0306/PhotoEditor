package com.fragmgia.photoeditor.ui.activity.combine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.AudioInfo;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.audio.AudioActivity;
import com.fragmgia.photoeditor.ui.activity.multiphoto.MultiImageActivity;
import com.fragmgia.photoeditor.ui.activity.video.VideoActivity;
import com.fragmgia.photoeditor.ui.adapter.CombiningImageAdapter;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 3/10/17.
 */
public class CombineActivity extends AppCompatActivity implements CombineContact.View {
    private static final String TAG = "CombineActivity";
    private static final int IMAGE_REQUEST_CODE = 1;
    private static final int AUDIO_REQUEST_CODE = 2;
    @BindView(R.id.image_list_combine)
    ImageView mCollectionImageView;
    @BindView(R.id.image_audio_combine)
    ImageView mAudioCombineImageView;
    @BindView(R.id.recycle_view_combining_images)
    RecyclerView mImagesRecycleView;
    @BindView(R.id.text_combining_audio_name)
    TextView mAudioNameTextView;
    @BindView(R.id.button_combine_video)
    Button mCombineButton;
    private CombineContact.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private CombiningImageAdapter mCombiningImageAdapter;
    private List<ImageInfo> mImageInfos;
    private AudioInfo mAudioInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);
        ButterKnife.bind(this);
        mPresenter = new CombinePresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCombiningImageAdapter.setImageInfos(mImageInfos);
        mCombiningImageAdapter.notifyDataSetChanged();
        if (mAudioInfo != null) mAudioNameTextView.setText(mAudioInfo.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Serializable serializableImage =
                        data.getSerializableExtra(ConstantManager.EXTRA_LIST_IMAGE_INFO);
                    mImageInfos = convertToArrayImageInfo(serializableImage);
                    break;
                case AUDIO_REQUEST_CODE:
                    mAudioInfo = (AudioInfo) data.getSerializableExtra(ConstantManager.EXTRA_AUDIO);
//                    String result = showDuration(mAudioInfo.getPath());
//                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    public String showDuration(String audioPath) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse(audioPath));
        int duration = mediaPlayer.getDuration();
        Log.i(TAG, String.valueOf(duration));
        mediaPlayer.release();
//        return String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit
//            .MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit
//            .MILLISECONDS.toMinutes(duration)), Locale.UK);
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(duration) - 6);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    public void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void start() {
        setUpActionBar();
        initView();
    }

    @Override
    public void showDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void navigateToActivity(String string) {
        Intent intent = new Intent(CombineActivity.this, VideoActivity.class);
        intent.putExtra("path", string);
        startActivity(intent);
    }

    @OnClick({R.id.image_list_combine, R.id.image_audio_combine, R.id.button_combine_video})
    public void onClick(View view) {
        Intent intent = null;
        int requestCode = 0;
        switch (view.getId()) {
            case R.id.image_list_combine:
                intent = new Intent(CombineActivity.this, MultiImageActivity.class);
                requestCode = IMAGE_REQUEST_CODE;
                break;
            case R.id.image_audio_combine:
                intent = new Intent(CombineActivity.this, AudioActivity.class);
                requestCode = AUDIO_REQUEST_CODE;
                break;
            case R.id.button_combine_video:
                mPresenter.convertVideo(mImageInfos, mAudioInfo);
                break;
            default:
                break;
        }
        if (intent != null) startActivityForResult(intent, requestCode);
    }

    public void initView() {
        mCombiningImageAdapter = new CombiningImageAdapter(this);
        mImagesRecycleView.setHasFixedSize(true);
        mImagesRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
            .HORIZONTAL, false));
        mImagesRecycleView.setAdapter(mCombiningImageAdapter);
        mProgressDialog = new ProgressDialog(CombineActivity.this);
        mProgressDialog.setMessage(getString(R.string.msg_creating));
        mProgressDialog.setCancelable(false);
    }

    public ArrayList<ImageInfo> convertToArrayImageInfo(Serializable serializable) {
        if (serializable == null) return null;
        ArrayList<ImageInfo> imageInfos = null;
        if (serializable instanceof ArrayList<?>) {
            ArrayList<?> arrayList = (ArrayList<?>) serializable;
            if (arrayList.size() > 0) {
                if (arrayList.get(0) instanceof ImageInfo) {
                    imageInfos = new ArrayList<>();
                    for (int i = 0; i < arrayList.size(); i++) {
                        imageInfos.add((ImageInfo) arrayList.get(i));
                    }
                }
            }
        }
        return imageInfos;
    }

}
