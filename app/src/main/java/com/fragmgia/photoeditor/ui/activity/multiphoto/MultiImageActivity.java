package com.fragmgia.photoeditor.ui.activity.multiphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.adapter.MultiImageAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 3/12/17.
 */
public class MultiImageActivity extends BaseActivity implements MultiImageContract.View {
    @BindView(R.id.recycle_view_images)
    RecyclerView mImagesRecyclerView;
    private MultiImageContract.Presenter mPresenter;
    private MultiImageAdapter mMultiImageAdapter;
    private List<ImageInfo> mImageInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);
        mPresenter = new MultiImagePresenter(this);
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
        mMultiImageAdapter = new MultiImageAdapter(this, this);
        mImagesRecyclerView.setHasFixedSize(true);
        mImagesRecyclerView.setLayoutManager(new GridLayoutManager(this,
            ConstantManager.NUM_COL_OF_ROW_RECYCLE_VIEW));
        mImagesRecyclerView.setAdapter(mMultiImageAdapter);
        mImageInfos = new ArrayList<>();
    }

    @Override
    public void showImages(List<ImageInfo> imageInfos) {
        mMultiImageAdapter.setImageInfos(imageInfos);
        mMultiImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void checkItem(ImageInfo imageInfo) {
        mImageInfos.add(imageInfo);
    }

    @Override
    public void uncheckItem(ImageInfo imageInfo) {
        mImageInfos.remove(imageInfo);
    }

    @Override
    public void accept() {
        Intent intent = new Intent();
        intent.putExtra(ConstantManager.EXTRA_LIST_IMAGE_INFO, (Serializable) mImageInfos);
        setResult(Activity.RESULT_OK, intent);
        super.accept();
    }
}
