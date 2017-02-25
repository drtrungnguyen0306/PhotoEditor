package com.fragmgia.photoeditor.ui.activity.adjust;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by trungnguyens93gmail.com on 2/25/17.
 */
public class AdjustActivity extends AppCompatActivity implements AdjustContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void start() {
    }
}
