package com.example.miniproject_2.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String imageUrl;
    public String duration;

    public Movie() {}

    @Ignore
    public Movie(String title, String description, String imageUrl, String duration) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.duration = duration;
    }
}
