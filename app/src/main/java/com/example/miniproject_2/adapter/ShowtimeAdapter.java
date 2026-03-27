package com.example.miniproject_2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_2.R;
import com.example.miniproject_2.database.AppDatabase;
import com.example.miniproject_2.entity.ShowTime;
import com.example.miniproject_2.entity.Theater;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private List<ShowTime> showTimes;
    private AppDatabase db;
    private OnShowtimeClickListener listener;

    public interface OnShowtimeClickListener {
        void onShowtimeClick(ShowTime showTime);
    }

    public ShowtimeAdapter(List<ShowTime> showTimes, AppDatabase db, OnShowtimeClickListener listener) {
        this.showTimes = showTimes;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        ShowTime showTime = showTimes.get(position);
        Theater theater = db.theaterDao().getById(showTime.theaterId);
        
        if (theater != null) {
            holder.tvTheater.setText(theater.name);
        }
        holder.tvTime.setText(showTime.startTime);
        holder.tvPrice.setText(String.format("%,.0fđ", showTime.price));

        holder.itemView.setOnClickListener(v -> listener.onShowtimeClick(showTime));
    }

    @Override
    public int getItemCount() {
        return showTimes.size();
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTheater, tvTime, tvPrice;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTheater = itemView.findViewById(R.id.tvShowtimeTheater);
            tvTime = itemView.findViewById(R.id.tvShowtimeTime);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
        }
    }
}
