package com.itu.smartparking.smartparkingcopenhagen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Fragment_map extends Fragment {

    private static final String EXTRA_USER = "com.itu.smartparking.smartparkingcopenhagen.user";
    private static final String DIALOG_PARK = "DialogPark";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_LATLNG = 1;

    private ArrayList<String> mPermissions = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;

    private User user;
    private DataAccess da;

    private GoogleMap map;

    private TextView usernameP;
    private Button list;

    private String mLong;
    private String mLat;
    private String mAdress;

    private double longitude;
    private double latitude;
    private double actLong;
    private double actLat;

    public PolylineOptions lineOptions = null;

    private java.util.List<Parking> parkings;

    private Polyline polyline = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(),
                    PackageManager.GET_SIGNING_CERTIFICATES);
            for (Signature signature : info.signingInfo.getSigningCertificateHistory().getApkContentsSigners()) {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }*/

        da = new DataAccess(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        String username = (String) getActivity().getIntent().getSerializableExtra(Map.EXTRA_USER);
        //user = da.getUser(username);


        /*usernameP = (TextView) v.findViewById(R.id.abc);

        mLong = (TextView) v.findViewById(R.id.longitude);
        mLat = (TextView) v.findViewById(R.id.latitude);
        mAdress = (TextView) v.findViewById(R.id.adress);*/

        list = (Button) v.findViewById(R.id.list_go);
        mPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        mPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        mPermissions.add(Manifest.permission.READ_CONTACTS);

        parkings = da.getParkings();

        ArrayList<String> mPermissionsToRequest = permissionsToRequest(mPermissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.
                M) {
            if
                    (mPermissionsToRequest.size() >
                    0) {
                requestPermissions(mPermissionsToRequest.toArray(
                        new String[mPermissionsToRequest.size()]),
                        ALL_PERMISSIONS_RESULT);
            }
        }


        /**/

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        checkPermission();
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            if (longitude != location.getLongitude() || latitude != location.getLatitude()) {
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                                showMap();
                            }
                        }
                    }
                });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = List.newIntent(getActivity(), latitude, longitude);
                startActivityForResult(intent, REQUEST_LATLNG);
            }
        });

//        usernameP.setText(username);

/*

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateParking.class);
                startActivity(intent);
            }
        });*/
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    /*if (longitude != location.getLongitude() || latitude != location.getLatitude()) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }*/
                }
            }
        };
        return v;
    }


    private void showMap() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                map = googleMap;
                LatLng latLng = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
                for (Parking parking:parkings){
                    latLng = new LatLng(parking.getLatitude(), parking.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(parking.getName()).snippet(parking.getId().toString()));
                }
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        actLat = marker.getPosition().latitude;
                        actLong = marker.getPosition().longitude;
                        FragmentManager manager = getFragmentManager();
                        ParkPickerFragment dialog = ParkPickerFragment.newInstance(UUID.fromString(marker.getSnippet()));
                        dialog.show(manager, DIALOG_PARK);
                        dialog.setTargetFragment(Fragment_map.this, REQUEST_DATE);
                        /*((Map) getActivity()).showSelection(UUID.fromString(marker.getSnippet()), latitude, longitude);

                        */

                        return false;
                    }
                });
            }
        });

        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
    }




    private ArrayList<String> permissionsToRequest(ArrayList<String> permissions) {
        ArrayList<String> result = new ArrayList<>();
        for (String permission : permissions)
            if (!hasPermission(permission))
                result.add(permission);
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return Objects.requireNonNull(getActivity())
                    .checkSelfPermission(permission) ==
                    PackageManager.PERMISSION_GRANTED;
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private boolean checkPermission() {
        return (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
 /*if (checkPermission())
 return;*/
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
    }

    private void stopLocationUpdates() {
        mFusedLocationProviderClient
                .removeLocationUpdates(mLocationCallback);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            boolean show = (boolean) data
                    .getSerializableExtra(ParkPickerFragment.EXTRA_PARK);
            if (show) {

                if (polyline != null) polyline.remove();

                String url = getDirectionsUrl(new LatLng(latitude, longitude), new LatLng(actLat, actLong));

                DownloadTask downloadTask = new DownloadTask();
               // downloadTask.execute(url);
                try {
                    Object str_result= downloadTask.execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
               // map.addPolyline(lineOptions);
            }
        }
        if (requestCode == REQUEST_LATLNG){
            double lat = (Double) data
                    .getSerializableExtra(Fragment_list.EXTRA_LAT);

            double lng = (Double) data
                    .getSerializableExtra(Fragment_list.EXTRA_LNG);

                if (polyline != null) polyline.remove();

                String url = getDirectionsUrl(new LatLng(latitude, longitude), new LatLng(lat, lng));

                DownloadTask downloadTask = new DownloadTask();
                // downloadTask.execute(url);
                try {
                    Object str_result= downloadTask.execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                // map.addPolyline(lineOptions);
        }
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        ApplicationInfo ai = null;
        try {
            ai = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Object value = (Object)ai.metaData.get("com.google.android.geo.API_KEY");

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + value.toString();

        System.out.println(url);

        return url;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            System.out.println(1);
            try {
                System.out.println(2);
                data = downloadUrl(url[0]);
                System.out.println(3);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            System.out.println(4);
            return data;
        }
    }

        private class ParserTask extends AsyncTask<String, Integer, java.util.List<java.util.List<HashMap<String, String>>>> {

            // Parsing the data in non-ui thread
            @Override
            protected java.util.List<java.util.List<HashMap<String, String>>> doInBackground(String... jsonData) {
                System.out.println(7);
                JSONObject jObject;
                java.util.List<java.util.List<HashMap<String, String>>> routes = null;
                java.util.List<java.util.List<HashMap>> rut = null;
                System.out.println(8);
                try {
                    System.out.println(9);
                    jObject = new JSONObject(jsonData[0]);
                    boolean found = false;
                    System.out.println(10);
                    DirectionsJSONParser parser = new DirectionsJSONParser();
                    System.out.println(11);
                    routes = parser.parse(jObject);
                    System.out.println(routes.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(12);
                System.out.println(13);
                return routes;
            }

            @Override
            protected void onPostExecute(java.util.List<java.util.List<HashMap<String, String>>> result) {
                System.out.println(14);
                ArrayList points = null;
                PolylineOptions lineOptions = null;
                MarkerOptions markerOptions = new MarkerOptions();
                System.out.println(result.size());
                System.out.println(15);
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList();
                    lineOptions = new PolylineOptions();
                    System.out.println(16);

                    java.util.List<HashMap<String, String>> path = result.get(i);

                    for (int j = 0; j < path.size(); j++) {
                        HashMap point = path.get(j);
                        System.out.println(17);
                        double lat = Double.parseDouble((String) point.get("lat"));
                        double lng = Double.parseDouble((String) point.get("lng"));
                        System.out.println(lat + " + " + lng);
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }
                    System.out.println(18);
                    lineOptions.addAll(points);
                    lineOptions.width(12);
                    lineOptions.color(Color.RED);
                    lineOptions.geodesic(true);

                }
                polyline = map.addPolyline(lineOptions);
                //addPoly();
// Drawing polyline in the Google Map for the i-th route
                /*System.out.println(map.toString());
                map.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(-35.016, 143.321),
                                new LatLng(-34.747, 145.592),
                                new LatLng(-34.364, 147.891),
                                new LatLng(-33.501, 150.217),
                                new LatLng(-32.306, 149.248),
                                new LatLng(-32.491, 147.309)));*/
            }
        }


        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();

                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }

}
