package com.example.miniproject_2.entity;
https://github.com/Geniedev1/Mobile_Mini_Project_2/pull/4/conflict?name=app%252Fsrc%252Fmain%252Fjava%252Fcom%252Fexample%252Fminiproject_2%252Fentity%252FTicket.java&base_oid=c950b161762319d013cf251ec19ce14d60454e8c&head_oid=0c040859565b5747d6765608bcb2c3035cfce26d
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String password;
    public String fullName;
    public String role;

    public User() {}

    @Ignore
    public User(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }
}
