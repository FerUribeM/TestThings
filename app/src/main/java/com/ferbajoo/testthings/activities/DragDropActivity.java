package com.ferbajoo.testthings.activities;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

@Foo(name = "DragDropActivity", value = "Arrastrar y soltar vistas", drawable = R.mipmap.ic_drag_drop)
public class DragDropActivity extends AppCompatActivity implements View.OnLongClickListener {

    private ImageView container_trash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_drag_drop);

        ImageView trash = findViewById(R.id.trash);
        ImageView trash_bag = findViewById(R.id.trash_bag);
        ImageView trash_fish = findViewById(R.id.trash_fish);
        ImageView trash_bote = findViewById(R.id.trash_bote);
        container_trash = findViewById(R.id.container_trash);


        trash.setOnLongClickListener(this);
        trash_bag.setOnLongClickListener(this);
        trash_fish.setOnLongClickListener(this);
        trash_bote.setOnLongClickListener(this);

    }

    @Override
    public boolean onLongClick(View view) {
        String message = null;
        switch (view.getId()) {
            case R.id.trash:
                message = "Bolsa con basura";
                break;
            case R.id.trash_bag:
                message = "Basura sin nada";
                break;
            case R.id.trash_fish:
                message = "Desecho de pescado";
                break;
            case R.id.trash_bote:
                message = "Lata de refresco";
                break;
        }
        container_trash.setOnDragListener(new MyDragEventListener(view));
        if (message != null) {
            ClipData.Item item = new ClipData.Item(message);
            ClipData dragData = new ClipData(String.valueOf(view.getId()), new String[]{MIMETYPE_TEXT_PLAIN}, item);
            View.DragShadowBuilder shadowBuilder = new MyDragShadowBuilder((ImageView) view);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(dragData, shadowBuilder, null, 0);
            }
            return true;
        }
        return false;
    }

    private class MyDragShadowBuilder extends View.DragShadowBuilder {

        private Drawable shadow;

        MyDragShadowBuilder(ImageView trash) {
            super(trash);

            this.shadow = trash.getDrawable();
        }

        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            int width, height;

            width = getView().getWidth();
            height = getView().getHeight();
            shadow.setBounds(0, 0, width, height);
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }
    }

    private class MyDragEventListener implements View.OnDragListener {

        private View view;

        MyDragEventListener(View trash) {
            this.view = trash;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        view.setVisibility(View.GONE);
                        return true;
                    }
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:
                    // Ignore the event
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    // Ignore the event
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    // Ignore the event
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    Toast.makeText(DragDropActivity.this, "Depositado " + item.getText(), Toast.LENGTH_LONG).show();
                    v.setBackgroundColor(Color.TRANSPARENT);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    v.invalidate();

                    view.setVisibility(event.getResult() ? View.GONE : View.VISIBLE);
                    return true;
                default:
                    Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                    break;
            }
            return false;
        }
    }

}
