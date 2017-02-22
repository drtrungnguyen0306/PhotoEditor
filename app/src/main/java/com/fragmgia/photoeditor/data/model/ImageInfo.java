package com.fragmgia.photoeditor.data.model;

import java.io.Serializable;

/**
 * Created by trungnguyens93gmail.com on 2/23/17.
 */
public class ImageInfo implements Serializable {
    private String mName;
    private String mPath;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }
}
