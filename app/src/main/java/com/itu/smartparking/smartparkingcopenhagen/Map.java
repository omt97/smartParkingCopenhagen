package com.itu.smartparking.smartparkingcopenhagen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class Map extends AppCompatActivity {

    public static final String EXTRA_USER = "com.itu.smartparking.smartparkingcopenhagen.user";
    private UUID id;
    private double actLat;
    private double actLong;

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

    public void showSelection(UUID id, double actLat, double actLong){
        this.id = id;
        this.actLat = actLat;
        this.actLong = actLong;
        FragmentManager fsel = getSupportFragmentManager();
        Fragment fragment = fsel.findFragmentById(R.id.fragment_book_container);
        if (fragment != null) {
            fsel.beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_book_container)).commit();

            fragment = new Fragment_selection();
            fsel.beginTransaction()
                    .add(R.id.fragment_book_container, fragment)
                    .commit();
        }
        else{
            fragment = new Fragment_selection();
            fsel.beginTransaction()
                    .add(R.id.fragment_book_container, fragment)
                    .commit();
        }
    }

    public UUID getId(){
        return id;
    }

    public double getActLat() {
        return actLat;
    }

    public double getActLong() {
        return actLong;
    }
}
