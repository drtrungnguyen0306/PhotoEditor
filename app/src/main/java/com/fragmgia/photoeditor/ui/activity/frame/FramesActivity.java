package com.fragmgia.photoeditor.ui.activity.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.ui.activity.merge.TempActivity;
import com.fragmgia.photoeditor.ui.base.BaseActivity;
import com.fragmgia.photoeditor.ui.widget.CustomImageView;

import org.xml.sax.helpers.LocatorImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 4/17/17.
 */
public class FramesActivity extends BaseActivity {
    private static final String TAG = "FramesActivity";
    @BindView(R.id.card_view_custom_image_view)
    CardView mCustomImageCardView;
    @BindView(R.id.card_view_temp)
    CardView mTempCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frames);
        ButterKnife.bind(this);
        Log.i(TAG, "onCreate");
    }

    @OnClick({R.id.card_view_custom_image_view, R.id.card_view_temp})
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.card_view_custom_image_view:
                intent = new Intent(FramesActivity.this, TempActivity.class);
                break;
            case R.id.card_view_temp:
                break;
            default:
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
