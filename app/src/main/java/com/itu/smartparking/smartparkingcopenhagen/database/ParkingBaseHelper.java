package com.itu.smartparking.smartparkingcopenhagen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itu.smartparking.smartparkingcopenhagen.Parking;


public class ParkingBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "parkingBase4.db";
    public ParkingBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ParkingDbSchema.ParkingTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ParkingDbSchema.ParkingTable.Cols.UUID + ", " +
                ParkingDbSchema.ParkingTable.Cols.NAME + ", " +
                ParkingDbSchema.ParkingTable.Cols.LATITUDE + ", " +
                ParkingDbSchema.ParkingTable.Cols.LONGITUDE + ", " +
                ParkingDbSchema.ParkingTable.Cols.AVAILABILITY + ", " +
                ParkingDbSchema.ParkingTable.Cols.PHOTO +
                ")"
        );
        db.execSQL("create table " + ParkingDbSchema.UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ParkingDbSchema.UserTable.Cols.USER + ", " +
                ParkingDbSchema.UserTable.Cols.PASSWORD + ", " +
                ParkingDbSchema.UserTable.Cols.ADMIN +
                ")"
        );

        FakeData fd = FakeData.get();
        for (Parking parking:fd.getParkings()){
            ContentValues values = getContentParkingValues(parking);
            db.insert(ParkingDbSchema.ParkingTable.NAME, null, values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private static ContentValues getContentParkingValues(Parking parking) {
        ContentValues values = new ContentValues();
        values.put(ParkingDbSchema.ParkingTable.Cols.UUID, parking.getId().toString());
        values.put(ParkingDbSchema.ParkingTable.Cols.NAME, parking.getName());
        values.put(ParkingDbSchema.ParkingTable.Cols.LATITUDE, parking.getLatitude());
        values.put(ParkingDbSchema.ParkingTable.Cols.LONGITUDE, parking.getLongitude());
        values.put(ParkingDbSchema.ParkingTable.Cols.AVAILABILITY, parking.getAvailability());
        values.put(ParkingDbSchema.ParkingTable.Cols.PHOTO, parking.getPhoto());
        return values;
    }
}
