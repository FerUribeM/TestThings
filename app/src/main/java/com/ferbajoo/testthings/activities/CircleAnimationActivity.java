package com.ferbajoo.testthings.activities;

import android.animation.Animator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.Utilities;

/**
 * Created by
 * feuribe on 29/11/2017.
 */
@Foo(name = "CircleAnimationActivity", value = "Animacion circular en android", drawable = R.drawable.circle_animation)
public class CircleAnimationActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView[] mFloats;
    private Point[] mPoints = {
            new Point(-450, -0),
            new Point(-340, -370),
            new Point(-0, -480),
            new Point(360, -340),
            new Point(450, 0),
            new Point(370, 350),
            new Point(2, 510),
            new Point(-350, 350)

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_circle_animation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Utilities.getToolbar(this, toolbar,getString(R.string.circle_animation_tittle));

        imageView = findViewById(R.id.iv_circle);
        mFloats = new ImageView[]{
                findViewById(R.id.fab_child_one),
                findViewById(R.id.fab_child_two),
                findViewById(R.id.fab_child_three),
                findViewById(R.id.fab_child_four),
                findViewById(R.id.fab_child_five),
                findViewById(R.id.fab_child_six),
                findViewById(R.id.fab_child_seven),
                findViewById(R.id.fab_child_eight)
        };
        findViewById(R.id.fab_father).setOnClickListener(float_click);

        startCircleAnimation(0, false);
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

    private final View.OnClickListener float_click = new View.OnClickListener() {
        private boolean state = true;

        @Override
        public void onClick(View v) {
            startCircleAnimation(0, state);
            state = !state;
        }
    };

    public void startCircleAnimation(final int position, final boolean state) {
        mFloats[position].setVisibility(View.VISIBLE);
        mFloats[position].clearAnimation();
        mFloats[position].animate()
                .translationY(!state ? mPoints[position].y : 0)
                .translationX(!state ? mPoints[position].x : 0)
                .alpha(!state ? 1 : 0)
                .scaleX(!state ? 1 : 0)
                .scaleY(!state ? 1 : 0)
                .setDuration(500)
                .setListener(new AbstractAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (position <= (mFloats.length - 2)) {
                            startCircleAnimation(position + 1, state);
                        }

                    }
                })
                .start();

    }


    abstract class AbstractAnimatorListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

}
