package com.fragmgia.photoeditor.ui.activity.function;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.color.ColorActivity;
import com.fragmgia.photoeditor.ui.activity.crop.CropActivity;
import com.fragmgia.photoeditor.ui.activity.effect.EffectActivity;
import com.fragmgia.photoeditor.ui.activity.shareimage.ShareImageActivity;
import com.fragmgia.photoeditor.ui.activity.sticker.StickerActivity;
import com.fragmgia.photoeditor.ui.adapter.FunctionAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class FunctionActivity extends BaseActivity implements FunctionContract.View {
    private FunctionContract.Presenter mPresenter;
    public static Bitmap sMainBitmap;
    @BindView(R.id.image_edit)
    ImageView mEditImageView;
    @BindView(R.id.recycle_view_functions)
    RecyclerView mFunctionRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);
        ButterKnife.bind(this);
        initImage();
        mPresenter = new FunctionPresenter(this);
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
        mPresenter.loadFunction();
    }

    @Override
    public void start() {
    }

    @Override
    public void showImage() {
        mEditImageView.setImageBitmap(sMainBitmap);
    }

    @Override
    public void showFunctions(List<Function> functions) {
        mFunctionRecycleView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        mFunctionRecycleView.setAdapter(new FunctionAdapter(this, functions, this));
    }

    @Override
    public void selectFunction(Function function) {
        Intent intent = mPresenter.getIntent(function);
        if (intent != null) startActivity(intent);
    }

    @Override
    public void accept() {
        String path = mPresenter.save(sMainBitmap);
        Intent intent = new Intent(FunctionActivity.this, ShareImageActivity.class);
        intent.putExtra("path", path);
        startActivity(intent);
    }

    public void initImage() {
        if (getIntent() == null) return;
        String typeOfImage = getIntent().getStringExtra(ConstantManager.EXTRA_TYPE_OF_IMAGE);
        if (typeOfImage != null) {
            switch (typeOfImage) {
                case ConstantManager.EXTRA_IMAGE_INFO:
                    ImageInfo imageInfo = (ImageInfo) getIntent()
                        .getSerializableExtra(ConstantManager.EXTRA_IMAGE_INFO);
                    sMainBitmap = BitmapFactory.decodeFile(imageInfo.getPath());
                    break;
                case ConstantManager.EXTRA_BITMAP:
                    sMainBitmap = getIntent().getParcelableExtra(ConstantManager.EXTRA_BITMAP);
                    break;
                default:
                    break;
            }
        }
    }
}
