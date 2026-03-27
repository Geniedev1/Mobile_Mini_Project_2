package com.example.miniproject_2.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.miniproject_2.entity.Theater;
import java.util.List;

@Dao
public interface TheaterDao extends BaseDao<Theater> {
    @Query("SELECT * FROM theaters")
    List<Theater> getAll();

    @Query("SELECT * FROM theaters WHERE id = :id")
    Theater getById(int id);
}
