package com.cp3405.joblink.ui.job;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.Job;
import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.User;
import com.cp3405.joblink.ui.database.UserDao;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class JobFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_job, container, false);


        JobDao jobDao = JobLinkRoomDatabase.getDatabase(getContext()).jobDao();
        List<Job> jobs = jobDao.getAllJobs();

        TextView name = root.findViewById(R.id.jobName);
        TextView descript = root.findViewById(R.id.jobDescription);
        TextView id = root.findViewById(R.id.jobEmployer);


        for(Job job:jobs) {

            System.out.println(job.jobTitle);

            String title = "Job Title: " + job.jobTitle + "\nJob ID: " + job.jobID;
            String description = "Job Description:\n" + job.description;
            String employer = "Posted By: Employer with ID  " + job.employerID;
            name.setText(title);
            descript.setText(description);
            id.setText(employer);
        }

        return root;
    }


}
