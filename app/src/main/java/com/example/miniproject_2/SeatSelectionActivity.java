package com.example.miniproject_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproject_2.base.BaseActivity;
import com.example.miniproject_2.entity.ShowTime;
import com.example.miniproject_2.entity.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SeatSelectionActivity extends BaseActivity {

    private GridView gvSeats;
    private TextView tvSelectedSeat, tvTotalPrice;
    private Button btnConfirm;
    private ShowTime showTime;
    private String selectedSeat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        gvSeats = findViewById(R.id.gvSeats);
        tvSelectedSeat = findViewById(R.id.tvSelectedSeat);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnConfirm = findViewById(R.id.btnConfirmBooking);

        int showTimeId = getIntent().getIntExtra("SHOWTIME_ID", -1);
        showTime = db.showTimeDao().getById(showTimeId);

        if (showTime != null) {
            setupSeatGrid();
        }

        btnConfirm.setOnClickListener(v -> {
            if (selectedSeat.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ghế", Toast.LENGTH_SHORT).show();
                return;
            }
            confirmBooking();
        });
    }

    private void setupSeatGrid() {
        ArrayList<String> seats = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            seats.add("S" + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seats);
        gvSeats.setAdapter(adapter);

        gvSeats.setOnItemClickListener((parent, view, position, id) -> {
            selectedSeat = seats.get(position);
            tvSelectedSeat.setText("Seat: " + selectedSeat);
            tvTotalPrice.setText(String.format("Total: %,.0fđ", showTime.price));
        });
    }

    private void confirmBooking() {
        // Luồng: Tạo Ticket
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Ticket ticket = new Ticket(showTime.id, prefsHelper.getUserId(), selectedSeat, currentDate, showTime.price);
        
        long ticketId = db.ticketDao().insert(ticket);
        
        Toast.makeText(this, "Đặt vé thành công!", Toast.LENGTH_SHORT).show();
        
        // Luồng cuối: Hiển thị vé đã đặt (Chuyển sang TicketDetailActivity)
        Intent intent = new Intent(this, TicketDetailActivity.class);
        intent.putExtra("TICKET_ID", (int)ticketId);
        startActivity(intent);
        finish();
    }
}
