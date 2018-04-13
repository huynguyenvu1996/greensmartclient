package com.group07.greensmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group07.greensmart.R;
import com.group07.greensmart.model.OpenWeather;
import com.group07.greensmart.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenvuhuy on 4/11/18.
 */

public class OpenWeatherAdapter extends RecyclerView.Adapter<OpenWeatherAdapter.OpenWeatherViewHolder> {

    private Context context;
    private ArrayList<OpenWeather> listOpenWeather;

    public OpenWeatherAdapter(Context context, ArrayList<OpenWeather> listOpenWeather) {
        this.context = context;
        this.listOpenWeather = listOpenWeather;
    }

    @Override
    public OpenWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_agp_weather_forecast, parent, false);
        return new OpenWeatherAdapter.OpenWeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OpenWeatherViewHolder holder, int position) {

        holder.txtDescription.setText(listOpenWeather.get(position).getDescription());
        holder.txtDate.setText(DateUtils.convertDate(listOpenWeather.get(position).getDt(), DateUtils.DATE_FORMAT_SIMPLE));
        holder.txtTemperature.setText(context.getString(R.string.item_agp_temperature_forecast, String.valueOf((int) listOpenWeather.get(position).getTemperature())));
        holder.txtHumidity.setText(context.getString(R.string.item_agp_humidity_forecast, String.valueOf((int) listOpenWeather.get(position).getHumidity())) + "%");
        holder.txtRain.setText(listOpenWeather.get(position).isRain() ? "Có mưa" : "Không mưa");
        Picasso.get().load(listOpenWeather.get(position).getIcon()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listOpenWeather.size();
    }


    public class OpenWeatherViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDate, txtCityName, txtTemperature, txtHumidity, txtRain, txtDescription;
        public CircleImageView image;

        public OpenWeatherViewHolder(View view) {
            super(view);
            txtTemperature = view.findViewById(R.id.txt_item_agp_temperature);
            txtHumidity = view.findViewById(R.id.txt_item_agp_humidity);
            txtDescription = view.findViewById(R.id.txt_item_agp_description);
            txtDate = view.findViewById(R.id.txt_item_agp_date);
            txtRain = view.findViewById(R.id.txt_item_agp_rain);
            image = view.findViewById(R.id.img_item_agp_forecast_image);
        }
    }
}
