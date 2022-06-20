package com.example.group14_inclass08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * InClass08
 * ForecastAdapter.java
 * Joel Hall
 * Jimmy Kropp
 */
public class ForecastAdapter extends ArrayAdapter<Forecast> {


    public ForecastAdapter(@NonNull Context context, int resource, @NonNull List<Forecast> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_layout, parent, false);

        Forecast forecast = getItem(position);

        TextView date = convertView.findViewById(R.id.textViewDate);
        TextView temp = convertView.findViewById(R.id.textViewForecastTemp);
        TextView maxTemp = convertView.findViewById(R.id.textViewForecastMax);
        TextView minTemp = convertView.findViewById(R.id.textViewForecastMin);
        TextView humidity = convertView.findViewById(R.id.textViewForecastHumidity);
        TextView description = convertView.findViewById(R.id.textViewForecastDescr);
        ImageView forecastIcon = convertView.findViewById(R.id.imageViewForecast);

        date.setText(forecast.getDate());
        temp.setText(forecast.getTemp() + " F");
        maxTemp.setText(forecast.getMaxTemp() + " F");
        minTemp.setText(forecast.getMinTemp() + " F");
        humidity.setText(forecast.getHumidity() + "%");
        description.setText(forecast.getDescription());
        Picasso.get().load("http://openweathermap.org/img/wn/" + forecast.getIcon() + "@2x.png").into(forecastIcon);


        return convertView;
    }
}
