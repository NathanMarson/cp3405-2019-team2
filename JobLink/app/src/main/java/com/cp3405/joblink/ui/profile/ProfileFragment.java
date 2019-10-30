package com.cp3405.joblink.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigator;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.User;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.UserDao;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment {
    private TextView name;
    private TextView email;
    private TextView phone;
    private Context context;
    private User user;
    private UserDao userDao;
    private Button saveChanges;
    private boolean isCurrentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        isCurrentUser = false;

        userDao = JobLinkRoomDatabase.getDatabase(context).userDao();

        Bundle bundle = getArguments();

        if (bundle != null) {
            int selectedUserID = bundle.getInt("userID");
            user = userDao.findUserById(selectedUserID);
        } else {
            user = userDao.findUserByLogin();
        }
//        if(user == null){
//            Snackbar.make(root, "Please log in", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//            manager.beginTransaction().replace(R.id.nav_host_fragment, login,
//                    login.getTag()).addToBackStack(null).commit();
//
//        }

        getUserDetails(root);

        saveChanges = root.findViewById(R.id.profile_save_changes);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.name = name.getText().toString();
                Log.i("database", user.name);
                user.email = email.getText().toString();
                Log.i("database", user.email);
                user.phoneNum = Integer.valueOf(phone.getText().toString());
                Log.i("database", String.valueOf(user.phoneNum));
                userDao.update(user);
                Snackbar.make(getView(), "Changes Saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
    }

    private void getUserDetails(View root) {
        name = root.findViewById(R.id.profile_name);
        name.setText(user.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEdit(name);
            }
        });

        email = root.findViewById(R.id.profile_email);
        email.setText(user.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEdit(email);
            }
        });

        phone = root.findViewById(R.id.profile_phone);
        phone.setText(String.valueOf(user.phoneNum));
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberEdit(phone);
            }
        });
    }

    private void textEdit(final TextView textView){
        //https://stackoverflow.com/a/10904665
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit");

        // Set up the input
        final EditText input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void numberEdit(final TextView textView){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit");

        // Set up the input
        final EditText input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        user.name = name.getText().toString();
        Log.i("database", user.name);
        user.email = email.getText().toString();
        Log.i("database", user.email);
        user.phoneNum = Integer.valueOf(phone.getText().toString());
        Log.i("database", String.valueOf(user.phoneNum));
        userDao.update(user);
    }
}
