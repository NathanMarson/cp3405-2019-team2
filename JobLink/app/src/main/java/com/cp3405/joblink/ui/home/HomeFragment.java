package com.cp3405.joblink.ui.home;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.Job;
import com.cp3405.joblink.ui.database.JobDao;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.User;
import com.cp3405.joblink.ui.database.UserDao;
import com.cp3405.joblink.ui.gallery.GalleryFragment;
import com.cp3405.joblink.ui.job.JobFragment;
import com.cp3405.joblink.ui.jobPost.JobPostFragment;
import com.cp3405.joblink.ui.jobPost.JobPostViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public boolean isEmployer;

    private final Job[] selectedJob = new Job[1];


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



        Button addJob = root.findViewById(R.id.jobButton);


        final JobPostFragment post = new JobPostFragment();
        final JobFragment jobView = new JobFragment();

        final FragmentManager manager = getFragmentManager();

        final ListView list = root.findViewById(R.id.notification_list);

        final JobDao jobDao = JobLinkRoomDatabase.getDatabase(getContext()).jobDao();
        List<Job> jobs = jobDao.getAllJobs();

        UserDao userDao = JobLinkRoomDatabase.getDatabase(getContext()).userDao();
        User user = userDao.findUserByLogin();

        if (user.username.equals("Employer")){
            isEmployer = true;
        }else{
            isEmployer = false;
        }

        if (isEmployer){
            addJob.setVisibility(View.VISIBLE); //Bring up the user type page
        }
        else {
            addJob.setVisibility(View.INVISIBLE);
            addJob.getLayoutParams().height = 1;
        }





        if (user.username.equals("Student")){

            String jobRecommends = user.recommended_jobs;
            final String[] items = jobRecommends.split(",");

            ArrayList<String> recommend = new ArrayList<>();


            for(String s : items) {
                recommend.add("You have been recommended for a job:  " + s);
            }

            //for every item i in the items list:
            //new list entry at [i] = You have been recommended for a job with id + i


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, recommend);
            list.setAdapter(arrayAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedJob[0] = jobDao.findJobByTitle(items[position]);
                    System.out.println(selectedJob[0].jobTitle);

                    Bundle bundle = new Bundle();
                    bundle.putInt("jobID", selectedJob[0].jobID);
                    jobView.setArguments(bundle);

                    manager.beginTransaction().replace(R.id.nav_host_fragment, jobView,
                            jobView.getTag()).commit();
                }
            });



        }



        final ListView list2 = root.findViewById(R.id.job_list);
        ArrayList<String> jobsList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, jobsList);
        list2.setAdapter(arrayAdapter2);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedJob[0] = jobDao.findJobByTitle(list2.getItemAtPosition(position).toString());

                Bundle bundle = new Bundle();
                bundle.putInt("jobID", selectedJob[0].jobID);
                jobView.setArguments(bundle);

                manager.beginTransaction().replace(R.id.nav_host_fragment, jobView,
                        jobView.getTag()).commit();



            }
        });


        for(Job job:jobs) {

            jobsList.add(job.jobTitle);
        }




        //jobList.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //manager.beginTransaction().replace(R.id.nav_host_fragment, jobView,
                        //jobView.getTag()).commit();
            //}
        //});

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