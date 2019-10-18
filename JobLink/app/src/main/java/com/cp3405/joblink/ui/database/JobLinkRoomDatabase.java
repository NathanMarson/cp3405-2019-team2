package com.cp3405.joblink.ui.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Job.class}, version = 1, exportSchema = false)
public abstract class JobLinkRoomDatabase extends RoomDatabase {

    private static JobLinkRoomDatabase jobLinkRoomDatabase;
    private static final String DB_NAME = "job_line.db";

    public abstract UserDao userDao();
    public abstract JobDao jobDao();

//    private static volatile JobLinkRoomDatabase jobLinkRoomDatabase;

    public static JobLinkRoomDatabase getDatabase(final Context context) {
//        context.deleteDatabase(DB_NAME); // Reset Database //
        if (jobLinkRoomDatabase == null) {
            synchronized (JobLinkRoomDatabase.class) {
                if (jobLinkRoomDatabase == null) {
                    jobLinkRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            JobLinkRoomDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("JobLinkRoomDatabase", "populating with data...");
                                    new PopulateDbAsync(jobLinkRoomDatabase).execute();
                                }
                            }).build();
//                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
//                            .addCallback(new RoomDatabase.Callback() {
//                                @Override
//                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                                    super.onCreate(db);
//                                    Log.d("MoviesDatabase", "populating with data...");
//                                    new PopulateDbAsync(INSTANCE).execute();
//                                }
//                            })
//                            .build();
                }
            }
        }
        return jobLinkRoomDatabase;
    }
    public void clearDb() {
        if (jobLinkRoomDatabase != null) {
            new PopulateDbAsync(jobLinkRoomDatabase).execute();
        }
    }
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final JobDao jobDao;
        public PopulateDbAsync(JobLinkRoomDatabase jobLinkRoomDatabase) {
            userDao = jobLinkRoomDatabase.userDao();
            jobDao = jobLinkRoomDatabase.jobDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            userDao.deleteAll();
//            jobDao.deleteAll();
            User studentUser = new User("Student", "Default", "Student",
                    "student@student.com", "student", 7883368, "image", false);
            User staffUser = new User("Staff", "Default", "Staff",
                    "staff@staff.com", "staff", 78233, "image", false);
            User employerUser = new User("Employer", "Default", "Employer",
                    "employer@employer.com", "employer", 3675637, "image", false);
            userDao.insert(studentUser, staffUser, employerUser);

            Job jobDefault = new Job("Default Title", "Default Description",
                    userDao.findUserByUsername("Employer").id);
            jobDao.insert(jobDefault);
//            Job movieOne = new Job("The Big Short", (int) jobDao.insert(directorOne));
//            final int dIdTwo = (int) jobDao.insert(directorTwo);
//            Job movieTwo = new Job("Arrival", dIdTwo);
//            Job movieThree = new Job("Blade Runner 2049", dIdTwo);
//            Job movieFour = new Job("Passengers", (int) jobDao.insert(directorThree));
            return null;
        }
    }

}