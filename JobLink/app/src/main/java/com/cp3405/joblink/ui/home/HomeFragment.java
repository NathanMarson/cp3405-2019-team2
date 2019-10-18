package com.cp3405.joblink.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.Job;
import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.User;
import com.cp3405.joblink.ui.gallery.GalleryFragment;
import com.cp3405.joblink.ui.jobPost.JobPostFragment;
import com.cp3405.joblink.ui.jobPost.JobPostViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public boolean isEmployer; //Will be pulled from the database


    public void first() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        isEmployer = true;

        Button addJob = root.findViewById(R.id.jobButton);
        if (isEmployer){
            addJob.setVisibility(View.VISIBLE); //Bring up the user type page
        }
        else {
            addJob.setVisibility(View.INVISIBLE);
        }

        final JobPostFragment post = new JobPostFragment();
        final FragmentManager manager = getFragmentManager();

        TextView jobList = root.findViewById(R.id.home_job_list_example);
        JobDao jobDao = JobLinkRoomDatabase.getDatabase(getContext()).jobDao();
        List<Job> jobs = jobDao.getAllJobs();



        for(Job job:jobs) {

            System.out.println(job.jobTitle);

            String text = job.jobTitle + "\nJob Description: "
                    + job.description + "\nEmployer ID: " + job.employerID;
            jobList.setText(text);
        }





        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.beginTransaction().replace(R.id.nav_host_fragment, post,
                        post.getTag()).commit();
            }
        });

        return root;
    }
}