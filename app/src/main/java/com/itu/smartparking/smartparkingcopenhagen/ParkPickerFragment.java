package com.itu.smartparking.smartparkingcopenhagen;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public class ParkPickerFragment extends DialogFragment {

    private ImageView photo;
    private TextView name;
    private TextView zone;
    private TextView distance;
    private Button book;

    private DataAccess da;

    private Parking mParking;


    private static final String ARG_ID = "id";
    private DatePicker mDatePicker;

    public static final String EXTRA_PARK =
            "com.itu.smartparking.smartparkingcopenhage.park";

    public static ParkPickerFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID, id);
        ParkPickerFragment fragment = new ParkPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_item, null);

        UUID id = (UUID) getArguments().getSerializable(ARG_ID);

        da = new DataAccess(getContext());
        mParking = da.getParking(id);

        photo = view.findViewById(R.id.imageView_i);
        zone = view.findViewById(R.id.item_zone_i);
        distance = view.findViewById(R.id.item_distance_i);

        if (mParking.getPhoto() != null) photo.setImageBitmap(BitmapFactory.decodeByteArray(mParking.getPhoto() , 0, mParking.getPhoto().length));
        zone.setText(getAddress(mParking.getLongitude(), mParking.getLatitude()));
        Location loc1 = new Location("");
        loc1.setLatitude(mParking.getLatitude());
        loc1.setLongitude(mParking.getLongitude());

        Location loc2 = new Location("");
        loc2.setLatitude(((Map) getActivity()).getActLat());
        loc2.setLongitude(((Map) getActivity()).getActLong());

        double distanceInMeters = loc1.distanceTo(loc2);
        distance.setText(Double.toString(distanceInMeters));

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(mParking.getName())
                .setPositiveButton(R.string.booking_parking, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, true);
                    }
                })
                .create();


    }

    private void sendResult(int resultCode, boolean show) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PARK, show);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
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
