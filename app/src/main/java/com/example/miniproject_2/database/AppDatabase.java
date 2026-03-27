package com.example.miniproject_2.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.miniproject_2.dao.MovieDao;
import com.example.miniproject_2.dao.ShowTimeDao;
import com.example.miniproject_2.dao.TheaterDao;
import com.example.miniproject_2.dao.TicketDao;
import com.example.miniproject_2.dao.UserDao;
import com.example.miniproject_2.entity.Movie;
import com.example.miniproject_2.entity.ShowTime;
import com.example.miniproject_2.entity.Theater;
import com.example.miniproject_2.entity.Ticket;
import com.example.miniproject_2.entity.User;

@Database(entities = {User.class, Movie.class, Theater.class, ShowTime.class, Ticket.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "movie_booking.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();
    public abstract MovieDao movieDao();
    public abstract TheaterDao theaterDao();
    public abstract ShowTimeDao showTimeDao();
    public abstract TicketDao ticketDao();

    public void seedInitialData() {
        if (userDao().findByUsername("admin") == null) {
            userDao().insert(new User("admin", "123", "Admin User", "admin"));
            userDao().insert(new User("user", "123", "Normal User", "user"));

            long m1 = movieDao().insert(new Movie("Avengers: Endgame", "The grave course of events set in motion by Thanos...", "https://m.media-amazon.com/images/I/81ai6zx6eXL._AC_SL1500_.jpg", "181 min"));
            long m2 = movieDao().insert(new Movie("Inception", "A thief who steals corporate secrets through dream-sharing technology...", "https://m.media-amazon.com/images/I/912AErFSBHL._AC_SL1500_.jpg", "148 min"));
            long m3 = movieDao().insert(new Movie("The Dark Knight", "When the menace known as the Joker wreaks havoc...", "https://m.media-amazon.com/images/I/91ebI2xS87L._AC_SL1500_.jpg", "152 min"));

            long t1 = theaterDao().insert(new Theater("CGV Vincom", "Vincom Center, 72 Le Thanh Ton, District 1"));
            long t2 = theaterDao().insert(new Theater("Lotte Cinema", "Lotte Mart, District 7"));

            showTimeDao().insert(new ShowTime((int)m1, (int)t1, "10:00 AM", 120000));
            showTimeDao().insert(new ShowTime((int)m1, (int)t2, "02:00 PM", 100000));
            showTimeDao().insert(new ShowTime((int)m2, (int)t1, "11:30 AM", 110000));
            showTimeDao().insert(new ShowTime((int)m3, (int)t2, "08:00 PM", 130000));
        }
    }
}
