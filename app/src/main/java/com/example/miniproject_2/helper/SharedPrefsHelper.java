package com.example.miniproject_2.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Quản lý trạng thái đăng nhập bằng SharedPreferences.
 * Dùng Singleton Pattern để tránh tạo nhiều instance.
 */
public class SharedPrefsHelper {

    private static final String PREF_NAME    = "app_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID  = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE     = "role";

    private static SharedPrefsHelper instance;
    private final SharedPreferences prefs;

    // Constructor private – chỉ tạo qua getInstance()
    private SharedPrefsHelper(Context context) {
        prefs = context.getApplicationContext()
                       .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefsHelper(context);
        }
        return instance;
    }

    // ── Lưu session sau khi đăng nhập thành công ─────────────
    public void saveLoginSession(int userId, String username, String role) {
        prefs.edit()
             .putBoolean(KEY_IS_LOGGED_IN, true)
             .putInt(KEY_USER_ID, userId)
             .putString(KEY_USERNAME, username)
             .putString(KEY_ROLE, role)
             .apply();
    }

    // ── Kiểm tra đã đăng nhập chưa ──────────────────────────
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // ── Lấy thông tin user đang đăng nhập ───────────────────
    public int    getUserId()   { return prefs.getInt(KEY_USER_ID, -1); }
    public String getUsername() { return prefs.getString(KEY_USERNAME, ""); }
    public String getRole()     { return prefs.getString(KEY_ROLE, ""); }

    // ── Xóa session khi đăng xuất ───────────────────────────
    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
