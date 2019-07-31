package com.itu.smartparking.smartparkingcopenhagen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class List extends AppCompatActivity {

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
