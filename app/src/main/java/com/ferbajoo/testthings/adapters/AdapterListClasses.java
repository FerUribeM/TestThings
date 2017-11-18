package com.ferbajoo.testthings.adapters;

import android.content.ComponentName;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ferbajoo.testthings.databinding.ListClassesLayoutBinding;
import com.ferbajoo.testthings.models.ClassModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * feuribe on 17/11/2017.
 */

public class AdapterListClasses extends RecyclerView.Adapter<AdapterListClasses.AdapterListClassesViewHolder>{

    private List<ClassModel> classes = new ArrayList<>();

    public AdapterListClasses(List<ClassModel> classes) {
        this.classes = classes;
    }

    @Override
    public AdapterListClassesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListClassesLayoutBinding binding = ListClassesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterListClassesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(AdapterListClassesViewHolder holder, int position) {
        holder.binding.setClasses(classes.get(position));
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }


     class AdapterListClassesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ListClassesLayoutBinding binding;

         AdapterListClassesViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }


         @Override
         public void onClick(View view) {
             if (getAdapterPosition() > -1) {
                 String packages = view.getContext().getPackageName();
                 Intent intent = new Intent(Intent.ACTION_MAIN);
                 intent.setComponent(new ComponentName(packages, packages+ ".activities." +classes.get(getAdapterPosition()).getName()));
                 view.getContext().startActivity(intent);
             }
         }
     }
}
