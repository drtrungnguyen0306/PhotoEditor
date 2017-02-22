package com.fragmgia.photoeditor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.util.ConstantManager;

/**
 * Created by trungnguyens93gmail.com on 2/22/17.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        runThread();
    }

    public void runThread() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, ConstantManager.LONG_TIME_THREAD);
    }
}
