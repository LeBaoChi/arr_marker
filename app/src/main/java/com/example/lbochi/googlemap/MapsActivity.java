package com.example.lbochi.googlemap;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback,DirectionFinderListener,GoogleMap.OnMapClickListener,GoogleMap.OnMarkerClickListener {
    private MapFragment mMapFragment;

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    int PLACE_PICKER_REQUEST = 1;

    private EditText marker_title;
    private Button submit_title;
    public String marker_1="Title";

    private static final LatLng[] LatLng_arr = new LatLng[]{
            new LatLng(21.007050, 105.842613), new LatLng(21.007061, 105.842838),
            new LatLng(21.007066, 105.843106), new LatLng(-23.202307, 135.395508),
            new LatLng(-19.705347, 129.550781)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        marker_title = (EditText) findViewById(R.id.marker_title);
        marker_title.setVisibility(LinearLayout.GONE);
        submit_title= (Button) findViewById(R.id.submit_title);
        submit_title.setVisibility(LinearLayout.GONE);
        submit_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTitle();
            }
        });
    }
    private void submitTitle(){
        marker_1 = marker_title.getText().toString();
        marker_title.setVisibility(LinearLayout.GONE);
        etOrigin.setVisibility(LinearLayout.VISIBLE);
        etDestination.setVisibility(LinearLayout.VISIBLE);
        btnFindPath.setVisibility(LinearLayout.VISIBLE);
        submit_title.setVisibility(LinearLayout.GONE);
        return;
    }
    private void sendRequest() {
        String origin = etOrigin.getText().toString();


        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bk = new LatLng(21.007072, 105.842939);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bk, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Đại học Bách Khoa hà Nội")
                .position(bk)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        addMarkersToMap();
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);


    }
    private void addMarkersToMap() {

        int numMarkersInRainbow =5 ;
        for (int i = 0; i < LatLng_arr.length; i++) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(LatLng_arr[i])
                    .title("Marker " + i));
//                    .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkersInRainbow))
//                    .flat(flat)
//                    .rotation(rotation));
//            mMarkerRainbow.add(marker);
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void onMapClick( LatLng point) {
        mMapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));

        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("TouchPoint1"));
    }

    public boolean onMarkerClick (Marker marker){
        // ẩn và hiện các layout
        marker_title.setVisibility(LinearLayout.VISIBLE);
        etOrigin.setVisibility(LinearLayout.GONE);
        etDestination.setVisibility(LinearLayout.GONE);
        btnFindPath.setVisibility(LinearLayout.GONE);
        submit_title.setVisibility(LinearLayout.VISIBLE);
//        marker_title = (EditText) findViewById(R.id.marker_title);
//        destination = etDestination.getText().toString();
        marker.setTitle(marker_1);
        return  false;
    }


}
