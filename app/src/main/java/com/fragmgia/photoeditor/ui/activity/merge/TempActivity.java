package com.fragmgia.photoeditor.ui.activity.merge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.data.model.ImageInfo;
import com.fragmgia.photoeditor.ui.activity.subimage.SubImagesActivity;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.ui.widget.CustomImageView;
import com.fragmgia.photoeditor.util.ConstantManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 4/11/17.
 */
public class TempActivity extends BaseActivity implements CustomImageView.OnClickImageView {
    private static final int REQUEST_CODE = 1;
    @BindView(R.id.custom_image_view)
    CustomImageView mCustomImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE) {
                    ImageInfo imageInfo = (ImageInfo) data.
                        getSerializableExtra(ConstantManager.EXTRA_IMAGE_INFO);
                    Bitmap bitmap = BitmapFactory.decodeFile(imageInfo.getPath());
                    mCustomImageView.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }

    public void init() {
        mCustomImageView.setOnClickImageView(this);
    }

    @Override
    public void onClick() {
        Intent intent = new Intent(TempActivity.this, SubImagesActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
