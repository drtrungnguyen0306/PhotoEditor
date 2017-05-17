package com.fragmgia.photoeditor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ComposePathEffect;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;
import com.fragmgia.photoeditor.ui.activity.combine.CombineActivity;
import com.fragmgia.photoeditor.ui.activity.images.ImagesActivity;
import com.fragmgia.photoeditor.ui.activity.merge.MergeActivity;
import com.fragmgia.photoeditor.ui.activity.multiphoto.MultiImageActivity;
import com.fragmgia.photoeditor.util.ConstantManager;

import java.io.File;

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

    @Override
    protected void onStart() {
        super.onStart();
        createFolder(Environment.getExternalStorageDirectory().getPath(), ConstantManager.MAIN_FOLDER);
    }

    @OnClick({R.id.card_view_edit, R.id.card_view_video, R.id.card_view_merge})
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.card_view_edit:
                intent = new Intent(this, ImagesActivity.class);
                break;
            case R.id.card_view_merge:
                intent = new Intent(this, MergeActivity.class);
                break;
            case R.id.card_view_video:
                intent = new Intent(this, CombineActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) startActivity(intent);
    }

    public void createFolder(String path, String nameFile) {
        File folder = new File(path + File.separator + nameFile);
        if (!folder.exists()) {
            folder.mkdir();
        }
        switch (nameFile) {
            case ConstantManager.MAIN_FOLDER:
                createFolder(folder.getPath(), ConstantManager.IMAGES_FOLDER);
                createFolder(folder.getPath(), ConstantManager.VIDEOS_FOLDER);
                break;
            case ConstantManager.IMAGES_FOLDER:
            case ConstantManager.VIDEOS_FOLDER:
                break;
        }
    }
}
