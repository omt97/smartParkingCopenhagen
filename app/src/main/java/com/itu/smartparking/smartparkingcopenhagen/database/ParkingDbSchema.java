package com.itu.smartparking.smartparkingcopenhagen.database;

public class ParkingDbSchema {

    public static final class UserTable {
        public static final String NAME = "users";
        public static final class Cols {
            public static final String USER = "user";
            public static final String PASSWORD = "password";
            public static final String ADMIN = "admin";
        }
    }

    public static final class ParkingTable {
        public static final String NAME = "parkings";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String ZONE = "zone";
            public static final String DISTANCE = "distance";
            public static final String PHOTO = "photo";
        }
    }
}
