package com.fragmgia.photoeditor.ui.activity.subimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.images.ImagesContract;
import com.fragmgia.photoeditor.ui.adapter.ImageAdapter;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 4/17/17.
 */
public class SubImagesActivity extends AppCompatActivity implements ImagesContract.View {
    @BindView(R.id.recycle_view_images)
    RecyclerView mImagesRecycleView;
    private ImageAdapter mImageAdapter;
    private ImagesContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);
        mPresenter = new SubImagesPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadImages();
    }

    @Override
    public void start() {
        setUpActionBar();
        mImageAdapter = new ImageAdapter(this, this);
        mImagesRecycleView.setHasFixedSize(true);
        mImagesRecycleView.setLayoutManager(new GridLayoutManager(SubImagesActivity.this,
            ConstantManager.NUM_COL_OF_ROW_RECYCLE_VIEW));
        mImagesRecycleView.setAdapter(mImageAdapter);
    }

    @Override
    public void showImages(List<ImageInfo> imageInfoList) {
        mImageAdapter.setImageInfos(imageInfoList);
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectSingleImage(ImageInfo imageInfo) {
        Intent intent = new Intent();
        intent.putExtra(ConstantManager.EXTRA_IMAGE_INFO, imageInfo);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showMessageDialog() {
    }

    @Override
    public void showRequestPermission() {
    }

    public void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
