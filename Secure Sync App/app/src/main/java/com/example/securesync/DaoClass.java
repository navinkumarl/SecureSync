package com.example.securesync;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoClass {

    @Insert
    void registerUser(UserModel user);

    @Query("select * from Users where User_Name=:username and Password=:password")
    UserModel loginUser(String username,String password);

    @Query("select * from Users")
    List<UserModel> viewUser();
}
