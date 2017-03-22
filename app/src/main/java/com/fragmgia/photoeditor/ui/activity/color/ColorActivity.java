package com.fragmgia.photoeditor.ui.activity.color;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.ui.widget.ColorImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 3/18/17.
 */
public class ColorActivity extends BaseActivity implements ColorContract.View,
    SeekBar.OnSeekBarChangeListener {
    private static int MAX_VALUE_SEEK_BAR = 255;
    @BindView(R.id.image_color)
    ColorImageView mColorImageView;
    @BindView(R.id.seek_bar_red_color)
    SeekBar mRedColorSeekBar;
    @BindView(R.id.seek_bar_green_color)
    SeekBar mGreenColorSeekBar;
    @BindView(R.id.seek_bar_blue_color)
    SeekBar mBlueColorSeekBar;
    @BindView(R.id.text_value_of_red_color)
    TextView mValueOfRedColorTextView;
    @BindView(R.id.text_value_of_green_color)
    TextView mValueOfGreenColorTextView;
    @BindView(R.id.text_value_of_blue_color)
    TextView mValueOfBlueColorTextView;
    private ColorContract.Presenter mPresenter;
    private Bitmap mColorBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        ButterKnife.bind(this);
        mPresenter = new ColorPresenter(this);
        mPresenter.start();
    }

    @Override
    public void start() {
        // set up ColorImageView
        mColorImageView.setDrawingCacheEnabled(true);
        mColorImageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mColorImageView.buildDrawingCache(true);
        // setMax
        mRedColorSeekBar.setMax(MAX_VALUE_SEEK_BAR);
        mGreenColorSeekBar.setMax(MAX_VALUE_SEEK_BAR);
        mBlueColorSeekBar.setMax(MAX_VALUE_SEEK_BAR);
        // setOnSeekBarChange
        mRedColorSeekBar.setOnSeekBarChangeListener(this);
        mGreenColorSeekBar.setOnSeekBarChangeListener(this);
        mBlueColorSeekBar.setOnSeekBarChangeListener(this);
        mColorImageView.setColorPaint(mRedColorSeekBar.getProgress(),
            mGreenColorSeekBar.getProgress(),
            mBlueColorSeekBar.getProgress());
        // setValueOfColor
        mValueOfRedColorTextView.setText(String.valueOf(mRedColorSeekBar.getProgress()));
        mValueOfGreenColorTextView.setText(String.valueOf(mGreenColorSeekBar.getProgress()));
        mValueOfBlueColorTextView.setText(String.valueOf(mBlueColorSeekBar.getProgress()));
    }

    @Override
    public void showImage() {
        mColorImageView.setImageBitmap(FunctionActivity.sMainBitmap);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_bar_red_color:
                mValueOfRedColorTextView.setText(String.valueOf(seekBar.getProgress()));
                break;
            case R.id.seek_bar_green_color:
                mValueOfGreenColorTextView.setText(String.valueOf(seekBar.getProgress()));
                break;
            case R.id.seek_bar_blue_color:
                mValueOfBlueColorTextView.setText(String.valueOf(seekBar.getProgress()));
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
        mColorImageView.setColorPaint(mRedColorSeekBar.getProgress(),
            mGreenColorSeekBar.getProgress(),
            mBlueColorSeekBar.getProgress());
    }

    @Override
    public void accept() {
        FunctionActivity.sMainBitmap = Bitmap.createBitmap(mColorImageView.getDrawingCache());
        mColorImageView.setDrawingCacheEnabled(false);
        super.accept();
    }
}
