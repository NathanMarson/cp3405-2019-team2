package com.cp3405.joblink.ui.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Job.class}, version = 1)
public abstract class JobLinkRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract JobDao jobDao();

    private static volatile JobLinkRoomDatabase jobLinkRoomDatabase;

    static JobLinkRoomDatabase getJobLinkRoomDatabase(final Context context) {
        if (jobLinkRoomDatabase == null) {
            synchronized (JobLinkRoomDatabase.class) {
                if (jobLinkRoomDatabase == null) {
                    jobLinkRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            JobLinkRoomDatabase.class, "job_link_database").build();
                }
            }
        }
        return jobLinkRoomDatabase;
    }
}
