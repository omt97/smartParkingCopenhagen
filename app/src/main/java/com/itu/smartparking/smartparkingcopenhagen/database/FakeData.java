package com.itu.smartparking.smartparkingcopenhagen.database;

import android.content.Context;

import com.itu.smartparking.smartparkingcopenhagen.Parking;

import java.util.ArrayList;
import java.util.List;

public class FakeData {

    private List<Parking> mParkings;

    private static FakeData sFakeData;

    public static FakeData get() {
        if (sFakeData == null) {
            sFakeData = new FakeData();
        }
        return sFakeData;
    }
    private FakeData() {
        mParkings = new ArrayList<>();
    }

    public List<Parking> getParkings() {

        mParkings.add(new Parking("Parking",55.6759, 12.5655, 12, null));
        mParkings.add(new Parking("EuroPark P-plads",55.671694, 12.577941, 16, null));
        mParkings.add(new Parking("Q-Park Industriens Hus",55.67534, 12.568018, 56, null));
        mParkings.add(new Parking("EuroPark (P-hus)",55.6734474, 12.5615603, 12, null));
        mParkings.add(new Parking("Q-Park Nørreport",55.6759, 12.5655, 87, null));
        mParkings.add(new Parking("Q-Park Magasin du Nord",55.67943, 12.582419, 73, null));
        mParkings.add(new Parking("Q-Park Adelgade",55.682669, 12.582337, 32, null));
        mParkings.add(new Parking("Q-Park Illum",55.679762, 12.579563, 12, null));
        mParkings.add(new Parking("Q-Park Codanhus",55.6794, 12.5346, 34, null));
        mParkings.add(new Parking("Q-Park Vesterport",55.675963, 12.560254, 24, null));
        mParkings.add(new Parking("Q-Park Admiral Hotel",55.6826832, 12.5938453, 47, null));
        mParkings.add(new Parking("Det Grønne Parkeringshus",55.68432, 12.555857, 39, null));
        mParkings.add(new Parking("Saga Hus",55.672958, 12.559498, 11, null));
        mParkings.add(new Parking("Jeudan Parkering",55.680817, 12.594237, 35, null));
        mParkings.add(new Parking("parking",55.6653044, 12.5660818, 76, null));
        mParkings.add(new Parking("Jeudan Parkering",55.683375, 12.588442, 26, null));
        mParkings.add(new Parking("ONEPARK Landgreven P house",55.683238, 12.585147, 65, null));
        mParkings.add(new Parking("Onepark A/S",55.676134, 12.566479, 21, null));
        mParkings.add(new Parking("Parking",55.67568, 12.559374, 25, null));
        mParkings.add(new Parking("Jeudan Parkering",55.6819706, 12.5796943, 29, null));

        return mParkings;
    }



}
