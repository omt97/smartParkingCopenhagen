package com.itu.smartparking.smartparkingcopenhagen;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Fragment_list extends Fragment {

    private static final String EXTRA_LATITUDE =
            "com.itu.smartparking.smartparkingcopenhagen.latitude";
    private static final String EXTRA_LONGITUDE =
            "com.itu.smartparking.smartparkingcopenhagen.longitude";

    private RecyclerView mListRecyclerView;
    private Button map;
    private Button create;

    private ListAdapter mAdapter;

    private double actLatitude;
    private double actLongitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actLatitude = getActivity().getIntent().getDoubleExtra(EXTRA_LATITUDE, 0);
        actLongitude = getActivity().getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListRecyclerView = (RecyclerView) view
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        map = (Button) view.findViewById(R.id.map_return);
        create = (Button) view.findViewById(R.id.create_button);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateParking.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateUI() {
        DataAccess dataAccess = new DataAccess(getContext());
        List<Parking> crimes = dataAccess.getParkings();
        mAdapter = new ListAdapter(crimes);
        mListRecyclerView.setAdapter(mAdapter);
    }

    private class ListHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView name;
        private TextView zone;
        private TextView distance;
        private Button book;

        private Parking mParking;

        public ListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_selection, parent, false));

            photo = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.item_name);
            zone = itemView.findViewById(R.id.item_zone);
            distance = itemView.findViewById(R.id.item_distance);
            book = itemView.findViewById(R.id.item_book);
        }

        public void bind(Parking parking) {
            mParking = parking;

            if (mParking.getPhoto() != null) photo.setImageBitmap(BitmapFactory.decodeByteArray(mParking.getPhoto() , 0, mParking.getPhoto().length));
            name.setText(mParking.getName());
            zone.setText(getAddress(mParking.getLongitude(), mParking.getLatitude()));
            Location loc1 = new Location("");
            loc1.setLatitude(mParking.getLatitude());
            loc1.setLongitude(mParking.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(actLatitude);
            loc2.setLongitude(actLongitude);

            double distanceInMeters = loc1.distanceTo(loc2);
            distance.setText(Double.toString(distanceInMeters));
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



    private class ListAdapter extends RecyclerView.Adapter<ListHolder> {
        private java.util.List<Parking> mParkings;
        public ListAdapter(List<Parking> parkings) {
            mParkings = parkings;
        }

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ListHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            Parking p = mParkings.get(position);
            holder.bind(p);
        }
        @Override
        public int getItemCount() {
            return mParkings.size();
        }
    }
}
