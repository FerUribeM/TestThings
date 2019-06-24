package com.ferbajoo.testthings.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;

@Foo(name = "LottieAnimationActivity", value = "Animaciones con lottie", drawable = R.drawable.ic_lottie_animation)
public class LottieAnimationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lottie_animation);
    }


}
