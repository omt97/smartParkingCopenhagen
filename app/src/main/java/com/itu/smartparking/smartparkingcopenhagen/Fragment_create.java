package com.itu.smartparking.smartparkingcopenhagen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Blob;

import static android.app.Activity.RESULT_OK;

public class Fragment_create extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView parking_image;
    private EditText name;
    private EditText latitude;
    private EditText longitude;
    private EditText availablity;
    private Button create;

    private File photoFile;

    private DataAccess da;

    private byte[] image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        da = new DataAccess(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create, container, false);

        parking_image = v.findViewById(R.id.imageView2);
        name = v.findViewById(R.id.name_create);
        latitude = v.findViewById(R.id.lat_create);
        longitude = v.findViewById(R.id.long_create);
        availablity = v.findViewById(R.id.ava_create);
        create = v.findViewById(R.id.create_button);

        parking_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((latitude.getText().length() > 0) && (latitude.getText().length() > 0) && (availablity.getText().length() > 0) && (name.getText().length() > 0)){
                    Parking parking = new Parking(name.getText().toString(), Double.parseDouble(latitude.getText().toString()), Double.parseDouble(longitude.getText().toString()), Integer.parseInt(availablity.getText().toString()), image);
                    da.addParking(parking);
                    getActivity().finish();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            parking_image.setImageBitmap(imageBitmap);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            image = bos.toByteArray();
        }
    }
}
