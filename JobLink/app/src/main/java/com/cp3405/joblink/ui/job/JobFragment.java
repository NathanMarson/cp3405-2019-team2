package com.cp3405.joblink.ui.job;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import androidx.fragment.app.Fragment;

public class JobFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_job, container, false);

        Bundle bundle = getArguments();

        String selectedJobTitle = bundle.getString("Job Title");


        JobDao jobDao = JobLinkRoomDatabase.getDatabase(getContext()).jobDao();
        final List<Job> jobs = jobDao.getAllJobs();

        final Job currentJob = jobDao.findJobByTitle(selectedJobTitle);

        final TextView name = root.findViewById(R.id.jobName);
        final TextView id = root.findViewById(R.id.jobID);
        final TextView descriptTitle = root.findViewById(R.id.jobDescriptionTitle);
        final TextView descript = root.findViewById(R.id.jobDescription);
        final TextView jobId = root.findViewById(R.id.jobEmployer);

        final UserDao userDao = JobLinkRoomDatabase.getDatabase(getContext()).userDao();
        final User user = userDao.findUserByLogin();

        Button recommendButton = root.findViewById(R.id.recommendButton);
        final EditText searchUser = root.findViewById(R.id.searchUser);
        final Button searchButton = root.findViewById(R.id.searchButton);
        final TextView searchedUser = root.findViewById(R.id.searchedUser);

        final String title = currentJob.jobTitle;
        String jobID = "Job ID: " + currentJob.jobID;
        String descriptionTitle = "Job Description:";
        String description = currentJob.description;
        String employer = "Posted By: Employer with ID  " + currentJob.employerID;
        name.setText(title);
        id.setText(jobID);
        descriptTitle.setText(descriptionTitle);
        descript.setText(description);
        jobId.setText(employer);





        if (user.username.equals("Staff")){
            recommendButton.setVisibility(View.VISIBLE); //Bring up the user type page
        }
        else {
            recommendButton.setVisibility(View.INVISIBLE);
        }

        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchUser.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.VISIBLE);
            }
        });

        final String text = "No Users Found";









        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedUser.setVisibility(View.VISIBLE);
                final User selectedUser = userDao.findUserByUsername(searchUser.getText().toString());

                try{
                    String test = userDao.findUserByUsername(searchUser.getText().toString()).username;
                    searchedUser.setText(test);
                    searchedUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedUser.recommended_jobs = selectedUser.recommended_jobs + ((name.getText().toString()) + ",");
                            Log.i("database", selectedUser.recommended_jobs);
                            userDao.update(selectedUser);
                            System.out.println(selectedUser.recommended_jobs);
                            Snackbar.make(view, "You recommended " + selectedUser.username + " for this job.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                }catch (NullPointerException e){
                    searchedUser.setText(text);
                }

            }
        });




        return root;
    }


}
