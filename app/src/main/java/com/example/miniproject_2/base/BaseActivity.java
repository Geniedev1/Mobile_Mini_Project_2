package com.example.miniproject_2.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject_2.database.AppDatabase;
import com.example.miniproject_2.helper.SharedPrefsHelper;

/**
 * BaseActivity – Activity cha mà mọi màn hình trong app đều kế thừa.
 * Cung cấp sẵn:
 *   - db           : AppDatabase (Room)
 *   - prefsHelper  : SharedPrefsHelper (SharedPreferences)
 *
 * Các Activity con chỉ cần extends BaseActivity và dùng ngay hai field trên.
 */
public abstract class BaseActivity extends AppCompatActivity {

    // Hai field này có thể truy cập từ mọi Activity con (protected)
    protected AppDatabase      db;
    protected SharedPrefsHelper prefsHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo Room Database (Singleton)
        db = AppDatabase.getInstance(this);

        // Khởi tạo SharedPreferences helper (Singleton)
        prefsHelper = SharedPrefsHelper.getInstance(this);
    }
}
