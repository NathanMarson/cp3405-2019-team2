package com.cp3405.joblink.ui.search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.Job;
import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.User;
import com.cp3405.joblink.ui.database.UserDao;
import com.cp3405.joblink.ui.job.JobFragment;
import com.cp3405.joblink.ui.profile.ProfileFragment;
import com.google.android.material.snackbar.Snackbar;
import com.cp3405.joblink.ui.home.HomeFragment;
import com.cp3405.joblink.ui.search.SearchFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private Context context;
    private View root;

    private JobDao jobDao;
    private UserDao userDao;

    private List<User> users;
    private List<Job> jobs;

    private EditText searchBar;
    private TableLayout searchViewTableLayout;

    private JobFragment jobView;
    private ProfileFragment profileView;
    private FragmentManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        final TextView textView = root.findViewById(R.id.search_title);
        searchBar = root.findViewById(R.id.search_bar);
        searchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final SearchFragment search = new SearchFragment();
        final HomeFragment home = new HomeFragment();
        manager = getFragmentManager();
        jobView = new JobFragment();
        profileView = new ProfileFragment();

        jobDao = JobLinkRoomDatabase.getDatabase(getContext()).jobDao();
        userDao = JobLinkRoomDatabase.getDatabase(getContext()).userDao();

        users = userDao.getAllUsers();
        jobs = jobDao.getAllJobs();

        final EditText jobTitle = root.findViewById(R.id.text_job_post_name);
        final EditText jobDes = root.findViewById(R.id.text_job_post_description);

        int count;

        Button searchButton = root.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                populateSearchView();

            }
        });

        populateSearchView();

        return root;
    }

    private void populateSearchView() {

        searchViewTableLayout = root.findViewById(R.id.main_table);

        searchViewTableLayout.removeAllViews();

        String search = "%" + searchBar.getText().toString() + "%";

        populateJobHeadings();

        populateJobListings(search);

        populateUserHeadings();

        populateUserListings(search);
    }

    private void populateUserListings(String search) {
        int count;
        count=0;

        List<User> searchedUsers = userDao.findUsersBySearch(search);

        for(final User user:searchedUsers) {

            String text = user.name + "\nUser Type: " + user.userType + "\nEmail: " + user.email
                    + "\nPhone No.: " + user.phoneNum;

            String name = user.name;
            String userType = user.userType;
            String email = user.email;
            int phoneNum = user.phoneNum;

            //            jobList.setText(text);
            // Create the table row
            TableRow tr = new TableRow(context);
            int textColor;
            if (count % 2 != 0) {
                tr.setBackgroundColor(Color.GRAY);
                textColor = Color.WHITE;
            } else {
                textColor = Color.BLACK;
            }
            tr.setId(100 + count);
            tr.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            //Create two columns to add as table data
            // Create a TextView to add date
            TextView labelNameInstance = new TextView(context);
            labelNameInstance.setId(200 + count);
            labelNameInstance.setText(name);
            labelNameInstance.setPadding(1, 0, 5, 0);
            labelNameInstance.setTextColor(textColor);
            tr.addView(labelNameInstance);
            TextView labelUserTypeInstance = new TextView(context);
            labelUserTypeInstance.setId(200 + count);
            labelUserTypeInstance.setText(userType);
            labelUserTypeInstance.setPadding(2, 0, 4, 0);
            labelUserTypeInstance.setTextColor(textColor);
            tr.addView(labelUserTypeInstance);
            TextView labelEmailInstance = new TextView(context);
            labelEmailInstance.setId(200 + count);
            labelEmailInstance.setText(email);
            labelEmailInstance.setPadding(3, 0, 3, 0);
            labelEmailInstance.setTextColor(textColor);
            tr.addView(labelEmailInstance);
            TextView labelUserPhoneNumberInstance = new TextView(context);
            labelUserPhoneNumberInstance.setId(200 + count);
            labelUserPhoneNumberInstance.setText(String.valueOf(phoneNum));
            labelUserPhoneNumberInstance.setPadding(4, 0, 2, 0);
            labelUserPhoneNumberInstance.setTextColor(textColor);
            tr.addView(labelUserPhoneNumberInstance);

            // finally add this to the table row
            searchViewTableLayout.addView(tr, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int userID = user.id;


                    Bundle bundle = new Bundle();
                    bundle.putInt("userID", userID);
                    profileView.setArguments(bundle);

                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.add(R.id.nav_host_fragment, profileView,
                            profileView.getTag());
                    transaction.commit();
                    transaction.addToBackStack(null);

//                    manager.beginTransaction().replace(R.id.nav_host_fragment, profileView,
//                            profileView.getTag()).commit();
                }
            });

            count++;
        }
    }

    private void populateUserHeadings() {
        TableRow userHeading = new TableRow(context);
        userHeading.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView userHeadingLabel = new TextView(context);
        userHeadingLabel.setTextSize(20);
        userHeadingLabel.setPadding(0, 4, 0, 0);
        userHeadingLabel.setText("Users");
        userHeading.addView(userHeadingLabel);

        searchViewTableLayout.addView(userHeading, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TableRow userHeadingBar = new TableRow(context);
//        tr_head.setId(10);
        userHeadingBar.setBackgroundColor(Color.BLACK);
        userHeadingBar.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView labelUserName = new TextView(context);
//        label_date.setId(20);
        labelUserName.setText("Name");
        labelUserName.setTextColor(Color.WHITE);
        labelUserName.setPadding(5, 5, 5, 5);
        userHeadingBar.addView(labelUserName);// add the column to the table row here

        TextView labelUserType = new TextView(context);
//        label_weight_kg.setId(21);// define id that must be unique
        labelUserType.setText("User Type"); // set the text for the header
        labelUserType.setTextColor(Color.WHITE); // set the color
        labelUserType.setPadding(5, 5, 5, 5); // set the padding (if required)
        userHeadingBar.addView(labelUserType); // add the column to the table row here

        TextView labelUserEmail = new TextView(context);
//        label_date.setId(20);
        labelUserEmail.setText("Email");
        labelUserEmail.setTextColor(Color.WHITE);
        labelUserEmail.setPadding(5, 5, 5, 5);
        userHeadingBar.addView(labelUserEmail);// add the column to the table row here

        TextView labelUserPhoneNumber = new TextView(context);
//        label_weight_kg.setId(21);// define id that must be unique
        labelUserPhoneNumber.setText("Ph No."); // set the text for the header
        labelUserPhoneNumber.setTextColor(Color.WHITE); // set the color
        labelUserPhoneNumber.setPadding(5, 5, 5, 5); // set the padding (if required)
        userHeadingBar.addView(labelUserPhoneNumber); // add the column to the table row here

        searchViewTableLayout.addView(userHeadingBar, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void populateJobListings(String search) {
        int count;
        count=0;

        List<Job> searchedJobs = jobDao.findJobsBySearch(search);

        for(final Job job:searchedJobs) {

            String text = job.jobTitle + "\nJob Description: "
                    + job.description + "\nEmployer ID: " + job.employerID;

            String title = job.jobTitle;
            String employer = userDao.findUserById(job.employerID).name;

            //            jobList.setText(text);
            // Create the table row
            TableRow tr = new TableRow(context);
            int textColor;
            if (count % 2 != 0) {
                tr.setBackgroundColor(Color.GRAY);
                textColor = Color.WHITE;
            } else {
                textColor = Color.BLACK;
            }
            tr.setId(100 + count);
            tr.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            //Create two columns to add as table data
            // Create a TextView to add date
            TextView labelJobTitleInstance = new TextView(context);
            labelJobTitleInstance.setId(200 + count);
            labelJobTitleInstance.setText(title);
            labelJobTitleInstance.setPadding(2, 0, 5, 0);
            labelJobTitleInstance.setTextColor(textColor);
            tr.addView(labelJobTitleInstance);
            TextView labelEmployerNameInstance = new TextView(context);
            labelEmployerNameInstance.setId(200 + count);
            labelEmployerNameInstance.setText(employer);
            labelEmployerNameInstance.setTextColor(textColor);
            tr.addView(labelEmployerNameInstance);

            // finally add this to the table row
            searchViewTableLayout.addView(tr, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int jobID = job.jobID;


                    Bundle bundle = new Bundle();
                    bundle.putInt("jobID", jobID);
                    jobView.setArguments(bundle);

                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.add(R.id.nav_host_fragment, jobView,
                            jobView.getTag());
                    transaction.commit();
                    transaction.addToBackStack(null);

//                    manager.beginTransaction().replace(R.id.nav_host_fragment, jobView,
//                            jobView.getTag()).commit();
                }
            });

            count++;
        }
    }

    private void populateJobHeadings() {
        TableRow jobHeading = new TableRow(context);
        jobHeading.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView jobHeadingLabel = new TextView(context);
        jobHeadingLabel.setTextSize(20);
        jobHeadingLabel.setPadding(0, 4, 0, 0);
        jobHeadingLabel.setText("Jobs");
        jobHeading.addView(jobHeadingLabel);

        searchViewTableLayout.addView(jobHeading, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TableRow jobHeadingBar = new TableRow(context);
//        tr_head.setId(10);
        jobHeadingBar.setBackgroundColor(Color.BLACK);
        jobHeadingBar.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView labelJobTitle = new TextView(context);
//        label_date.setId(20);
        labelJobTitle.setText("Job Title");
        labelJobTitle.setTextColor(Color.WHITE);
        labelJobTitle.setPadding(5, 5, 5, 5);
        jobHeadingBar.addView(labelJobTitle);// add the column to the table row here

        TextView labelEmployerName = new TextView(context);
//        label_weight_kg.setId(21);// define id that must be unique
        labelEmployerName.setText("Employer"); // set the text for the header
        labelEmployerName.setTextColor(Color.WHITE); // set the color
        labelEmployerName.setPadding(5, 5, 5, 5); // set the padding (if required)
        jobHeadingBar.addView(labelEmployerName); // add the column to the table row here

        searchViewTableLayout.addView(jobHeadingBar, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}