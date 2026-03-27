package com.example.miniproject_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SeatSelectionActivity extends BaseActivity {

    private GridView gvSeats;
    private TextView tvSelectedSeat, tvTotalPrice;
    private Button btnConfirm;
    private ShowTime showTime;
    private final ArrayList<String> seats = new ArrayList<>();
    private final Set<String> bookedSeats = new HashSet<>();
    private String selectedSeat;
    private SeatAdapter seatAdapter;

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

        if (showTime == null) {
            Toast.makeText(this, "Không tìm thấy suất chiếu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupSeatGrid();
        updateSelectionInfo();

        btnConfirm.setOnClickListener(v -> {
            if (selectedSeat == null || selectedSeat.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ghế", Toast.LENGTH_SHORT).show();
                return;
            }
            confirmBooking();
        });
    }

    private void setupSeatGrid() {
        for (int i = 1; i <= 30; i++) {
            seats.add(String.format(Locale.getDefault(), "S%02d", i));
        }

        loadBookedSeats();

        seatAdapter = new SeatAdapter();
        gvSeats.setAdapter(seatAdapter);

        gvSeats.setOnItemClickListener((parent, view, position, id) -> {
            String tappedSeat = seats.get(position);

            if (bookedSeats.contains(tappedSeat)) {
                Toast.makeText(this, "Ghế này đã được đặt", Toast.LENGTH_SHORT).show();
                return;
            }

            if (tappedSeat.equals(selectedSeat)) {
                selectedSeat = null;
            } else {
                selectedSeat = tappedSeat;
            }

            updateSelectionInfo();
            seatAdapter.notifyDataSetChanged();
        });
    }

    private void loadBookedSeats() {
        bookedSeats.clear();
        List<String> soldSeats = db.ticketDao().getBookedSeatsByShowTimeId(showTime.id);
        if (soldSeats != null) {
            bookedSeats.addAll(soldSeats);
        }
    }

    private void updateSelectionInfo() {
        if (selectedSeat == null || selectedSeat.isEmpty()) {
            tvSelectedSeat.setText("Ghế đã chọn: Chưa chọn");
            tvTotalPrice.setText("Tổng tiền: 0đ");
            btnConfirm.setEnabled(false);
            btnConfirm.setAlpha(0.6f);
            return;
        }

        tvSelectedSeat.setText("Ghế đã chọn: " + selectedSeat);
        tvTotalPrice.setText(String.format(Locale.getDefault(), "Tổng tiền: %,.0fđ", showTime.price));
        btnConfirm.setEnabled(true);
        btnConfirm.setAlpha(1f);
    }

    private class SeatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return seats.size();
        }

        @Override
        public Object getItem(int position) {
            return seats.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
            }

            TextView tvSeatLabel = view.findViewById(R.id.tvSeatLabel);
            String seat = seats.get(position);
            tvSeatLabel.setText(seat);

            boolean isBooked = bookedSeats.contains(seat);
            boolean isSelected = seat.equals(selectedSeat);

            if (isBooked) {
                tvSeatLabel.setBackgroundResource(R.drawable.bg_seat_booked);
                tvSeatLabel.setTextColor(getColor(android.R.color.white));
                tvSeatLabel.setAlpha(0.85f);
            } else if (isSelected) {
                tvSeatLabel.setBackgroundResource(R.drawable.bg_seat_selected);
                tvSeatLabel.setTextColor(getColor(android.R.color.white));
                tvSeatLabel.setAlpha(1f);
            } else {
                tvSeatLabel.setBackgroundResource(R.drawable.bg_seat_available);
                tvSeatLabel.setTextColor(getColor(android.R.color.black));
                tvSeatLabel.setAlpha(1f);
            }

            return view;
        }
    }

    private void confirmBooking() {
        if (selectedSeat == null || selectedSeat.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ghế", Toast.LENGTH_SHORT).show();
            return;
        }

        int seatBookedCount = db.ticketDao().countBookedSeat(showTime.id, selectedSeat);
        if (seatBookedCount > 0) {
            loadBookedSeats();
            selectedSeat = null;
            updateSelectionInfo();
            if (seatAdapter != null) {
                seatAdapter.notifyDataSetChanged();
            }
            Toast.makeText(this, "Ghế vừa được người khác đặt. Vui lòng chọn ghế khác.", Toast.LENGTH_LONG).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Ticket ticket = new Ticket(showTime.id, prefsHelper.getUserId(), selectedSeat, currentDate, showTime.price);

        long ticketId = db.ticketDao().insert(ticket);

        if (ticketId <= 0) {
            Toast.makeText(this, "Không thể đặt vé, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Đặt vé thành công!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, TicketDetailActivity.class);
        intent.putExtra("TICKET_ID", (int) ticketId);
        startActivity(intent);
        finish();
    }
}
