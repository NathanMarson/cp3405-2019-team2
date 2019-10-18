package com.cp3405.joblink.ui.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = "username", unique = true)})
public class User {

//    @NonNull
//    public String getId() {
//        return id;
//    }
//
//    @NonNull
//    public String getFirstName() {
//        return this.username;
//    }
//
//    @NonNull
//    public String getLastName() {
//        return this.name;
//    }
//
//    @NonNull
//    public String getUserType() {
//        return this.userType;
//    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @NonNull
    @ColumnInfo(name = "username")
    public String username;
    @NonNull
    @ColumnInfo(name = "name")
    public String name;
    @NonNull
    @ColumnInfo(name = "user_type")
    public String userType;
    @NonNull
    @ColumnInfo(name = "email")
    public String email;
    @NonNull
    @ColumnInfo(name = "password")
    public String password;
    @NonNull
    @ColumnInfo(name = "phone_num")
    public int phoneNum;
    @NonNull
    @ColumnInfo(name = "image")
    public String image;
    @NonNull
    @ColumnInfo(name = "is_logged_in")
    public boolean isLoggedIn;

    public User(String username, String name, String userType, String email,
                String password, int phoneNum, String image, boolean isLoggedIn) {
        this.username = username;
        this.name = name;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.image = image;
        this.isLoggedIn = isLoggedIn;
    }
}
