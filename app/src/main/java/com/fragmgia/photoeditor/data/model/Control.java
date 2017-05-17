package com.fragmgia.photoeditor.data.model;

/**
 * Created by trungnguyens93gmail.com on 5/17/17.
 */
public class Control {
    private int mImage;
    private String mTitle;

    public Control(int image, String title) {
        mImage = image;
        mTitle = title;
    }

    public int getImage() {
        return mImage;
    }

    public String getTitle() {
        return mTitle;
    }
}
