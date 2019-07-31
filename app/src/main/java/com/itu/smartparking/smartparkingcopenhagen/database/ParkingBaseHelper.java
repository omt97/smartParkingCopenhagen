package com.itu.smartparking.smartparkingcopenhagen.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ParkingBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "parkingBase2.db";
    public ParkingBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ParkingDbSchema.ParkingTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ParkingDbSchema.ParkingTable.Cols.UUID + ", " +
                ParkingDbSchema.ParkingTable.Cols.ZONE + ", " +
                ParkingDbSchema.ParkingTable.Cols.DISTANCE + ", " +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
