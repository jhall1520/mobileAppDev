package com.example.group14_inclass11;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.group14_inclass11.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
Group14_InClass11
MapsActivity.java
Jimmy Kropp
Joel Hall
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    OkHttpClient client = new OkHttpClient();
    String title;
    ArrayList<LatLng> latLngs;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



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

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/map/route")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        latLngs = new ArrayList<>();

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        title = jsonObject.getString("title");
                        JSONArray path = jsonObject.getJSONArray("path");
                        for (int i = 0; i < path.length(); i++) {
                            JSONObject cords = (JSONObject) path.get(i);
                            latLngs.add(new LatLng(Double.parseDouble(cords.getString("latitude")), Double.parseDouble(cords.getString("longitude"))));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LatLng startingPoint = latLngs.get(0);
                            mMap.addMarker(new MarkerOptions().position(startingPoint).title("Start Location"));

                            LatLng endingPoint = latLngs.get(latLngs.size() - 1);
                            mMap.addMarker(new MarkerOptions().position(endingPoint).title("End Location"));


                            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                                    .clickable(true)
                                    .addAll(latLngs));
                            polyline1.setTag(title);

                            //add all the points ..
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            for (LatLng latLng : latLngs) {
                                builder.include(latLng);
                            }

                            LatLngBounds bounds = builder.build();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                        }
                    });
                }
            }
        });
    }
}