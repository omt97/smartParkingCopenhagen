package com.itu.smartparking.smartparkingcopenhagen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class CreateParking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        FragmentManager fmap = getSupportFragmentManager();
        Fragment fragment = fmap.findFragmentById(R.id.fragment_create_container);
        if (fragment == null) {
            fragment = new Fragment_create();
            fmap.beginTransaction()
                    .add(R.id.fragment_create_container, fragment)
                    .commit();
        }
    }

}
