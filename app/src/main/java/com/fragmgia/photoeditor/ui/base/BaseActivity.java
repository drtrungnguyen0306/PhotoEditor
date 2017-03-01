package com.fragmgia.photoeditor.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fragmgia.photoeditor.R;

public class BaseActivity extends AppCompatActivity {
    public static boolean sIsChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActionBar();
        sIsChanged = false;
    }

    public void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            checkIsChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkIsChanged() {
        if (sIsChanged) showDiscardMessage();
        else finish();
    }

    public void reset() {
    }

    public void showDiscardMessage() {
        new AlertDialog.Builder(this)
            .setMessage(getString(R.string.msg_discard_changes))
            .setPositiveButton(R.string.action_discard, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reset();
                    finish();
                }
            })
            .setNegativeButton(R.string.action_cancel, null)
            .create()
            .show();
    }
}
