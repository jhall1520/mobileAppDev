package com.example.group14_inclass08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * InClass08
 * MainActivity.java
 * Joel Hall
 * Jimmy Kropp
 */
public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener, CurrentWeatherFragment.CListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment())
                .commit();
    }

    @Override
    public void gotoCurrentWeather(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CurrentWeatherFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToWeatherForecastFragment(String lat, String lon, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherForecastFragment.newInstance(lat, lon, name))
                .addToBackStack(null)
                .commit();
    }
}