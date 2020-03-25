package com.android.jobber.Ui.fragments.PublishingFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.Message;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CustomDialogMap extends DialogFragment implements OnMapReadyCallback{




    Activity activity;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 111;
    Dialog dialog;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    private Button btnClose;
    private double lat=0.0,lang=0.0;
    FragmentManager dialogFragment;
    DialogFragment dialogFragmentt;
    GoogleMap googleMap;
    MapView mMapView;
    public CustomDialogMap(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }
    PresenterPublish presenter;
    public void showDialog(final Activity activity,PresenterPublish presenter){
        this.activity = activity;
        this.presenter = presenter;
         dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialog_map);
        dialog.show();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        btnClose = dialog.findViewById(R.id.btn_close);
        MapsInitializer.initialize(activity);
        mMapView = (MapView) dialog.findViewById(R.id.mapView);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();// needed to get the map to display immediately


            if (!checkPermissions()) {
                requestPermissions();
            } else {
                getLastLocation(presenter);
            }

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });



    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(ContentValues.TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted.
                    getLastLocation(presenter);
                }

                break;
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(activity,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(ContentValues.TAG, "Displaying permission rationale to provide additional context.");


        } else {
            Log.i(ContentValues.TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(final PresenterPublish presenter) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();


                            lat = mLastLocation.getLatitude();
                            lang = mLastLocation.getLongitude();
                            mMapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(final GoogleMap googleMap) {
                                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                                    googleMap.clear(); //clear old markers

                                    CameraPosition googlePlex = CameraPosition.builder()
                                            .target(new LatLng(lat,lang))
                                            .zoom(15)
                                            .bearing(0)
                                            .tilt(45)
                                            .build();

                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(37.4219999, -122.0862462))
//                        .title("Spider Man")
//                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.used_car)));

                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(lat,lang))
                                            .title("Iron Man")
                                            .snippet("His Talent : Plenty of money"));

                                    Log.e(ContentValues.TAG, "onMapReady: "+lat+","+lang );
//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(37.3092293,-122.1136845))
//                        .title("Captain America"));

                                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                        @Override
                                        public void onMapClick(LatLng latLng) {
                                            lat = latLng.latitude;
                                            lang = latLng.longitude;
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(lat,lang))
                                                    .title("Iron Man")
                                                    .snippet("His Talent : Plenty of money"));
                                            presenter.performGetLatLang(lat,lang);
                                            //Message.message(activity,"The Location is Selected");
                                        }
                                    });
                                }
                            });

                        } else {
                            Log.w(ContentValues.TAG, "getLastLocation:exception", task.getException());

                        }
                    }
                });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.clear(); //clear old markers

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(lat,lang))
                .zoom(15)
                .bearing(0)
                .tilt(45)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(37.4219999, -122.0862462))
//                        .title("Spider Man")
//                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.used_car)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lang))
                .title("Iron Man")
                .snippet("His Talent : Plenty of money"));

        Log.e(ContentValues.TAG, "onMapReady: "+lat+","+lang );
//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(37.3092293,-122.1136845))
//                        .title("Captain America"));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                lat = latLng.latitude;
                lang = latLng.longitude;

                Message.message(activity,"The Location is Selected");
            }
        });
    }
}

