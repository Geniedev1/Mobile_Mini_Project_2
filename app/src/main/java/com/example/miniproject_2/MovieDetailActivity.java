package com.example.miniproject_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniproject_2.adapter.ShowtimeAdapter;
import com.example.miniproject_2.base.BaseActivity;
import com.example.miniproject_2.entity.Movie;
import com.example.miniproject_2.entity.ShowTime;

import java.util.List;

public class MovieDetailActivity extends BaseActivity implements ShowtimeAdapter.OnShowtimeClickListener {

    private ImageView ivPoster;
    private TextView tvTitle, tvDuration, tvDesc;
    private RecyclerView rvShowtimes;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ivPoster = findViewById(R.id.ivDetailPoster);
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvDuration = findViewById(R.id.tvDetailDuration);
        tvDesc = findViewById(R.id.tvDetailDesc);
        rvShowtimes = findViewById(R.id.rvShowtimes);

        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        movie = db.movieDao().getById(movieId);

        if (movie != null) {
            tvTitle.setText(movie.title);
            tvDuration.setText(movie.duration);
            tvDesc.setText(movie.description);
            Glide.with(this).load(movie.imageUrl).into(ivPoster);

            loadShowtimes();
        }
    }

    private void loadShowtimes() {
        List<ShowTime> showTimes = db.showTimeDao().getByMovieId(movie.id);
        rvShowtimes.setLayoutManager(new LinearLayoutManager(this));
        ShowtimeAdapter adapter = new ShowtimeAdapter(showTimes, db, this);
        rvShowtimes.setAdapter(adapter);
    }

    @Override
    public void onShowtimeClick(ShowTime showTime) {
        // Luồng: Chọn Showtime -> Đặt vé (Book Ticket) -> Kiểm tra đăng nhập
        if (!prefsHelper.isLoggedIn()) {
            Toast.makeText(this, "Vui lòng đăng nhập để đặt vé", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            // Chuyển sang màn hình chọn ghế (Seat Selection)
            Intent intent = new Intent(this, SeatSelectionActivity.class);
            intent.putExtra("SHOWTIME_ID", showTime.id);
            startActivity(intent);
        }
    }
}
