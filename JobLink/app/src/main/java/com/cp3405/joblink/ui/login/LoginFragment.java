package com.cp3405.joblink.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cp3405.joblink.MainActivity;
import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
        final HomeFragment home = new HomeFragment();
        final FragmentManager manager = getFragmentManager();
        final EditText username = root.findViewById(R.id.text_login_name_entry);
        Button logInButton = root.findViewById(R.id.button_login);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().toLowerCase().equals("employer")){
                    //Check database with credentials
                    Snackbar.make(view, "Logged in as an employer", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    manager.beginTransaction().replace(R.id.nav_host_fragment, home,
                            home.getTag()).commit();
                }
                else {
                    Snackbar.make(view, "Incorrect username or password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });




        return root;


    }





}