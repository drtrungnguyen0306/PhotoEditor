package com.fragmgia.photoeditor.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 2/23/17.
 */
public class MainActivity extends Activity {
    @BindView(R.id.card_view_edit)
    CardView mEditCardView;
    @BindView(R.id.card_view_merge)
    CardView mMergeCardView;
    @BindView(R.id.card_view_video)
    CardView mVideoCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.card_view_edit, R.id.card_view_merge, R.id.card_view_video})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view_edit:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_view_merge:
                Toast.makeText(this, "Merge", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_view_video:
                Toast.makeText(this, "Video", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
