package com.ferbajoo.testthings.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;

/**
 * Created by
 *          feuribe on 16/11/2017.
 */
//@Foo(name = "JSActivityTest", value = "Prueba para funciones JavaScript en Android", drawable = R.mipmap.ic_android_javascript)
@Foo(name = "JSActivityTest", value = "Prueba para funciones JavaScript en Android", drawable = R.mipmap.ic_android_javascript)
public class JSActivityTest extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_javascript);

    }
}
