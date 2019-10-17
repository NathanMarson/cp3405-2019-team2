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

    @Query("SELECT * FROM users WHERE first_name = :firstName LIMIT 1")
    User findUserByName(String firstName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(User users);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(User users);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users ORDER BY first_name ASC")
    List<User> getAllUsers();

//    @Query("SELECT COUNT(DISTINCT `table_name`) FROM `information_schema`.`columns` WHERE `table_schema` = 'your_db_name'")
//    int isNull();
}