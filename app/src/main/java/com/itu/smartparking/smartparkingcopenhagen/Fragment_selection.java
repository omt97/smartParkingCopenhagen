package com.itu.smartparking.smartparkingcopenhagen;

import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public class Fragment_selection extends Fragment {

    private ImageView photo;
    private TextView name;
    private TextView zone;
    private TextView distance;
    private Button book;

    private DataAccess da;

    private Parking mParking;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        da = new DataAccess(getContext());
        mParking = da.getParking(((Map) getActivity()).getId());

        photo = view.findViewById(R.id.imageView);
        name = view.findViewById(R.id.item_name);
        zone = view.findViewById(R.id.item_zone_i);
        distance = view.findViewById(R.id.item_distance_i);
        book = view.findViewById(R.id.item_book);

        if (mParking.getPhoto() != null) photo.setImageBitmap(BitmapFactory.decodeByteArray(mParking.getPhoto() , 0, mParking.getPhoto().length));
        System.out.println(mParking.getName());
        name.setText(mParking.getName());
        zone.setText(getAddress(mParking.getLongitude(), mParking.getLatitude()));
        Location loc1 = new Location("");
        loc1.setLatitude(mParking.getLatitude());
        loc1.setLongitude(mParking.getLongitude());

        Location loc2 = new Location("");
        loc2.setLatitude(((Map) getActivity()).getActLat());
        loc2.setLongitude(((Map) getActivity()).getActLong());

        double distanceInMeters = loc1.distanceTo(loc2);
      //  distance.setText(Double.toString(distanceInMeters));
        distance.setText(mParking.getAvailability());

        return view;
    }

    private String getAddress(double longitude, double latitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        StringBuilder stringBuilder = new StringBuilder();
        try {
            java.util.List<Address> addresses =
                    geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                stringBuilder.append(address.getAddressLine(0)).append("\n");
                stringBuilder.append(address.getLocality()).append("\n");
                stringBuilder.append(address.getPostalCode()).append("\n");
                stringBuilder.append(address.getCountryName());
            } else
                return "No address found";
        } catch (IOException ex) { }
        return stringBuilder.toString();
    }
}
