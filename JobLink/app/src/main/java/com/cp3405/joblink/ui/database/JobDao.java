package com.cp3405.joblink.ui.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface JobDao {

    @Query("SELECT * FROM jobs WHERE jobID = :jobID LIMIT 1")
    Job findJobById(int jobID);

//    @Insert
//    void insert(Job job);

    @Query("SELECT * FROM jobs WHERE job_title = :title LIMIT 1")
    Job findJobByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Job jobs);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Job... jobs);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Job jobs);

    @Query("DELETE FROM jobs")
    void deleteAll();

    @Query("SELECT * FROM jobs ORDER BY job_title ASC")
    LiveData<List<Job>> getAllJobs();

}
