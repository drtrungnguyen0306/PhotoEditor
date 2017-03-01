package com.fragmgia.photoeditor.ui.activity.images;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.adapter.ImageAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesActivity extends BaseActivity implements ImagesContract.View {
    @BindView(R.id.recycle_view_images)
    RecyclerView mRecyclerView;
    private ImagesPresenter mPresenter;
    private ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);
        mPresenter = new ImagesPresenter(this);
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkReadExternalPermission();
    }

    @Override
    public void start() {
        mImageAdapter = new ImageAdapter(this, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(ImagesActivity.this,
            ConstantManager.NUM_COL_OF_ROW_RECYCLE_VIEW));
        mRecyclerView.setAdapter(mImageAdapter);
    }

    @Override
    public void showImages(List<ImageInfo> imageInfos) {
        mImageAdapter.setImageInfos(imageInfos);
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectSingleImage(ImageInfo imageInfo) {
        if (imageInfo.getName().equals(ConstantManager.CAPTURE_IMAGE)) captureImage();
        else {
            Intent intent = new Intent(ImagesActivity.this, FunctionActivity.class);
            intent.putExtra(ConstantManager.EXTRA_TYPE_OF_IMAGE, ConstantManager.EXTRA_IMAGE_INFO);
            intent.putExtra(ConstantManager.EXTRA_IMAGE_INFO, imageInfo);
            startActivity(intent);
        }
    }

    @Override
    public void showMessageDialog() {
        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_request_permission))
            .setMessage(getString(R.string.msg_request_permission))
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showRequestPermission();
                }
            })
            .setNegativeButton(android.R.string.no, null)
            .create()
            .show();
    }

    @Override
    public void showRequestPermission() {
        ActivityCompat.requestPermissions(ImagesActivity.this,
            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
            ConstantManager.PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ConstantManager.PERMISSION_REQUEST_CODE &&
            grantResults.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter.loadImages();
        }
    }

    public void captureImage() {
        Intent capImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (capImg.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(capImg, ConstantManager.REQUEST_TAKE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantManager.REQUEST_TAKE_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get(ConstantManager.EXTRA_BITMAP_CAPTURE);

        }
    }
}
