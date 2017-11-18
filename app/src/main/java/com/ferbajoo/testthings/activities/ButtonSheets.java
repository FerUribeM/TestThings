package com.ferbajoo.testthings.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.interfaces.Foo;

/**
 * Created by
 *          feuribe on 17/11/2017.
 */
@Foo(name = "ButtonSheets", value = "Funcionalidad del Button Sheets", drawable = R.mipmap.ic_button_sheets)
public class ButtonSheets extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
