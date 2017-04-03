package com.fragmgia.photoeditor.ui.activity.merge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trungnguyens93gmail.com on 3/29/17.
 */
public class MergeActivity extends BaseActivity implements MergeContract.View {
    @BindView(R.id.card_view_merge_grid)
    CardView mGridCardView;
    @BindView(R.id.card_view_merge_frame)
    CardView mFrameCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void start() {
    }

    @OnClick({R.id.card_view_merge_grid, R.id.card_view_merge_frame})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.card_view_merge_grid:
                break;
            case R.id.card_view_merge_frame:
                break;
            default:
                break;
        }
    }
}
