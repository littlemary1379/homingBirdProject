package com.mary.homingbird.findFriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.mary.homingbird.R;
import com.mary.homingbird.bean.UserBean;
import com.mary.homingbird.findFriend.adapter.FindFriendAdapter;
import com.mary.homingbird.util.DlogUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class FindFriendActivity extends AppCompatActivity {

    private static final String TAG = "FindFriendActivity";

    private ImageView imageViewSearch;
    private EditText editTextSearch;
    private RecyclerView recyclerView;

    private FirebaseFirestore db;

    private FindFriendAdapter findFriendAdapter;

    private boolean isCode;
    private boolean isEmail;
    private boolean isUsername;
    private ArrayList<UserBean> userBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        findView();
        initGoogle();
        initAdapter();
        setListener();
    }

    private void initGoogle() {
        db = FirebaseFirestore.getInstance();
    }

    private void findView() {
        imageViewSearch = findViewById(R.id.imageViewSearch);
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initAdapter(){
        findFriendAdapter = new FindFriendAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(findFriendAdapter);
    }

    private void setListener() {
        imageViewSearch.setOnClickListener(v -> {
            String keyword = editTextSearch.getText().toString().trim();
            findFriend(keyword);
        });
    }

    private void findFriend(String keyword) {

        userBeans = new ArrayList<>();

        isCode = false;
        isEmail = false;
        isUsername = false;

        db.collection("user")
                .whereEqualTo("code", keyword)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            userBeans.add(documentSnapshot.toObject(UserBean.class));
                        }
                    }
                    isCode = true;
                    setRecycler();
                })
                .addOnFailureListener(e -> e.printStackTrace());

        db.collection("user")
                .whereEqualTo("email", keyword)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            userBeans.add(documentSnapshot.toObject(UserBean.class));
                        }
                    }
                    isEmail = true;
                    setRecycler();
                })
                .addOnFailureListener(e -> e.printStackTrace());

        db.collection("user")
                .whereEqualTo("username", keyword)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            userBeans.add(documentSnapshot.toObject(UserBean.class));
                        }
                    }
                    isUsername = true;
                    setRecycler();
                })
                .addOnFailureListener(e -> e.
                        printStackTrace());
    }

    private void setRecycler(){
        recyclerView.setVisibility(View.VISIBLE);
        if(isCode && isEmail && isUsername){
            DlogUtil.d(TAG, "setRecycler");
            findFriendAdapter.setList(userBeans);
        }
    }

}