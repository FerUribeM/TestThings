package com.ferbajoo.testthings.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.Utilities;

/**
 * Created by
 * feuribe on 17/11/2017.
 */
@Foo(name = "BottonSheetsActivity", value = "Funcionalidad del Botton Sheets en Android", drawable = R.mipmap.ic_button_sheets)
public class BottonSheetsActivity extends AppCompatActivity {

    private BottomSheetBehavior behavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_botton_sheets);

        Toolbar toolbar = findViewById(R.id.toolbar_button);
        Utilities.getToolbar(this, toolbar, getString(R.string.bottom_sheet));

        View bottom_sheets = findViewById(R.id.desing_botton);
        behavior = BottomSheetBehavior.from(bottom_sheets);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_botton_sheet, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.it_menu:
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
        }
        return true;
    }

}
