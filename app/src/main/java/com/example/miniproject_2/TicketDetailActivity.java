package com.example.miniproject_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.miniproject_2.base.BaseActivity;
import com.example.miniproject_2.entity.Movie;
import com.example.miniproject_2.entity.ShowTime;
import com.example.miniproject_2.entity.Theater;
import com.example.miniproject_2.entity.Ticket;

public class TicketDetailActivity extends BaseActivity {

    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvPrice, tvId;
    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        tvMovie = findViewById(R.id.tvTicketMovie);
        tvTheater = findViewById(R.id.tvTicketTheater);
        tvTime = findViewById(R.id.tvTicketTime);
        tvSeat = findViewById(R.id.tvTicketSeat);
        tvPrice = findViewById(R.id.tvTicketPrice);
        tvId = findViewById(R.id.tvTicketId);
        btnBackHome = findViewById(R.id.btnBackHome);

        int ticketId = getIntent().getIntExtra("TICKET_ID", -1);
        Ticket ticket = db.ticketDao().getById(ticketId);

        if (ticket != null) {
            ShowTime showTime = db.showTimeDao().getById(ticket.showTimeId);
            if (showTime != null) {
                Movie movie = db.movieDao().getById(showTime.movieId);
                Theater theater = db.theaterDao().getById(showTime.theaterId);

                if (movie != null) tvMovie.setText(movie.title);
                if (theater != null) tvTheater.setText(theater.name);
                tvTime.setText("Time: " + showTime.startTime);
            }
            tvSeat.setText("Seat: " + ticket.seatNumber);
            tvPrice.setText(String.format("Price: %,.0fđ", ticket.totalPrice));
            tvId.setText("ID: #" + ticket.id + " | " + ticket.bookingDate);
        }

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
