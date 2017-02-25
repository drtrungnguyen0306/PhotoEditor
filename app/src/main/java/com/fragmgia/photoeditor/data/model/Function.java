package com.fragmgia.photoeditor.data.model;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class Function {
    private int mImageId;
    private String mName;

    public Function(int imageId, String name) {
        mImageId = imageId;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }
}
