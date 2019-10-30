package com.cp3405.joblink.ui.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User findUserById(int id);

//    @Insert
//    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :firstName LIMIT 1")
    User findUserByUsername(String firstName);

    @Query("SELECT * FROM users WHERE is_logged_in = 1 LIMIT 1")
    User findUserByLogin();

    @Query("SELECT * FROM users WHERE name LIKE :search OR username LIKE :search " +
            "OR user_type LIKE :search OR email LIKE :search OR phone_num LIKE :search")
    List<User> findUsersBySearch(String search);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(User users);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(User... users);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users ORDER BY username ASC")
    List<User> getAllUsers();

//    @Query("SELECT COUNT(DISTINCT `table_name`) FROM `information_schema`.`columns` WHERE `table_schema` = 'your_db_name'")
//    int isNull();
}