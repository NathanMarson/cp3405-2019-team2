package com.cp3405.joblink.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.UserDao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = root.findViewById(R.id.text_login);
        loginViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

//    public void test() {
//        JobLinkRoomDatabase jobLinkRoomDatabase = new JobLinkRoomDatabase() {
//            @Override
//            public UserDao userDao() {
//                return null;
//            }
//
//            @Override
//            public JobDao jobDao() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            protected InvalidationTracker createInvalidationTracker() {
//                return null;
//            }
//
//            @Override
//            public void clearAllTables() {
//
//            }
//        };
//    }


}