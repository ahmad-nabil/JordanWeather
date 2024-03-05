package com.ahmad.jordanweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.jordanweather.ApiWeatherPackage.daily;
import com.ahmad.jordanweather.R;
import com.ahmad.jordanweather.databinding.ItemsRecycleBinding;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainRv extends RecyclerView.Adapter<MainRv.holderWeather> {
List<daily>dailyList;
Context context;

    public MainRv(List<daily> dailyList, Context context) {
        this.dailyList = dailyList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainRv.holderWeather onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsRecycleBinding recycleBinding=ItemsRecycleBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new holderWeather(recycleBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRv.holderWeather holder, int position) {
holder.recycleBinding.summary.setText(dailyList.get(position).getSummary());
holder.recycleBinding.max.setText((Math.round(dailyList.get(position).getTemp().getMax()- 273.15))+"\t C");
holder.recycleBinding.min.setText((Math.round(dailyList.get(position).getTemp().getMin()- 273.15))+"\t C");
if(dailyList.get(position).getSummary().contains("rain")){
    holder.recycleBinding.weather.setImageDrawable(AppCompatResources.getDrawable(context,R.drawable.rain));
}else {
    holder.recycleBinding.weather.setImageDrawable(AppCompatResources.getDrawable(context,R.drawable.summer));
}
        long date=dailyList.get(position).getDt();
        LocalDateTime localDateTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault());
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateWeather=localDateTime.format(formatter);
        holder.recycleBinding.date.setText(dateWeather);
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public class holderWeather extends RecyclerView.ViewHolder{
        ItemsRecycleBinding recycleBinding;
        public holderWeather(@NonNull ItemsRecycleBinding recycleBinding) {
            super(recycleBinding.getRoot());
            this.recycleBinding=recycleBinding;
        }
    }
}
