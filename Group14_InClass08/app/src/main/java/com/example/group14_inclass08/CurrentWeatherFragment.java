package com.example.group14_inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * InClass08
 * CurrentWeatherFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    OkHttpClient client = new OkHttpClient();
    String lat;
    String lon;
    String icon;
    TextView cityView;
    TextView desc;
    TextView temperature;
    TextView maxTemp;
    TextView minTemp;
    TextView wSpeed;
    TextView wDegree;
    TextView cloudi;
    TextView humid;
    ImageView weatherIcon;
    Weather weatherObject;
    Button checkForecast;
    CListener cListener;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        cityView = view.findViewById(R.id.textViewCity);
        temperature = view.findViewById(R.id.textViewTemperature);
        maxTemp = view.findViewById(R.id.textViewShowMax);
        minTemp = view.findViewById(R.id.textViewShowMin);
        desc = view.findViewById(R.id.textViewShowDescr);
        humid = view.findViewById(R.id.textViewShowHumidity);
        wSpeed = view.findViewById(R.id.textViewShowWindSpeed);
        wDegree = view.findViewById(R.id.textViewShowWindDegree);
        cloudi = view.findViewById(R.id.textViewShowCloudiness);
        weatherIcon = view.findViewById(R.id.imageViewIcon);
        checkForecast = view.findViewById(R.id.buttonCheck);
        String city = mCity.getCity();
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&appid=32ee5fcb98111e23101d37faee480ad4";
        Request request1 = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        lat = jsonArray.getJSONObject(0).getString("lat");
                        lon = jsonArray.getJSONObject(0).getString("lon");

                        getWeather();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        view.findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cListener.goToWeatherForecastFragment(lat, lon, cityView.getText().toString());
            }
        });

        return view;
    }

    private void getWeather(){

        String url2 = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=imperial&appid=32ee5fcb98111e23101d37faee480ad4";
        Request request2 = new Request.Builder()
                .url(url2)
                .build();
        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray weather = jsonObject.getJSONArray("weather");
                        JSONObject main = jsonObject.getJSONObject("main");
                        JSONObject wind = jsonObject.getJSONObject("wind");
                        JSONObject cloud = jsonObject.getJSONObject("clouds");

                        String description = weather.getJSONObject(0).getString("description");
                        icon = weather.getJSONObject(0).getString("icon");
                        String temp = main.getString("temp");
                        String tempMax = main.getString("temp_max");
                        String tempMin = main.getString("temp_min");
                        String windSpeed = wind.getString("speed");
                        String windDegree = wind.getString("deg");
                        String cloudiness = cloud.getString("all");
                        String humidity = main.getString("humidity");

                        weatherObject = new Weather(mCity.getCity(), mCity.getCountry(), description, icon, temp, tempMax, tempMin,
                                windSpeed, windDegree, cloudiness, humidity);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("tag","Updating.....");
                                String countryCity = mCity.getCity() + "," + mCity.getCountry();
                                cityView.setText(countryCity);
                                temperature.setText(weatherObject.getTemp() + " F");
                                maxTemp.setText(weatherObject.getTempMax() + " F");
                                minTemp.setText(weatherObject.getTempMin() + " F");
                                desc.setText(weatherObject.getDescription());
                                humid.setText(weatherObject.getHumidity() + "%");
                                wSpeed.setText(weatherObject.getWindSpeed() + " miles/hr");
                                wDegree.setText(weatherObject.getWindDegree() + " degrees");
                                cloudi.setText(weatherObject.getCloudiness() + "%");
                                checkForecast.setVisibility(View.VISIBLE);
                                Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(weatherIcon);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CListener) {
            cListener = (CListener) context;
        } else {
            throw new IllegalStateException("MainActivity does not implement CListener");
        }
    }

    public interface CListener {
        void goToWeatherForecastFragment(String lat, String lon, String name);
    }
}