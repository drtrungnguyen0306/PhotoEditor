package com.fragmgia.photoeditor.ui.activity.adjust;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.ui.adapter.AdjustAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjustActivity extends BaseActivity implements AdjustContract.View,
    SeekBar.OnSeekBarChangeListener {
    private static int mCurrentBrightnessValue = 0;
    private static int mCurrentContrastValue = 0;
    private static int mCurrentHueValue = 0;
    @BindView(R.id.image_adjust)
    ImageView mAdjustImageView;
    @BindView(R.id.text_adjust_name)
    TextView mAdjustName;
    @BindView(R.id.seek_bar_adjust)
    SeekBar mAdjustSeekBar;
    @BindView(R.id.text_adjust_value)
    TextView mAdjustValue;
    @BindView(R.id.recycle_view_adjust)
    RecyclerView mAdjustRecycleView;
    protected AdjustPresenter mPresenter;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust);
        ButterKnife.bind(this);
        mPresenter = new AdjustPresenter(this);
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
        mPresenter.loadFunctions();
    }

    @Override
    public void start() {
        mAdjustName.setText(ConstantManager.Adjusts.BRIGHTNESS_FUNCTION);
        mAdjustSeekBar.setMax(200);
        mAdjustSeekBar.setProgress(mCurrentBrightnessValue + 100);
        mAdjustSeekBar.setOnSeekBarChangeListener(this);
        mAdjustValue.setText(String.valueOf(mCurrentBrightnessValue));
    }

    @Override
    public void showImage(Bitmap bitmap) {
        mBitmap = bitmap;
        mAdjustImageView.setImageBitmap(bitmap);
    }

    @Override
    public void showFunctions(List<Function> functions) {
        mAdjustRecycleView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        mAdjustRecycleView.setAdapter(new AdjustAdapter(this, functions, this));
    }

    @Override
    public void selectFunction(Function function) {
        switch (function.getName()) {
            case ConstantManager.Adjusts.BRIGHTNESS_FUNCTION:
                mAdjustName.setText(String.valueOf(ConstantManager.Adjusts.BRIGHTNESS_FUNCTION));
                mAdjustSeekBar.setProgress(mCurrentBrightnessValue + 100);
                mAdjustValue.setText(String.valueOf(mCurrentBrightnessValue));
                break;
            case ConstantManager.Adjusts.CONTRAST_FUNCTION:
                mAdjustName.setText(String.valueOf(ConstantManager.Adjusts.CONTRAST_FUNCTION));
                mAdjustSeekBar.setProgress(mCurrentContrastValue + 100);
                mAdjustValue.setText(String.valueOf(mCurrentContrastValue));
                break;
            case ConstantManager.Adjusts.HUE_FUNCTION:
                mAdjustName.setText(String.valueOf(ConstantManager.Adjusts.HUE_FUNCTION));
                mAdjustSeekBar.setProgress(mCurrentHueValue + 100);
                mAdjustValue.setText(String.valueOf(mCurrentHueValue));
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        sIsChanged = true;
        String adjustName = mAdjustName.getText().toString();
        switch (adjustName) {
            case ConstantManager.Adjusts.BRIGHTNESS_FUNCTION:
                mCurrentBrightnessValue = progress - 100;
                mAdjustValue.setText(String.valueOf(mCurrentBrightnessValue));
                break;
            case ConstantManager.Adjusts.CONTRAST_FUNCTION:
                mCurrentContrastValue = progress - 100;
                mAdjustValue.setText(String.valueOf(mCurrentContrastValue));
                break;
            case ConstantManager.Adjusts.HUE_FUNCTION:
                mCurrentHueValue = progress - 100;
                mAdjustValue.setText(String.valueOf(mCurrentHueValue));
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        String adjustName = mAdjustName.getText().toString();
        switch (adjustName) {
            case ConstantManager.Adjusts.BRIGHTNESS_FUNCTION:
                mBitmap = mPresenter.changeBrightness(mBitmap, mCurrentBrightnessValue);
                break;
            case ConstantManager.Adjusts.CONTRAST_FUNCTION:
                mBitmap = mPresenter.changeContrast(mBitmap, mCurrentContrastValue);
                break;
            case ConstantManager.Adjusts.HUE_FUNCTION:
                mBitmap = mPresenter.changeHUE(mBitmap, mCurrentHueValue);
                break;
            default:
                break;
        }
        mAdjustImageView.setImageBitmap(mBitmap);
    }

    @Override
    public void reset() {
        super.reset();
        sIsChanged = false;
        mCurrentBrightnessValue = 0;
        mCurrentContrastValue = 0;
        mCurrentHueValue = 0;
    }

    @Override
    public void accept() {
        mPresenter.save(mBitmap);
        super.accept();
    }
}
