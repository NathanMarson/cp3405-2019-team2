package com.cp3405.joblink.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.Job;
import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.User;
import com.cp3405.joblink.ui.database.UserDao;
import com.cp3405.joblink.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private Context context;

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

//        Context context = getApplicationContext();

        final UserDao userDao = JobLinkRoomDatabase.getDatabase(context).userDao();
        JobDao jobDao = JobLinkRoomDatabase.getDatabase(context).jobDao();

        try {

            User user = userDao.findUserByLogin();
            user.isLoggedIn = false;
            userDao.update(user);
        } catch(Exception e){
            Log.i("error", "No users have logged in");
        }

        List<User> users = userDao.getAllUsers();
        List<Job> jobs = jobDao.getAllJobs();
      
        final HomeFragment home = new HomeFragment();
        final FragmentManager manager = getFragmentManager();
        final EditText username = root.findViewById(R.id.text_login_name_entry);
        final EditText password = root.findViewById(R.id.text_login_password_entry);
        Button logInButton = root.findViewById(R.id.button_login);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username != null && password != null){
                    User user = userDao.findUserByUsername(username.getText().toString());
                    if (user != null) {
                        // Check database with credentials
                        if (user.password.equals(password.getText().toString())) {
                            user.isLoggedIn = true;
                            userDao.update(user);
                            Snackbar.make(view, String.format("Logged in as %s", user.userType), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            manager.beginTransaction().replace(R.id.nav_host_fragment, home,
                                    home.getTag()).commit();
                        } else {
                            Snackbar.make(view, "Incorrect username or password", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } // Add new user if username is not recognised
                }

//                if (username.getText().toString().toLowerCase().equals("employer")){
//                    //Check database with credentials
//                    Snackbar.make(view, "Logged in as an employer", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    manager.beginTransaction().replace(R.id.nav_host_fragment, home,
//                            home.getTag()).commit();
//                }
//                else {
//                    Snackbar.make(view, "Incorrect username or password", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//
//                }

            }
        });
      
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onStop() {
        super.onStop();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}