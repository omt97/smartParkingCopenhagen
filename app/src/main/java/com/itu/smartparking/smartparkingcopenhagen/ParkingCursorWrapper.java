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
        String zone = getString(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.ZONE));
        int distance = getInt(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.DISTANCE));
        byte[] photo = getBlob(getColumnIndex(ParkingDbSchema.ParkingTable.Cols.PHOTO));
        return new Parking(id, zone, distance, photo);
    }

}
