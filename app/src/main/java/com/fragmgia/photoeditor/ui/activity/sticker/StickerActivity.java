package com.fragmgia.photoeditor.ui.activity.sticker;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.ui.activity.function.FunctionActivity;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.ui.widget.SubImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 4/5/17.
 */
public class StickerActivity extends BaseActivity {
    @BindView(R.id.sub_image_view_test)
    SubImageView mSubImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = FunctionActivity.sMainBitmap;
        mSubImageView.setImageBitmap(bitmap);

    }
}
