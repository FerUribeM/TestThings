package com.ferbajoo.testthings.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ferbajoo.testthings.GlobalClasses;
import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.adapters.AdapterListClasses;
import com.ferbajoo.testthings.configuration.Configuration;
import com.ferbajoo.testthings.models.ClassModel;

import java.util.ArrayList;

/**
*ejemplo de comentario
*/
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerActivity;
    private ArrayList<ClassModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerActivity = findViewById(R.id.rvActivitysTest);
        recyclerActivity.setHasFixedSize(true);

        refreshLayout = findViewById(R.id.swipe_refresh_activitys);
        refreshLayout.setOnRefreshListener(this);

        if (savedInstanceState == null) {
            showActivitys();
        }

    }


    public void showActivitys() {
        if (models == null){
            models = GlobalClasses.getAllClasses();
        }
        AdapterListClasses adapter = new AdapterListClasses(models);
        recyclerActivity.setAdapter(adapter);
        recyclerActivity.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerActivity.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Configuration.CLASSES, models);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //noinspection unchecked
        models = (ArrayList<ClassModel>) savedInstanceState.getSerializable(Configuration.CLASSES);
        showActivitys();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }
}
