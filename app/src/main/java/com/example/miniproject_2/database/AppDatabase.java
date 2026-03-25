package com.example.miniproject_2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// ── Khai báo @Database ────────────────────────────────────────────────────────
// entities  : Danh sách các @Entity (thêm class vào mảng này khi tạo bảng mới)
// version   : Tăng lên mỗi khi thay đổi schema (thêm bảng, thêm cột...)
// exportSchema : Tắt export schema file (đơn giản hóa cho project học tập)
@Database(entities = {}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "miniproject2.db";
    private static volatile AppDatabase instance; // volatile đảm bảo thread-safe

    // ── Singleton Pattern ─────────────────────────────────────────────────────
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) { // Double-checked locking
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME
                    )
                    // Cho phép query trên main thread (chỉ dùng khi học/demo)
                    // Thực tế nên dùng AsyncTask / ExecutorService / LiveData
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() // Xóa DB cũ khi lên version mới
                    .build();
                }
            }
        }
        return instance;
    }

    // ── Khai báo abstract DAO tại đây (thêm khi tạo DAO mới) ─────────────────
    // Ví dụ:
    // public abstract UserDao userDao();
    // public abstract ProductDao productDao();
}
