package com.mary.homingbird.findFriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.homingbird.R;
import com.mary.homingbird.util.DlogUtil;

import java.util.HashMap;

public class FindFriendActivity extends AppCompatActivity {

    private static final String TAG = "FindFriendActivity";

    private ImageView imageViewSearch;
    private EditText editTextSearch;

    private FirebaseFirestore db;

    private boolean isCode = false;
    private boolean isEmail = false;
    private boolean isUsername = false;
    private HashMap<String, Object> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        findView();
        initGoogle();
        setListener();
    }

    private void initGoogle() {
        db = FirebaseFirestore.getInstance();
    }

    private void findView() {
        imageViewSearch = findViewById(R.id.imageViewSearch);
        editTextSearch = findViewById(R.id.editTextSearch);
    }

    private void setListener() {
        imageViewSearch.setOnClickListener(v -> {
            String keyword = editTextSearch.getText().toString().trim();
            findFriend(keyword);
        });
    }

    private void findFriend(String keyword) {
        db.collection("user")
                .whereEqualTo("code", keyword)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            hashMap.put("email", documentSnapshot.getData().get("email"));
                            String username = (String) documentSnapshot.getData().get("username");
                            if(username == null){
                                hashMap.put("username", "설정된 닉네임이 없습니다.");
                            } else {
                                hashMap.put("username", documentSnapshot.getData().get("username"));
                            }
                            hashMap.put("code", documentSnapshot.getData().get("code"));
                        }
                    }
                    DlogUtil.d(TAG, hashMap);
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
                            hashMap.put("email", documentSnapshot.getData().get("email"));
                            String username = (String) documentSnapshot.getData().get("username");
                            if(username == null){
                                hashMap.put("username", "설정된 닉네임이 없습니다.");
                            } else {
                                hashMap.put("username", documentSnapshot.getData().get("username"));
                            }
                            hashMap.put("code", documentSnapshot.getData().get("code"));
                        }
                    }
                    DlogUtil.d(TAG, hashMap);
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
                            hashMap.put("email", documentSnapshot.getData().get("email"));
                            String username = (String) documentSnapshot.getData().get("username");
                            if(username == null){
                                hashMap.put("username", "설정된 닉네임이 없습니다.");
                            } else {
                                hashMap.put("username", documentSnapshot.getData().get("username"));
                            }
                            hashMap.put("code", documentSnapshot.getData().get("code"));
                        }
                    }
                    DlogUtil.d(TAG, hashMap);
                    isUsername = true;
                    setRecycler();
                })
                .addOnFailureListener(e -> e.printStackTrace());
    }


    private void setRecycler(){
        if(isCode && isEmail && isUsername){
            DlogUtil.d(TAG, "으앙 : " + hashMap);
        }
    }

}