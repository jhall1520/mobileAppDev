package com.example.group14_hw08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Group14_HW08
 * MainActivity.java
 * Joel Hall
 * Jimmy Kropp
 */
public class MainActivity extends AppCompatActivity {

    private static int AUTOCOMPLETE_REQUEST_CODE1 = 1;
    private static int AUTOCOMPLETE_REQUEST_CODE2 = 2;
    Place place1;
    Place place2;
    TextView address1;
    TextView address2;

    String TAG = "stuff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        address1 = findViewById(R.id.textViewAddress1);
        address2 = findViewById(R.id.textViewAddress2);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.MAPS_API_KEY), Locale.US);
        }

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

        findViewById(R.id.buttonAddress1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getApplicationContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE1);
            }
        });

        findViewById(R.id.buttonAddress2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getApplicationContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE2);
            }
        });

        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (place1 == null) {
                    Toast.makeText(MainActivity.this, "Select a start location", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (place2 == null) {
                    Toast.makeText(MainActivity.this, "Select an end location", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("place1", place1);
                intent.putExtra("place2", place2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE1 && data != null) {
            try {
                place1 = Autocomplete.getPlaceFromIntent(data);
            } catch (IllegalArgumentException e) {
                return;
            }
            address1.setText(place1.getAddress());
        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE2 && data != null) {
            try {
                place2 = Autocomplete.getPlaceFromIntent(data);
            } catch (IllegalArgumentException e) {
                return;
            }
            address2.setText(place2.getAddress());
        }
    }
}