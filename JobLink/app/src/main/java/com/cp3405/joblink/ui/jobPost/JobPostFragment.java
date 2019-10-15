package com.cp3405.joblink.ui.jobPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.home.HomeFragment;
import com.cp3405.joblink.ui.jobPost.JobPostViewModel;
import com.google.android.material.snackbar.Snackbar;

public class JobPostFragment extends Fragment {

    private JobPostViewModel jobPostViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jobPostViewModel =
                ViewModelProviders.of(this).get(JobPostViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jobpost, container, false);
        final TextView textView = root.findViewById(R.id.post_job);
        jobPostViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final HomeFragment home = new HomeFragment();
        final FragmentManager manager = getFragmentManager();

        Button postButton = root.findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Job Posted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                manager.beginTransaction().replace(R.id.nav_host_fragment, home,
                        home.getTag()).commit();
            }
        });
        return root;
    }
}