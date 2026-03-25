package com.example.miniproject_2.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

/**
 * BaseDao<T> – Interface generic chứa các thao tác CRUD cơ bản.
 * Mọi DAO cụ thể phải extends interface này, tránh viết lặp code.
 *
 * @param <T> – Kiểu Entity (VD: User, Product, Order…)
 */
public interface BaseDao<T> {

    // Thêm 1 bản ghi; trả về rowId (long)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T entity);

    // Thêm nhiều bản ghi cùng lúc
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(T... entities);

    // Cập nhật 1 bản ghi (dựa theo @PrimaryKey)
    @Update
    void update(T entity);

    // Xóa 1 bản ghi (dựa theo @PrimaryKey)
    @Delete
    void delete(T entity);
}
