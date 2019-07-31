package com.itu.smartparking.smartparkingcopenhagen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class Map extends AppCompatActivity {

    public static final String EXTRA_USER = "com.itu.smartparking.smartparkingcopenhagen.user";

    public static Intent newIntent(Context packageContext, String user) {
        Intent intent = new Intent(packageContext, Map.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        FragmentManager fmap = getSupportFragmentManager();
        Fragment fragment = fmap.findFragmentById(R.id.fragment_map_container);
        if (fragment == null) {
            fragment = new Fragment_map();
            fmap.beginTransaction()
                    .add(R.id.fragment_map_container, fragment)
                    .commit();
        }
    }

    public void showSelection(){
        FragmentManager fsel = getSupportFragmentManager();
        Fragment fragment = fsel.findFragmentById(R.id.fragment_book_container);
        if (fragment == null) {
            fragment = new Fragment_selection();
            fsel.beginTransaction()
                    .add(R.id.fragment_book_container, fragment)
                    .commit();
        }
    }
}
