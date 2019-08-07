package com.itu.smartparking.smartparkingcopenhagen;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.itu.smartparking.smartparkingcopenhagen.database.ParkingDbSchema;

import java.util.UUID;

public class ParkingCursorWrapper extends CursorWrapper {

    public ParkingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Parking getParking() {
        UUID id = UUID.fromString(getString(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.UUID)));
        String name = getString(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.NAME));
        double latitude = getDouble(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.LATITUDE));
        double longitude = getDouble(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.LONGITUDE));
        int availability = getInt(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.AVAILABILITY));
        byte[] photo = getBlob(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.PHOTO));
        return new Parking(id, name, latitude, longitude, availability, photo);
    }

}
