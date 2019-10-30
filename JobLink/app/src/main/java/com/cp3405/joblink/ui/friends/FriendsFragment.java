package com.cp3405.joblink.ui.friends;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3405.joblink.R;
import com.cp3405.joblink.ui.database.JobLinkRoomDatabase;
import com.cp3405.joblink.ui.database.UserDao;

import java.util.List;

public class FriendsFragment extends Fragment {
    private RecyclerView friends;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserDao userDao;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container, false);

        friends = root.findViewById(R.id.friends_recycler);
        friends.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new FriendsAdapter(root.getContext(), getFriends());
        friends.setAdapter(adapter);
        friends.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
        
        return root;
    }

    private List<String> getFriends() {
        userDao = JobLinkRoomDatabase.getDatabase(context).userDao();
        return userDao.getUserType("Friend");
    }
}
