package com.itu.smartparking.smartparkingcopenhagen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class List extends AppCompatActivity {

    private static final String EXTRA_LATITUDE =
            "com.itu.smartparking.smartparkingcopenhagen.latitude";
    private static final String EXTRA_LONGITUDE =
            "com.itu.smartparking.smartparkingcopenhagen.longitude";

    public static Intent newIntent(Context packageContext, double latitude, double longitude) {
        Intent intent = new Intent(packageContext, List.class);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager fmap = getSupportFragmentManager();
        Fragment fragment = fmap.findFragmentById(R.id.fragment_list_container);
        if (fragment == null) {
            fragment = new Fragment_list();
            fmap.beginTransaction()
                    .add(R.id.fragment_list_container, fragment)
                    .commit();
        }
    }
}
