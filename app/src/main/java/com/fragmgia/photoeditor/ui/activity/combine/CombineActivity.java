package com.fragmgia.photoeditor.ui.activity.combine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.fragmgia.photoeditor.ui.adapter.CombiningImageAdapter;
import com.fragmgia.photoeditor.util.ConstantManager;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 3/10/17.
 */
public class CombineActivity extends AppCompatActivity implements CombineContact.View {
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
    private CombiningImageAdapter mCombiningImageAdapter;
    private List<ImageInfo> mImageInfos;
    private AudioInfo mAudioInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);
        ButterKnife.bind(this);
        mPresenter = new CombinePresenter(this);
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
                    break;
                default:
                    break;
            }
        }
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
                new AsyncCombine().execute();
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

    class AsyncCombine extends AsyncTask<Void, Void, Void> {
        private ProgressDialog mProgressDialog;
        private String mVideoPath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(CombineActivity.this);
            mProgressDialog.setMessage(getString(R.string.msg_wait));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            File file = Environment.getExternalStorageDirectory();
            long millis = System.currentTimeMillis();
            mVideoPath = file + "/test" + millis + ".mp4";
            try {
                FFmpegFrameGrabber grabberImage = new FFmpegFrameGrabber(mImageInfos.get(0).getPath());
                FFmpegFrameGrabber grabberAudio = new FFmpegFrameGrabber(mAudioInfo.getPath());
                grabberImage.start();
                grabberAudio.start();
                FFmpegFrameRecorder recorder =
                    new FFmpegFrameRecorder(mVideoPath,
                        grabberImage.getImageWidth(),
                        grabberImage.getImageHeight(), 2);
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
                recorder.setFormat("mp4");
                recorder.setSampleRate(grabberAudio.getSampleRate());
                recorder.setVideoBitrate(30);
                Frame frame1, frame2 = null;
                recorder.start();
                while ((frame1 = grabberImage.grabFrame()) != null ||
                    (frame2 = grabberAudio.grabFrame()) != null) {
                    recorder.record(frame1);
                    recorder.record(frame2);
                }
                recorder.stop();
                grabberImage.stop();
                grabberAudio.stop();
            } catch (Exception ex) {
                Toast.makeText(CombineActivity.this, R.string.msg_error_combining,
                    Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            Toast.makeText(CombineActivity.this, R.string.msg_success, Toast.LENGTH_SHORT).show();
        }
    }
}
