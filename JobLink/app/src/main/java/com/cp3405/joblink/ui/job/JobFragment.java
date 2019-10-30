package com.cp3405.joblink.ui.job;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import androidx.fragment.app.FragmentTransaction;

public class JobFragment extends Fragment {

    private TableLayout searchViewTableLayout;

    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_job, container, false);

        Bundle bundle = getArguments();

        int selectedJobID = bundle.getInt("jobID");


        JobDao jobDao = JobLinkRoomDatabase.getDatabase(context).jobDao();
        final List<Job> jobs = jobDao.getAllJobs();

        final Job currentJob = jobDao.findJobById(selectedJobID);

        final TextView jobName = root.findViewById(R.id.jobName);
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
        jobName.setText(title);
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

                searchViewTableLayout = root.findViewById(R.id.user_table);

                searchViewTableLayout.removeAllViews();

                String search = "%" + searchedUser.getText().toString() + "%";

                try{
                    int count = 0;

                    List<User> searchedUsers = userDao.findUsersBySearch(search);

                    for(final User user:searchedUsers) {

                        String name = user.name;
                        String userType = user.userType;
                        String email = user.email;
                        int phoneNum = user.phoneNum;

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
                            public void onClick(View view) {
                                user.recommended_jobs = user.recommended_jobs + ((jobName.getText().toString()) + ",");
                                Log.i("database", user.recommended_jobs);
                                userDao.update(user);
                                System.out.println(user.recommended_jobs);
                                Snackbar.make(view, "You recommended " + user.username + " for this job.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });

                        count++;
                    }

//                    String test = userDao.findUserByUsername(searchUser.getText().toString()).username;
//                    searchedUser.setText(test);
//                    searchedUser.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            selectedUser.recommended_jobs = selectedUser.recommended_jobs + ((jobName.getText().toString()) + ",");
//                            Log.i("database", selectedUser.recommended_jobs);
//                            userDao.update(selectedUser);
//                            System.out.println(selectedUser.recommended_jobs);
//                            Snackbar.make(view, "You recommended " + selectedUser.username + " for this job.", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
//                        }
//                    });
                }catch (NullPointerException e){
                    searchedUser.setText(text);
                }
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
