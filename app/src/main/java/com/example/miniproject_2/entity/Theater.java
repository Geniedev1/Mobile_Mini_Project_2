package com.example.miniproject_2.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String address;

    public Theater() {}

    @Ignore
    public Theater(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
