package com.itu.smartparking.smartparkingcopenhagen;

import java.sql.Blob;
import java.util.UUID;

public class Parking {

    private UUID id;
    private String name;
    private double latitude;
    private double longitude;
    private int availability;
    private byte[] photo;

    public Parking(String name, double latitude, double longitude, int availability, byte[] photo) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availability = availability;
        this.photo = photo;
    }

    public Parking(UUID id, String name, double latitude, double longitude, int availability, byte[] photo) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availability = availability;
        this.photo = photo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
