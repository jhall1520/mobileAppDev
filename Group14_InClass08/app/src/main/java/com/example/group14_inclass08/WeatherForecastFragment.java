package com.example.group14_inclass08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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


/**
 * InClass08
 * WeatherForecastFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class WeatherForecastFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LAT = "lat";
    private static final String ARG_LON = "lon";
    private static final String ARG_NAME = "name";

    OkHttpClient client = new OkHttpClient();
    ListView listView;
    ForecastAdapter forecastAdapter;

    ArrayList<Forecast> forecastArrayList = new ArrayList<>();

    private String lat;
    private String lon;
    private String name;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param lat Parameter 1.
     * @param lon Parameter 2.
     * @return A new instance of fragment WeatherForcastFragment.
     */
    public static WeatherForecastFragment newInstance(String lat, String lon, String name) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAT, lat);
        args.putString(ARG_LON, lon);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lat = getArguments().getString(ARG_LAT);
            lon = getArguments().getString(ARG_LON);
            name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        listView = view.findViewById(R.id.listView);
        forecastAdapter = new ForecastAdapter(getActivity(), R.layout.forecast_layout, forecastArrayList);
        listView.setAdapter(forecastAdapter);

        TextView cityCountry = view.findViewById(R.id.textViewCityForecast);
        cityCountry.setText(name);

        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=imperial&appid=32ee5fcb98111e23101d37faee480ad4";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    forecastArrayList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject curObject = jsonArray.getJSONObject(i);

                            String curDate = curObject.getString("dt_txt");

                            JSONArray weather = curObject.getJSONArray("weather");
                            JSONObject main = curObject.getJSONObject("main");

                            String description = weather.getJSONObject(0).getString("description");
                            String icon = weather.getJSONObject(0).getString("icon");
                            String temp = main.getString("temp");
                            String tempMax = main.getString("temp_max");
                            String tempMin = main.getString("temp_min");
                            String humidity = main.getString("humidity");

                            forecastArrayList.add(new Forecast(curDate, temp, tempMax, tempMin, icon, humidity, description));

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                forecastAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }
}