package com.ferbajoo.testthings.models;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by
 * feuribe on 17/11/2017.
 */

public class ClassModel implements Serializable{

    private String name;

    private String description;

    private int drawable;

    public ClassModel(String name, String description, int drawable) {
        this.name = name;
        this.description = description;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    @BindingAdapter({"load_image"})
    public static void loadImage(ImageView view, int drawable){
        view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), drawable));
    }

}
