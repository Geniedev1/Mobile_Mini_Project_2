package com.example.miniproject_2.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets",
        foreignKeys = {
                @ForeignKey(entity = ShowTime.class, parentColumns = "id", childColumns = "showTimeId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("showTimeId"), @Index("userId")})
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int showTimeId;
    public int userId;
    public String seatNumber;
    public String bookingDate;
    public double totalPrice;

    public Ticket() {}

    @Ignore
    public Ticket(int showTimeId, int userId, String seatNumber, String bookingDate, double totalPrice) {
        this.showTimeId = showTimeId;
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
    }
}
