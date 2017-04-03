package com.fragmgia.photoeditor.data.model;

import android.content.Context;

/**
 * Created by trungnguyens93gmail.com on 4/3/17.
 */
public class Option {
    public static int sIconClick = 40;
    private int mMinHeight;
    private int mMinWidth;
    private int mClipHeight;

    public Option(Context context) {
        mMinHeight = dp2px(context, sIconClick);
        mMinWidth = dp2px(context, sIconClick);
        mClipHeight = dp2px(context, sIconClick);
    }

    public int getMinHeight() {
        return mMinHeight;
    }

    public int getMinWidth() {
        return mMinWidth;
    }

    public int getClipHeight() {
        return mClipHeight;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
