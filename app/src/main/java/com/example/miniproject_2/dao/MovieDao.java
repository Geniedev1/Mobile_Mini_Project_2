package com.example.miniproject_2.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.miniproject_2.entity.Movie;
import java.util.List;

@Dao
public interface MovieDao extends BaseDao<Movie> {
    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE id = :id")
    Movie getById(int id);
}
