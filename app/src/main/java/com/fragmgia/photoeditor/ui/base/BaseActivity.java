package com.fragmgia.photoeditor.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fragmgia.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    public static boolean sIsChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partial_toolbar);
        setUpActionBar();
        sIsChanged = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                checkIsChanged();
                break;
            case R.id.menu_item_done:
                accept();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void checkIsChanged() {
        if (sIsChanged) showDiscardMessage();
        else finish();
    }

    public void accept() {
        finish();
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
