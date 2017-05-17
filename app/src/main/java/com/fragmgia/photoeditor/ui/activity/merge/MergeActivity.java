package com.fragmgia.photoeditor.ui.activity.merge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Control;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.MainActivity;
import com.fragmgia.photoeditor.ui.activity.frame.FramesActivity;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.activity.selectedimage.SeletedImageActivity;
import com.fragmgia.photoeditor.ui.adapter.MergeImageAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.ui.widget.CustomMergeImage;
import com.fragmgia.photoeditor.util.ConstantManager;
import com.fragmgia.photoeditor.util.RequestPermissionUtils;
import com.fragmgia.photoeditor.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 3/29/17.
 */
public class MergeActivity extends BaseActivity implements MergeContract.View,
    CustomMergeImage.EventImageView {
    private static final int REQUEST_CODE = 1;
    @BindView(R.id.relative_root_imageview)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.recycle_view_type_form)
    RecyclerView mRecyclerView;
    private MergeContract.Presenter mPresenter;
    private CustomMergeImage mCustomMergeImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        ButterKnife.bind(this);
        mPresenter = new MergePresenter(this);
        mCustomMergeImage = new CustomMergeImage(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadControls();
    }

    @Override
    public void start() {
        mCustomMergeImage.setEvent(this);
    }

    @Override
    public void showControls(List<Control> controls) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
            .HORIZONTAL, false));
        mRecyclerView.setAdapter(new MergeImageAdapter(this, controls, this));
    }

    @Override
    public void selectControl(int position) {
        mRelativeLayout.removeAllViews();
        switch (position) {
            case ConstantManager.LAYOUT_OLD:
                mCustomMergeImage.setNumberLayout(ConstantManager.LAYOUT_OLD);
                break;
            case ConstantManager.LAYOUT_ONE:
                mCustomMergeImage.setNumberLayout(ConstantManager.LAYOUT_ONE);
                break;
            case ConstantManager.LAYOUT_TWO:
                mCustomMergeImage.setNumberLayout(ConstantManager.LAYOUT_TWO);
                break;
            case ConstantManager.LAYOUT_THREE:
                mCustomMergeImage.setNumberLayout(ConstantManager.LAYOUT_THREE);
                break;
            case ConstantManager.LAYOUT_FOUR:
                mCustomMergeImage.setNumberLayout(ConstantManager.LAYOUT_FOUR);
                break;
        }
        mRelativeLayout.addView(mCustomMergeImage);
    }

    @Override
    public void clickPickImage() {
        Intent intent = new Intent(MergeActivity.this, SeletedImageActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                        String path = data.getStringExtra(ConstantManager.EXTRA_IMAGE_INFO_PATH);
                        mCustomMergeImage.setBitmap(Util.convertPathToBitmap(path));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void accept() {
        Bitmap bitmap = Util.getBitmapFromCustomView(mCustomMergeImage);
        FunctionActivity.sMainBitmap = bitmap;
        Intent intent = new Intent(MergeActivity.this, FunctionActivity.class);
        startActivity(intent);
        super.accept();
    }
}
