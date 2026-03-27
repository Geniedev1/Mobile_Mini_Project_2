package com.example.miniproject_2.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.miniproject_2.entity.Ticket;
import java.util.List;

@Dao
public interface TicketDao extends BaseDao<Ticket> {
    @Query("SELECT * FROM tickets WHERE userId = :userId")
    List<Ticket> getByUserId(int userId);

    @Query("SELECT * FROM tickets WHERE id = :id")
    Ticket getById(int id);
}
