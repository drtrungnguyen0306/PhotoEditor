package com.fragmgia.photoeditor.ui.activity.function;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Environment;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.source.local.FunctionsDataSource;
import com.fragmgia.photoeditor.ui.activity.adjust.AdjustActivity;
import com.fragmgia.photoeditor.ui.activity.color.ColorActivity;
import com.fragmgia.photoeditor.ui.activity.crop.CropActivity;
import com.fragmgia.photoeditor.ui.activity.effect.EffectActivity;
import com.fragmgia.photoeditor.ui.activity.sticker.StickerActivity;
import com.fragmgia.photoeditor.util.ConstantManager;
import com.fragmgia.photoeditor.util.DateTimFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class FunctionPresenter implements FunctionContract.Presenter {
    private FunctionContract.View mView;
    private String mPathImage;

    public FunctionPresenter(FunctionContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadImage() {
        mView.showImage();
    }

    @Override
    public void loadFunction() {
        List<Function> functions = FunctionsDataSource.getFunctions();
        mView.showFunctions(functions);
    }

    @Override
    public Intent getIntent(Function function) {
        Intent intent = null;
        switch (function.getName()) {
            case ConstantManager.Functions.EFFECT_FUNTION:
                intent = new Intent((Activity) mView, EffectActivity.class);
                break;
            case ConstantManager.Functions.CROP_FUNCTION:
                intent = new Intent((Activity) mView, CropActivity.class);
                break;
            case ConstantManager.Functions.COLOR_FUNCTION:
                intent = new Intent((Activity) mView, ColorActivity.class);
                break;
            case ConstantManager.Functions.ADJUST_FUNCTION:
                intent = new Intent((Activity) mView, AdjustActivity.class);
                break;
            default:
                break;
        }
        return intent;
    }

    @Override
    public String save(Bitmap bitmap) {
        String currentTime = DateTimFormat.getCurrentTime();
        String outputFile = currentTime + ConstantManager.TYPE_OF_IMAGE;
        File imageFile = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS), outputFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, ConstantManager.QUALYTY_IMAGE,
                fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception ex) {
        }
        return imageFile.getPath();
    }
}
