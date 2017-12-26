package com.ferbajoo.testthings.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.Utilities;

/**
 * Created by
 * feuribe on 16/11/2017.
 */
@Foo(name = "JSTestActivity", value = "Prueba para funciones JavaScript en Android", drawable = R.mipmap.ic_android_javascript)
public class JSTestActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_javascript);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Utilities.getToolbar(this, toolbar,getString(R.string.js_tittle));

        final WebView wv_animate_write = findViewById(R.id.wv_animate_write);
        wv_animate_write.getSettings().setJavaScriptEnabled(true);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wv_animate_write.loadUrl(getString(R.string.WRITE_ON_HTML));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
