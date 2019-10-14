package com.cp3405.joblink.ui.login;

import android.app.Application;

import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.UserDao;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LoginViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("Please Login");
    }

    public LiveData<String> getText() {
        return mText;
    }
}