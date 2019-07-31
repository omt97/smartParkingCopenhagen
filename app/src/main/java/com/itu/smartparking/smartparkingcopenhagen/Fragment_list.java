package com.itu.smartparking.smartparkingcopenhagen;

import android.graphics.BitmapFactory;
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

import java.util.List;

public class Fragment_list extends Fragment {

    private RecyclerView mListRecyclerView;

    private ListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListRecyclerView = (RecyclerView) view
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

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
        private TextView zone;
        private TextView distance;
        private Button book;

        private Parking mParking;

        public ListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_selection, parent, false));

            photo = itemView.findViewById(R.id.imageView);
            zone = itemView.findViewById(R.id.item_zone);
            distance = itemView.findViewById(R.id.item_distance);
            book = itemView.findViewById(R.id.item_book);
        }

        public void bind(Parking parking) {
            mParking = parking;

            photo.setImageBitmap(BitmapFactory.decodeByteArray(mParking.getPhoto() , 0, mParking.getPhoto().length));
            zone.setText(mParking.getZone());
            distance.setText(Integer.toString(mParking.getDistance()));
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
