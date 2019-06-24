package com.ferbajoo.testthings.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;

/**
 * Created by
 *          amplemind on 6/27/18.
 */
@Foo(name = "TinderActivity", value = "Calificar cartas desplazandolas",drawable = R.drawable.tinder_image)
public class TinderActivity extends AppCompatActivity implements SensorEventListener{

    private CardStackView mCardStack;
    private CardAdapterImages mAdapter;

    /**
     * Constants for sensors
     */
    private static final float SHAKE_THRESHOLD = 2.1f;
    private static final int SHAKE_WAIT_TIME_MS = 500;

    /**
     * Sensors
     */
    private SensorManager mSensorManager;
    private Sensor mSensorAcc;
    private long mShakeTime = 0;

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder);

        initSensor();


        mCardStack = findViewById(R.id.cardStackView);
        mCardStack.setCardEventListener(mCardEvent);

        loadCards();

    }

    private void initSensor() {
        // Get the sensors to use
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null){
            mSensorAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    private void loadCards() {
        mAdapter = new CardAdapterImages(this, R.layout.item_card_tinder);
        mAdapter.setData(loadData());
        mCardStack.setAdapter(mAdapter);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                Log.e("UnRELIABLE","No se generan datos de accelerometro");
            }
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectShake(event);
        }

    }

    /**
     * Detect a shake based on the ACCELEROMETER sensor
     *
     * @param event
     */
    private void detectShake(SensorEvent event) {
        long now = System.currentTimeMillis();

        if ((now - mShakeTime) > SHAKE_WAIT_TIME_MS) {
            mShakeTime = now;

            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            // Change background color if gForce exceeds threshold;
            // otherwise, reset the color
            if (gForce > SHAKE_THRESHOLD) {
                mCardStack.reverse();
                Log.e("Se agito el cel","Se da reversa");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class CardAdapterImages extends ArrayAdapter<SportList>{

        private ArrayList<SportList> sportLists = new ArrayList<>();

        CardAdapterImages(@NonNull Context context, int resource) {
            super(context, resource);
        }

        public void setData(ArrayList<SportList> sportLists){
            this.sportLists = sportLists;
        }

        @SuppressLint("ViewHolder")
        @NonNull
        @Override
        public View getView(int position, @Nullable View contentView, @NonNull ViewGroup parent) {
            ViewHolder holder;

            if (contentView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                contentView = inflater.inflate(R.layout.item_card_tinder, parent, false);
                holder = new ViewHolder(contentView);
                contentView.setTag(holder);
            } else {
                holder = (ViewHolder) contentView.getTag();
            }

            SportList item = getItem(position);

            if (item != null) {
                holder.name.setText(item.name);
                holder.country.setText(item.country);
                Glide.with(getContext()).load(item.url).into(holder.image);
            }

            return contentView;
        }

        private class ViewHolder {
            TextView name;
            TextView country;
            ImageView image;

             ViewHolder(View view) {
                this.name = view.findViewById(R.id.item_name);
                this.country = view.findViewById(R.id.item_description);
                this.image = view.findViewById(R.id.item_card);
            }
        }

        @Nullable
        @Override
        public SportList getItem(int position) {
            return sportLists.get(position);
        }

        @Override
        public int getCount() {
            return sportLists.size();
        }
    }

    public ArrayList<SportList> loadData() {
        ArrayList<SportList> spots = new ArrayList<>();
        spots.add(new SportList("Barcelona", "España", "https://www.blaugranas.com/media/galeria/25/2/2/0/9/n_f_c_barcelona_escudo-4089022.jpg"));
        spots.add(new SportList("Real madrid", "España", "http://1000logos.net/wp-content/uploads/2017/06/Color-Real-Madrid-Logo.jpg"));
        spots.add(new SportList("Liverpool", "Inglaterra", "http://www.tibetanreview.net/wp-content/uploads/2017/10/Liverpool-FC.jpg"));
        spots.add(new SportList("Atletico", "España", "http://e00-marca.uecdn.es/p/110/sp/11000/thumbnail/entry_id/0_ccukm9d0/version/100001/height/402"));
        spots.add(new SportList("Manchester", "Inglaterra", "https://www.101greatgoals.com/wp-content/uploads/2017/06/manchester-united-logo.jpg"));
        spots.add(new SportList("Juventus", "Italia", "http://www.brandemia.org/sites/default_image/files/logo_juventus_brandemia.jpg"));
        spots.add(new SportList("Chelsea", "Inglaterra", "https://www.101greatgoals.com/wp-content/uploads/2017/05/3286f3efad79e01f9f1469c0c7998984_chelsea-logo-chelsea-fc-logo-clipart_1600-1200.jpg"));
        spots.add(new SportList("Arsenal", "España", "https://wallpapercave.com/wp/wc1776982.jpg"));
        spots.add(new SportList("Paris Saint-Germain", "Francia", "http://www.brandemia.org/wp-content/uploads/2013/02/PSG_marca_escudo.jpg"));
        spots.add(new SportList("Inter de Milan", "Italia", "https://cdn-images-1.medium.com/max/2000/1*-1bWRL6cKaDB-FNR753lMg.png"));
        return spots;
    }


    private CardStackView.CardEventListener mCardEvent = new CardStackView.CardEventListener() {
        @Override
        public void onCardDragging(float percentX, float percentY) {
            Log.e("onCardDragging","Action");
        }

        @Override
        public void onCardSwiped(SwipeDirection direction) {
            Log.e("onCardSwiped","Action");
            Log.d("CardStackView", "onCardSwiped: " + direction.toString());
            Toast.makeText(TinderActivity.this, direction.toString(), Toast.LENGTH_SHORT).show();
            if (mCardStack.getTopIndex() == mAdapter.getCount()){
                Toast.makeText(TinderActivity.this, "Se terminaron las cartas", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCardReversed() {
            Log.e("onCardReversed","Action");
        }

        @Override
        public void onCardMovedToOrigin() {
            Log.e("onCardMovedToOrigin","Action");
        }

        @Override
        public void onCardClicked(int index) {
            mCardStack.reverse();
            Log.e("onCardClicked","Action");
        }
    };

    public class SportList {
        private String name;
        private String country;
        private String url;

        SportList(String name, String country, String url) {
            this.name = name;
            this.country = country;
            this.url = url;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}
