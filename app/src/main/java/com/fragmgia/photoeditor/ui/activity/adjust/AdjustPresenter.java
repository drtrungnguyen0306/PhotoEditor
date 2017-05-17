package com.fragmgia.photoeditor.ui.activity.adjust;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.fragmgia.photoeditor.data.model.Function;
import com.fragmgia.photoeditor.data.source.local.FunctionsDataSource;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.activity.function.FunctionContract;
import com.fragmgia.photoeditor.ui.activity.function.FunctionPresenter;

import java.util.List;

/**
 * Created by trungnguyens93gmail.com on 2/26/17.
 */
public class AdjustPresenter implements AdjustContract.Presenter {
    private AdjustContract.View mView;

    public AdjustPresenter(AdjustContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.start();
    }

    @Override
    public void loadImage() {
        Bitmap bitmap = FunctionActivity.sMainBitmap;
        mView.showImage(bitmap);
    }

    @Override
    public void loadFunctions() {
        List<Function> functions = FunctionsDataSource.getAdjustFunctions();
        mView.showFunctions(functions);
    }

    @Override
    public Bitmap changeContrast(Bitmap bmIn, double value) {
        // image size
        int width = bmIn.getWidth();
        int height = bmIn.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, bmIn.getConfig());
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);
        // color information
        int A, R, G, B;
        int pixel;
        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = bmIn.getPixel(x, y);
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
        return bmOut;
    }

    @Override
    public Bitmap changeHUE(Bitmap bmIn, int value) {
        int width = bmIn.getWidth();
        int height = bmIn.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, bmIn.getConfig());
        int colorPixel;
        float[] hsv = new float[3];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                colorPixel = bmIn.getPixel(x, y);
                Color.colorToHSV(colorPixel, hsv);
                hsv[0] += value;
                bmOut.setPixel(x, y, Color.HSVToColor(Color.alpha(colorPixel), hsv));
            }
        }
        return bmOut;
    }

    @Override
    public Bitmap changeBrightness(Bitmap bmIn, int value) {
        int width = bmIn.getWidth();
        int height = bmIn.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, bmIn.getConfig());
        int a, r, g, b;
        int pixel;
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                pixel = bmIn.getPixel(x, y);
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
        return bmOut;
    }

    @Override
    public void save(Bitmap bitmap) {
        FunctionActivity.sMainBitmap = bitmap;
    }
}
