package com.itu.smartparking.smartparkingcopenhagen;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.itu.smartparking.smartparkingcopenhagen.database.ParkingDbSchema;

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String user = getString(getColumnIndex(ParkingDbSchema.UserTable.Cols.USER));
        String password = getString(getColumnIndex(ParkingDbSchema.UserTable.Cols.PASSWORD));
        int admin = getInt(getColumnIndex(ParkingDbSchema.UserTable.Cols.ADMIN));
        return new User(user, password, admin);
    }
}
