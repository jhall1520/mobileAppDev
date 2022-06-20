package com.example.group14_hw08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.group14_hw08.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Group14_HW08
 * MapsActivity.java
 * Joel Hall
 * Jimmy Kropp
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Place place1;
    Place place2;
    ArrayList<LatLng> latLngs;
    EditText places;
    LatLngBounds bounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        places = binding.editTextPlaces;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        binding.buttonSearchPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (places.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Type in a place to search", Toast.LENGTH_SHORT).show();
                    return;
                }
                LatLng latLng = latLngs.get(latLngs.size()/2);
                float[] results = new float[1];
                Location.distanceBetween(latLng.latitude, latLng.longitude, place1.getLatLng().latitude, place1.getLatLng().longitude, results);
                double location1Distance = results[0];
                Location.distanceBetween(latLng.latitude, latLng.longitude, place2.getLatLng().latitude, place2.getLatLng().longitude, results);
                double location2Distance = results[0];

                double radius = location1Distance <= location2Distance ? location2Distance : location1Distance;
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                                + latLng.latitude + "%2C" + latLng.longitude + "&radius=" + radius + "&keyword=" + places.getText().toString() + "&key=AIzaSyCWgx5hYKbV9gh9sw3ZVltuRyAUtnIozh0")
                        .method("GET", null)
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
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                //JSONObject results = jsonObject.getJSONObject("results");
                                JSONArray results = jsonObject.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject place = results.getJSONObject(i);
                                    String name = place.getString("name");
                                    JSONObject geometry = place.getJSONObject("geometry");
                                    JSONObject location = geometry.getJSONObject("location");
                                    LatLng latLng1 = new LatLng(Double.parseDouble(location.getString("lat")),
                                            Double.parseDouble(location.getString("lng")));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mMap.addMarker(new MarkerOptions().position(latLng1).title(name));
                                        }
                                    });
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
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

        Intent intent = getIntent();
        place1 = intent.getParcelableExtra("place1");
        place2 = intent.getParcelableExtra("place2");


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/directions/json?origin=place_id:" + place1.getId() + "&destination=place_id:" + place2.getId() + "&key=AIzaSyCWgx5hYKbV9gh9sw3ZVltuRyAUtnIozh0")
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        latLngs = new ArrayList<>();
                        latLngs.add(place1.getLatLng());

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray routes = jsonObject.getJSONArray("routes");
                        JSONObject routesStuff = (JSONObject) routes.get(0);
                        JSONArray legs = routesStuff.getJSONArray("legs");
                        JSONObject legsStuff = (JSONObject) legs.get(0);
                        JSONArray steps = (JSONArray) legsStuff.getJSONArray("steps");
                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject coords = (JSONObject) steps.get(i);
                            JSONObject end = (JSONObject) coords.getJSONObject("end_location");
                            latLngs.add(new LatLng(Double.parseDouble(end.getString("lat")), Double.parseDouble(end.getString("lng"))));
                        }

                        latLngs.add(place2.getLatLng());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LatLng startingPoint = place1.getLatLng();
                                mMap.addMarker(new MarkerOptions().position(startingPoint).title(place1.getName()));

                                LatLng endingPoint = place2.getLatLng();
                                mMap.addMarker(new MarkerOptions().position(endingPoint).title(place2.getName()));


                                Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                                        .clickable(true)
                                        .addAll(latLngs));
                                String title = place1.getName() + " --> " + place2.getName();
                                polyline1.setTag(title);

                                //add all the points ..
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latLng : latLngs) {
                                    builder.include(latLng);
                                }

                                bounds = builder.build();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }
}