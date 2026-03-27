package com.example.miniproject_2.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.miniproject_2.entity.ShowTime;
import java.util.List;

@Dao
public interface ShowTimeDao extends BaseDao<ShowTime> {
    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<ShowTime> getByMovieId(int movieId);

    @Query("SELECT * FROM showtimes WHERE id = :id")
    ShowTime getById(int id);
}
