package com.cp3405.joblink.ui.database;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class JobViewModel extends AndroidViewModel {

    private JobDao jobDao;
    private LiveData<List<Job>> jobsLiveData;
    public JobViewModel(@NonNull Application application) {
        super(application);
        jobDao = JobLinkRoomDatabase.getDatabase(application).jobDao();
        jobsLiveData = jobDao.getAllJobs();
    }
    public LiveData<List<Job>> getJobList() {
        return jobsLiveData;
    }
    public void insert(Job... jobs) {
        jobDao.insert(jobs);
    }
    public void update(Job job) {
        jobDao.update(job);
    }
    public void deleteAll() {
        jobDao.deleteAll();
    }
}
