package com.fragmgia.photoeditor.ui.activity.shareimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.util.ConstantManager;
import com.fragmgia.photoeditor.util.DateTimFormat;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by trungnguyens93gmail.com on 5/9/17.
 */
public class ShareImagePresenter implements ShareImageContract.Presenter {
    private ShareImageContract.View mView;
    private Bitmap mBitmap;

    public ShareImagePresenter(ShareImageContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadImage() {
        mBitmap = FunctionActivity.sMainBitmap;
        mView.showImage(mBitmap);
    }

    @Override
    public void save() {
        new SaveAsyncTask().execute(mBitmap);
    }

    @Override
    public void share(String path) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Send Image");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
        mView.showShareIntent(intent);
    }

    class SaveAsyncTask extends AsyncTask<Bitmap, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.showDialog();
        }

        @Override
        protected Boolean doInBackground(Bitmap... params) {
            Bitmap bitmap = params[0];
            String currentTime = DateTimFormat.getCurrentTime();
            String outputFile = currentTime + ConstantManager.TYPE_OF_IMAGE;
            File imageFile = new File(Environment.getExternalStorageDirectory() +
                File.separator + ConstantManager.MAIN_FOLDER + File.separator +
                ConstantManager.IMAGES_FOLDER, outputFile);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, ConstantManager.QUALYTY_IMAGE,
                    fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mView.dismissDialog();
            mView.showResult(aBoolean);
        }
    }
}
