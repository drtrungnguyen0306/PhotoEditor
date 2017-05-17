package com.fragmgia.photoeditor.ui.activity.combine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.fragmgia.photoeditor.data.model.AudioInfo;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.audio.AudioActivity;
import com.fragmgia.photoeditor.util.ConstantManager;
import com.fragmgia.photoeditor.util.DateTimFormat;
import com.fragmgia.photoeditor.util.FileManager;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by trungnguyens93gmail.com on 3/10/17.
 */
public class CombinePresenter implements CombineContact.Presenter {
    private static final String TAG = "CombinePresenter";
    private CombineContact.View mView;
    private FFmpeg mFFmpeg;
    private String mOutputVideoFile;
    private String mCropAudioFile;
    private List<ImageInfo> mImageInfos;
    private AudioInfo mAudioInfo;

    public CombinePresenter(CombineContact.View view) {
        mView = view;
        loadFFmpeg((Activity) view);
    }

    public void loadFFmpeg(Context context) {
        mFFmpeg = FFmpeg.getInstance(context);
        try {
            mFFmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onSuccess() {
                    super.onSuccess();
                }

                @Override
                public void onFailure() {
                    super.onFailure();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });
        } catch (FFmpegNotSupportedException exception) {
        }
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void convertVideo(List<ImageInfo> imageInfos, AudioInfo audioInfo) {
        mImageInfos = imageInfos;
        mAudioInfo = audioInfo;
        new CombineAsync().execute();
    }

    public String[] scaleImage(String imagePath, int width, int height) {
        File imageFile = new File(imagePath);
        File foleder = new File(Environment.getExternalStorageDirectory() + File.separator +
            ConstantManager.TEMP_FOLDER);
        if (!foleder.exists()) {
            boolean success = true;
            success = foleder.mkdir();
            if (!success) return null;
        }
        List<String> listQuery = new ArrayList<>();
        listQuery.add("-i");
        listQuery.add(imageFile.getPath());
        listQuery.add("-vf");
        listQuery.add("scale=" + width + ":" + height + ",setsar=1:1");
        listQuery.add(foleder + File.separator + imageFile.getName());
        return listQuery.toArray(new String[listQuery.size()]);
    }

    public String[] filterComplexQuery(List<ImageInfo> imageInfos) {
//        mOutputVideoFile = Environment.getExternalStoragePublicDirectory(
//            Environment.DIRECTORY_DOWNLOADS + File.separator + DateTimFormat.getCurrentTime() +
//                ConstantManager.TYPE_OF_MUSIC).toString();
        mOutputVideoFile = DateTimFormat.getCurrentTime() + ConstantManager.TYPE_OF_VIDEO;
        List<String> listQuery = new ArrayList<>();
        for (ImageInfo imageInfo : imageInfos) {
            File imageFile = new File(imageInfo.getPath());
            String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + ConstantManager.TEMP_FOLDER + File.separator + imageFile.getName();
            listQuery.add("-loop");
            listQuery.add("1");
            listQuery.add("-t");
            listQuery.add("4");
            listQuery.add("-i");
            listQuery.add(imagePath);
        }
        listQuery.add("-filter_complex");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[0:v]fade=t=out:st=");
        stringBuilder.append(String.valueOf(imageInfos.size() - 1));
        stringBuilder.append(":d=1[v0];");
        for (int i=1; i<imageInfos.size(); i++) {
            stringBuilder.append("[");
            stringBuilder.append(String.valueOf(i));
            stringBuilder.append(":v]fade=t=in:st=0:d=1,fade=t=out:st=4:d=1[v");
            stringBuilder.append(String.valueOf(i));
            stringBuilder.append("];");
        }
        for (int i=0; i<imageInfos.size(); i++) {
            stringBuilder.append("[v");
            stringBuilder.append(String.valueOf(i));
            stringBuilder.append("]");
        }
        stringBuilder.append("concat=n=");
        stringBuilder.append(String.valueOf(imageInfos.size()));
        stringBuilder.append(":v=1:a=0,format=yuv420p[v]");
        listQuery.add(String.valueOf(stringBuilder));
        listQuery.add("-map");
        listQuery.add("[v]");
        listQuery.add(Environment.getExternalStoragePublicDirectory(Environment
            .DIRECTORY_DOWNLOADS + File.separator + mOutputVideoFile).getPath());
        return listQuery.toArray(new String[listQuery.size()]);
    }

    public String[] cropVideo(String audioPath, String startTime, int lengthOfMusic) {
        mCropAudioFile = DateTimFormat.getCurrentTime() + ConstantManager.TYPE_OF_AUDIO;
        List<String> listQuery = new ArrayList<>();
        listQuery.add("-ss");
        listQuery.add(startTime);
        listQuery.add("-t");
        listQuery.add(String.valueOf(lengthOfMusic));
        listQuery.add("-i");
        listQuery.add(audioPath);
        listQuery.add("-acodec");
        listQuery.add("copy");
        listQuery.add(Environment.getExternalStoragePublicDirectory(Environment
            .DIRECTORY_DOWNLOADS + File.separator + mCropAudioFile).getPath());
        return listQuery.toArray(new String[listQuery.size()]);
    }

    public String[] combineVideoAndAudio(String audioPath) {
        List<String> listQuery = new ArrayList<>();
        listQuery.add("-i");
        listQuery.add(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS + File.separator + mOutputVideoFile).getPath());
        listQuery.add("-i");
        listQuery.add(Environment.getExternalStoragePublicDirectory(Environment
            .DIRECTORY_DOWNLOADS + File.separator + audioPath).getPath());
        listQuery.add("-c:v");
        listQuery.add("copy");
        listQuery.add("-c:a");
        listQuery.add("aac");
        listQuery.add("-strict");
        listQuery.add("experimental");
        listQuery.add(Environment.getExternalStorageDirectory() + File.separator +
            ConstantManager.MAIN_FOLDER + File.separator + ConstantManager.VIDEOS_FOLDER +
            File.separator + mOutputVideoFile);
        return listQuery.toArray(new String[listQuery.size()]);
    }

    public void executeFFmpeg(final String[] query, final boolean isShowDialog) {
        try {
            mFFmpeg.execute(query, new FFmpegExecuteResponseHandler() {
                @Override
                public void onStart() {
                }

                @Override
                public void onProgress(String message) {
                }

                @Override
                public void onFailure(String message) {
                }

                @Override
                public void onSuccess(String message) {
                }

                @Override
                public void onFinish() {
                    if (isShowDialog) {
                        FileManager
                            .deleteDirectory(new File(Environment.getExternalStorageDirectory() +
                            File.separator + ConstantManager.TEMP_FOLDER));
                        FileManager.deleteDirectory(new File(Environment
                            .getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS +
                                File.separator + mOutputVideoFile).getPath()));
                        FileManager.deleteDirectory(new File(Environment
                            .getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS +
                                File.separator + mCropAudioFile).getPath()));
                        mView.dismissDialog();
                        mView.navigateToActivity(Environment.getExternalStorageDirectory() +
                            File.separator + ConstantManager.MAIN_FOLDER + File.separator +
                            ConstantManager.VIDEOS_FOLDER + File.separator + mOutputVideoFile);
                    }
                }
            });
        } catch (FFmpegCommandAlreadyRunningException exception) {
            exception.printStackTrace();
        }
    }

    public String getDuration(String videoPath, int numOfImages) {
        MediaPlayer mediaPlayer = MediaPlayer.create((Activity) mView, Uri.parse(videoPath));
        int duration = (mediaPlayer.getDuration()/2) - (numOfImages * 2 * 1000);
        return String.format(Locale.US, "%d:%d",
            TimeUnit.MILLISECONDS.toMinutes(duration),
            TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    class CombineAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (ImageInfo imageInfo : mImageInfos) {
                String[] query = scaleImage(imageInfo.getPath(), 640, 400);
                if (query != null) executeFFmpeg(query, false);
                else return null;
            }
            executeFFmpeg(filterComplexQuery(mImageInfos), false);
            String startTime = getDuration(mAudioInfo.getPath(), mImageInfos.size());
            executeFFmpeg(cropVideo(mAudioInfo.getPath(), startTime, mImageInfos.size() * 4), false);
            executeFFmpeg(combineVideoAndAudio(mCropAudioFile), true);
            return null;
        }
    }
}
