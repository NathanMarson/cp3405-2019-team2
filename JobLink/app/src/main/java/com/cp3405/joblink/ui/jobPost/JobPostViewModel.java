package com.cp3405.joblink.ui.jobPost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JobPostViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JobPostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Post a Job");
    }

    public LiveData<String> getText() {
        return mText;
    }
}