package com.itu.smartparking.smartparkingcopenhagen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itu.smartparking.smartparkingcopenhagen.database.ParkingBaseHelper;
import com.itu.smartparking.smartparkingcopenhagen.database.ParkingDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataAccess {

    private SQLiteDatabase mDatabase;
    private Context mContext;


    public DataAccess(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ParkingBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addParking(Parking c) {
        ContentValues values = getContentParkingValues(c);
        mDatabase.insert(ParkingDbSchema.ParkingTable.NAME, null, values);
    }

    public void addUser(User u) {
        ContentValues values = getContentUserValues(u);
        mDatabase.insert(ParkingDbSchema.UserTable.NAME, null, values);
    }

    public void updateParking(Parking p) {
        String uuidString = p.getId().toString();
        ContentValues values = getContentParkingValues(p);
        mDatabase.update(ParkingDbSchema.ParkingTable.NAME, values,
                ParkingDbSchema.ParkingTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void updateUser(User u) {
        String user = u.getUser();
        ContentValues values = getContentUserValues(u);
        mDatabase.update(ParkingDbSchema.UserTable.NAME, values,
                ParkingDbSchema.UserTable.Cols.USER + " = ?",
                new String[] { user });
    }

    public User getUser(String username){
        User user = null;
        UserCursorWrapper cursor = queryUser(ParkingDbSchema.UserTable.Cols.USER + " = ?",
                new String[] { username }
        );
        try {
            if (cursor.getCount() == 0) return null;
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }


    public List<Parking> getParkings(){
        List<Parking> parkings = new ArrayList<>();
        ParkingCursorWrapper cursor = queryParking(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                parkings.add(cursor.getParking());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return parkings;
    }

    private UserCursorWrapper queryUser(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ParkingDbSchema.UserTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new UserCursorWrapper(cursor);
    }

    private ParkingCursorWrapper queryParking(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ParkingDbSchema.ParkingTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ParkingCursorWrapper(cursor);
    }

    public File getPhotoFile(Parking parking) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, parking.getPhotoFilename());
    }

    private static ContentValues getContentParkingValues(Parking parking) {
        ContentValues values = new ContentValues();
        values.put(ParkingDbSchema.ParkingTable.Cols.UUID, parking.getId().toString());
        values.put(ParkingDbSchema.ParkingTable.Cols.ZONE, parking.getZone());
        values.put(ParkingDbSchema.ParkingTable.Cols.DISTANCE, parking.getDistance());
        values.put(ParkingDbSchema.ParkingTable.Cols.PHOTO, parking.getPhoto());
        return values;
    }

    private static ContentValues getContentUserValues(User user) {
        ContentValues values = new ContentValues();
        values.put(ParkingDbSchema.UserTable.Cols.USER, user.getUser());
        values.put(ParkingDbSchema.UserTable.Cols.PASSWORD, user.getPassword());
        values.put(ParkingDbSchema.UserTable.Cols.ADMIN, user.getAdmin());
        return values;
    }

}
