package com.fragmgia.photoeditor.ui.activity.shareimage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 5/9/17.
 */
public class ShareImageActivity extends AppCompatActivity implements ShareImageContract.View {
    @BindView(R.id.image_share)
    ImageView mShareImageView;
    @BindView(R.id.button_save_image)
    Button mSaveButton;
    @BindView(R.id.button_share_image)
    Button mShareButton;
    private ProgressDialog mProgressDialog;
    private String mImagePath;

    private ShareImageContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_image);
        ButterKnife.bind(this);
        setUpActionBar();
        mImagePath = getIntent().getStringExtra("path");
        mPresenter = new ShareImagePresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void start() {
        mProgressDialog = new ProgressDialog(ShareImageActivity.this);
        mProgressDialog.setMessage(getResources().getString(R.string.msg_saving));
        mProgressDialog.setCancelable(false);
    }

    public void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void showImage(Bitmap bitmap) {
        mShareImageView.setImageBitmap(bitmap);
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
    public void showResult(boolean result) {
        if (result) {
            Toast.makeText(this, R.string.msg_success_save_image_file, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, R.string.msg_error_save_image_file, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showShareIntent(Intent intent) {
        startActivity(Intent.createChooser(intent, "Another Activity"));
    }

    @OnClick({R.id.button_save_image, R.id.button_share_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save_image:
                mPresenter.save();
                break;
            case R.id.button_share_image:
                mPresenter.share(mImagePath);
                break;
            default:
                break;
        }
    }
}
