package com.cp3405.joblink.ui.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

//    @NonNull
//    public String getId() {
//        return id;
//    }
//
//    @NonNull
//    public String getFirstName() {
//        return this.firstName;
//    }
//
//    @NonNull
//    public String getLastName() {
//        return this.lastName;
//    }
//
//    @NonNull
//    public String getUserType() {
//        return this.userType;
//    }

    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName;
    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName;
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

    public User(String id, String firstName, String lastName, String userType, String email,
                String password, int phoneNum, String image) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.image = image;
    }
}
