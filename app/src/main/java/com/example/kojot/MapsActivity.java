package com.example.kojot;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        drawCircle1(new LatLng(wyniki.lat, wyniki.lng));
        drawCircle2(new LatLng(wyniki.lat, wyniki.lng));
        drawCircle3(new LatLng(wyniki.lat, wyniki.lng));

        // Add a marker in Sydney and move the camera
        LatLng local = new LatLng(wyniki.lat, wyniki.lng);
        mMap.addMarker(new MarkerOptions().position(local).title("Accident localisation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11.0f));

    }

    private void drawCircle3(LatLng point) {

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(wyniki.xg3);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions);

    }

    private void drawCircle2(LatLng point) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(wyniki.xg2);
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.fillColor(0x0f00ffff);
        circleOptions.strokeWidth(2);
        mMap.addCircle(circleOptions);
    }

    private void drawCircle1(LatLng point) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(wyniki.xg1);
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.fillColor(0x0fffff00);
        circleOptions.strokeWidth(2);
        mMap.addCircle(circleOptions);
    }
}
