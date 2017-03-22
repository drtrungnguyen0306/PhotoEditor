package com.fragmgia.photoeditor.ui.activity.adjust;

import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.activity.function.FunctionContract;
import com.fragmgia.photoeditor.ui.adapter.FunctionAdapter;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjustActivity extends BaseActivity implements FunctionContract.View,
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
        mPresenter.start();
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
    public void getImage() {
    }

    @Override
    public void showImage() {
        mBitmap = FunctionActivity.sMainBitmap;
        mAdjustImageView.setImageBitmap(mBitmap);
    }

    @Override
    public void showFunctions(List<Function> functions) {
        mAdjustRecycleView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        mAdjustRecycleView.setAdapter(new FunctionAdapter(this, functions, this));
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

    private void changeContrast(double value)
    {
        // image size
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, mBitmap.getConfig());
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);
        // color information
        int A, R, G, B;
        int pixel;
        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = mBitmap.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.green(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.blue(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        // return final image
        mBitmap = bmOut;
        mAdjustImageView.setImageBitmap(bmOut);
    }

    public void changeHUE(int value) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        Bitmap desBitmap = Bitmap.createBitmap(width, height, mBitmap.getConfig());
        int colorPixel;
        float[] hsv = new float[3];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                colorPixel = mBitmap.getPixel(x, y);
                Color.colorToHSV(colorPixel, hsv);
                hsv[0] += value;
                desBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(colorPixel), hsv));
            }
        }
        mBitmap = desBitmap;
        mAdjustImageView.setImageBitmap(desBitmap);
    }

    public void changeBrightness(int value) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, mBitmap.getConfig());
        int a, r, g, b;
        int pixel;
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                pixel = mBitmap.getPixel(x, y);
                a = Color.alpha(pixel);
                // red
                r = Color.red(pixel);
                r += value;
                if (r > 255) r = 255;
                else if (r < 0) r = 0;
                // green
                g = Color.green(pixel);
                g += value;
                if (g > 255) g = 255;
                else if (g < 0) g = 0;
                // blue
                b = Color.blue(pixel);
                b += value;
                if (b > 255) b = 255;
                else if (b < 0) b = 0;
                bmOut.setPixel(x, y, Color.argb(a, r, g, b));
            }
        }
        mBitmap = bmOut;
        mAdjustImageView.setImageBitmap(mBitmap);
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
                changeBrightness(mCurrentBrightnessValue);
                break;
            case ConstantManager.Adjusts.CONTRAST_FUNCTION:
                changeContrast(mCurrentContrastValue);
                break;
            case ConstantManager.Adjusts.HUE_FUNCTION:
                changeHUE(mCurrentHueValue);
                break;
            default:
                break;
        }
    }

    @Override
    public void reset() {
        super.reset();
        sIsChanged = false;
        mCurrentBrightnessValue = 0;
        mCurrentContrastValue = 0;
        mCurrentHueValue = 0;
    }
}
