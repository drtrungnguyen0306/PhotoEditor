package com.fragmgia.photoeditor.ui.activity.effect;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.adapter.EffectAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 3/2/17.
 */
public class EffectActivity extends BaseActivity implements EffectContract.View {
    @BindView(R.id.image_effect)
    ImageView mEffectImageView;
    @BindView(R.id.recycle_view_effects)
    RecyclerView mEffectRecycleView;
    private Bitmap mBitmap;
    private EffectContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect);
        ButterKnife.bind(this);
        mPresenter = new EffectPresenter(this);
        mPresenter.start();
    }

    @Override
    public void start() {
    }

    @Override
    public void getImage() {
        mBitmap = FunctionActivity.sMainBitmap;
    }

    @Override
    public void showImage() {
        mEffectImageView.setImageBitmap(mBitmap);
    }

    @Override
    public void showEffects(List<Function> functions) {
        mEffectRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
            .HORIZONTAL, false));
        mEffectRecycleView.setAdapter(new EffectAdapter(this, mBitmap, functions, this));
    }

    @Override
    public void selectEffect(Bitmap bitmap) {
        mBitmap = bitmap;
        mEffectImageView.setImageBitmap(bitmap);
    }
}
