package com.cp3405.joblink;

import android.app.Application;
//import android.app.FragmentManager;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.cp3405.joblink.ui.database.Job;
import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.JobViewModel;
import com.cp3405.joblink.ui.database.User;
import com.cp3405.joblink.ui.database.UserDao;
import com.cp3405.joblink.ui.home.HomeFragment;
import com.cp3405.joblink.ui.jobPost.JobPostFragment;
import com.cp3405.joblink.ui.search.SearchFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private JobViewModel jobViewModel;
    private Context context;
    JobLinkRoomDatabase database;
//    public SearchFragment search = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SearchFragment search = new SearchFragment();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getSupportFragmentManager();
                int count = manager.getBackStackEntryCount();
//                if (manager.getBackStackEntryCount() > 0){
//                    manager.popBackStack();
//                }
//                getSupportFragmentManager().popBackStack();

                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.nav_host_fragment, search,
                        search.getTag());
                transaction.addToBackStack(null);
                transaction.commit();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_friends, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,
                R.id.nav_login)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        jobViewModel = ViewModelProviders.of(this).get(JobViewModel.class);

//        database = new JobLinkRoomDatabase() {
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
//                clearDb();
//            }
//        };

//        System.out.println(database.userDao().isNull());

        final UserDao userDao = JobLinkRoomDatabase.getDatabase(context).userDao();
        JobDao jobDao = JobLinkRoomDatabase.getDatabase(context).jobDao();
//
////        userDao.deleteAll();
//
//        System.out.println(userDao.getAllUsers());
        List<User> users = userDao.getAllUsers();
        List<Job> jobs = jobDao.getAllJobs();
        for (User user : users) {
            System.out.println(user.username);
        }
//
//        User studentUser = new User("Student", "Default", "Student",
//                "student@student.com", "student", 7883368, "image");
//
//        userDao.insert(studentUser);
////        User test = userDao.findUserByUsername("Student");
////        System.out.println(test.username);
//
//        User staffUser = new User("Staff", "Default", "Staff",
//                "staff@staff.com", "staff", 78233, "image");
//
//        userDao.insert(staffUser);
//        userDao.findUserByUsername("Staff");
//
//        User employerUser = new User("Employer", "Default", "Employer",
//                "employer@employer.com", "employer", 3675637, "image");
//
//        userDao.insert(employerUser);
//        userDao.findUserByUsername("Employer");
//
//        System.out.println(userDao.getAllUsers());
//        users = userDao.getAllUsers();
//        for(User user:users) {
//            System.out.println(user.username);
//        }
//        int size = users.size();
//
//        Job jobDefault = new Job("Default Title", "Default Description",
//                userDao.findUserByUsername("Employer").id);
//        jobDao.insert(jobDefault);
//
//        System.out.println(jobDao.getAllJobs());
//        jobs = jobDao.getAllJobs();
        for (Job job : jobs) {
            System.out.println(job.jobTitle);
        }


//        final SearchFragment search = new SearchFragment();
////        final FragmentManager manager = getFragmentManager();
//        final EditText username = findViewById(R.id.text_login_name_entry);
//        final EditText password = findViewById(R.id.text_login_password_entry);
//        Button logInButton = findViewById(R.id.button_login);
//        logInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (username != null && password != null) {
//                    User user = userDao.findUserByUsername(username.getText().toString());
//                    if (user != null) {
//                        // Check database with credentials
//                        if (user.password.equals(password.getText().toString())) {
//                            user.isLoggedIn = true;
//                            userDao.update(user);
//                            Snackbar.make(view, String.format("Logged in as %s", user.userType), Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
//                            manager.beginTransaction().replace(R.id.nav_host_fragment, search,
//                                    search.getTag()).commit();
//                        }
//                    }
//                }
//            }
//        });
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.context = context;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
