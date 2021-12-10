package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User ...users);

    @Query("select * from user where useremail = :email and userpassword = :password")
    List<User> checkUser(String email , String password);

    @Query("select * from user where useremail = :mail")
    List<User> checkemail(String mail);

    @Query("update user set userpassword = :password where useremail = :mail")
    void updatepassword(String mail , String password);

}