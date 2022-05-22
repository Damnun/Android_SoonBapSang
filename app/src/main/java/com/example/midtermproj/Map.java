package com.example.midtermproj;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울"); // 마커 제목
        markerOptions.snippet("한국의 수도"); // 마커 설명
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)); // 초기 위치
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15)); // 줌의 정도
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//        // add marker
//        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//            @Override
//            public void onMapLoaded() {
//                Log.d(TAG, "Load");
//                LatLng latLng = new LatLng(addMapPoint(context, "서울시청").getLatitude(),
//                         addMapPoint(context, "서울시청").getLongitude());
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
//            }
//        });
//
//        for (int i = 0; i < s.size(); i++) {
//            Log.d(TAG, "create" + String.valueOf(i));
//            Location location = addMapPoint(context, clinics.get(i).getAddress());
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            mMap.addMarker(markerOptions);
        }
    }

//    public static Location addMapPoint(Context context) {
//        Location location = new Location("");
//        Geocoder geocoder = new Geocoder(context);
//        List<Address> addresses = null;
//
//        try {
//            addresses = geocoder.getFromLocationName("서울", 3);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (addresses != null) {
//            for (int i = 0; i < addresses.size(); i++) {
//                Address lating = addresses.get(i);
//                location.setLatitude(lating.getLatitude());
//                location.setLongitude(lating.getLongitude());
//            }
//        }
//        return location;
//    }
