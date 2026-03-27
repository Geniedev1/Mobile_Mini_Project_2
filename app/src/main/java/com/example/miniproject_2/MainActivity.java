package com.example.miniproject_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_2.adapter.MovieAdapter;
import com.example.miniproject_2.adapter.TheaterAdapter;
import com.example.miniproject_2.base.BaseActivity;
import com.example.miniproject_2.entity.Movie;
import com.example.miniproject_2.entity.Theater;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends BaseActivity implements MovieAdapter.OnMovieClickListener, TheaterAdapter.OnTheaterClickListener {

    private TextView tvUserStatus, tvListTitle;
    private ImageView ivMyTickets;
    private RecyclerView rvMain;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.seedInitialData();

        tvUserStatus = findViewById(R.id.tvUserStatus);
        tvListTitle = findViewById(R.id.tvListTitle);
        ivMyTickets = findViewById(R.id.ivMyTickets);
        rvMain = findViewById(R.id.rvMain);
        tabLayout = findViewById(R.id.tabLayout);

        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        loadMovies();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loadMovies();
                } else {
                    loadTheaters();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Xử lý nút Login/User
        tvUserStatus.setOnClickListener(v -> {
            if (!prefsHelper.isLoggedIn()) {
                // Nếu chưa login -> Chuyển sang màn hình Login
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                // Nếu đã login -> Hỏi có muốn Logout không
                showLogoutDialog();
            }
        });

        ivMyTickets.setOnClickListener(v -> {
            if (!prefsHelper.isLoggedIn()) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                Toast.makeText(this, "Tính năng vé của tôi đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có muốn đăng xuất khỏi tài khoản " + prefsHelper.getUsername() + "?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    prefsHelper.clearSession(); // Xóa SharedPreferences
                    updateUI(); // Cập nhật lại giao diện
                    Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (prefsHelper.isLoggedIn()) {
            tvUserStatus.setText(prefsHelper.getUsername());
        } else {
            tvUserStatus.setText("Login");
        }
    }

    private void loadMovies() {
        tvListTitle.setText("Now Showing");
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        List<Movie> movies = db.movieDao().getAll();
        MovieAdapter adapter = new MovieAdapter(movies, this);
        rvMain.setAdapter(adapter);
    }

    private void loadTheaters() {
        tvListTitle.setText("Cinemas");
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        List<Theater> theaters = db.theaterDao().getAll();
        TheaterAdapter adapter = new TheaterAdapter(theaters, this);
        rvMain.setAdapter(adapter);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("MOVIE_ID", movie.id);
        startActivity(intent);
    }

    @Override
    public void onBookClick(Movie movie) {
        onMovieClick(movie);
    }

    @Override
    public void onTheaterClick(Theater theater) {
        Toast.makeText(this, "Selected: " + theater.name, Toast.LENGTH_SHORT).show();
    }
}
