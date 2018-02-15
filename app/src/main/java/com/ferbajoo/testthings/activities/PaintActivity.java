package com.ferbajoo.testthings.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.Utilities;

@Foo(name = "PaintActivity", value = "Pintar en layout", drawable = R.mipmap.ic_paint_layout)
public class PaintActivity extends AppCompatActivity implements View.OnTouchListener {

    private int corx, cory;
    private PaintView fondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_paint);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Utilities.getToolbar(this, toolbar,getString(R.string.paint_layout_tittle));

        corx = 100;
        cory = 100;

        RelativeLayout layout = findViewById(R.id.linear_canvas);
        fondo = new PaintView(this);
        fondo.setOnTouchListener(this);
        layout.addView(fondo);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return false;
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        corx = (int) motionEvent.getX();
        cory = (int) motionEvent.getY();
        fondo.invalidate();
        return true;
    }

    class PaintView extends View{

        public PaintView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(20);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(corx, cory,40, paint);
        }

    }

}
