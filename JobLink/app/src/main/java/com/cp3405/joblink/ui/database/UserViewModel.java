package com.cp3405.joblink.ui.database;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {

    private UserDao userDao;
    private LiveData<List<User>> usersLiveData;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userDao = JobLinkRoomDatabase.getDatabase(application).userDao();
        usersLiveData = userDao.getAllUsers();
    }
    public LiveData<List<User>> getUserList() {
        return usersLiveData;
    }
    public void insert(User... users) {
        userDao.insert(users);
    }
    public void update(User user) {
        userDao.update(user);
    }
    public void deleteAll() {
        userDao.deleteAll();
    }
}
