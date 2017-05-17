package com.fragmgia.photoeditor.ui.activity.selectedimage;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.merge.MergeActivity;
import com.fragmgia.photoeditor.ui.adapter.SelectedImageAdapter;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 5/17/17.
 */
public class SeletedImageActivity extends AppCompatActivity implements SelectedImageContract.View {
    @BindView(R.id.recycle_view_images)
    RecyclerView mRecyclerView;
    private SelectedImageContract.Presenter mPresenter;
    private SelectedImageAdapter mImageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);
        mPresenter = new SelectedImagePresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkReadExternalPermission();
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
        mImageAdapter = new SelectedImageAdapter(this, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(SeletedImageActivity.this,
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
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantManager.EXTRA_IMAGE_INFO_PATH, imageInfo.getPath());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
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
        ActivityCompat.requestPermissions(SeletedImageActivity.this,
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
}
