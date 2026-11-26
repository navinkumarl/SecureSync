package com.example.securesync;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Users")
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "User_Name")
    private String name;
    @ColumnInfo(name = "Phone_No")
    private String phone;
    @ColumnInfo(name = "Email")
    private String mail;
    @ColumnInfo(name = "Password")
    private String password;

    public int getId() { return id; }
    public void setId(int id) {
        this.id=id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name=name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone=phone;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail=mail;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password=password;
    }
}
