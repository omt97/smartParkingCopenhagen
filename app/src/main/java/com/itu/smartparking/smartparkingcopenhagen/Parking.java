package com.itu.smartparking.smartparkingcopenhagen;

import java.sql.Blob;
import java.util.UUID;

public class Parking {

    private UUID id;
    private String zone;
    private int distance;
    private byte[] photo;

    public Parking(String zone, int distance, byte[] photo) {
        this.id = UUID.randomUUID();
        this.zone = zone;
        this.distance = distance;
        this.photo = photo;
    }

    public Parking(UUID id, String zone, int distance, byte[] photo) {
        this.id = id;
        this.zone = zone;
        this.distance = distance;
        this.photo = photo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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
