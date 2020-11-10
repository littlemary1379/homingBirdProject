package com.mary.homingbird.util;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mary.homingbird.bean.MemberBean;

import java.util.HashMap;

public class SendFriendUtil {

    private static final String TAG = "SendFriendUtil";

    private static FirebaseFirestore db;
    private static FirebaseAuth firebaseAuth;

    private static void initGoogleLogin() {

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    public static void sendFriend(String email, String code){
        initGoogleLogin();


        // 친구에게 보내는 요청 데이터베이스(내 데이터베이스에 남게 된다)
        HashMap<String, Object> myHashMap = new HashMap<>();
        myHashMap.put("email", email);
        myHashMap.put("code", code);
        myHashMap.put("state", "hold");
        DlogUtil.d(TAG, "email : "+ email);
        db.collection("/user/"+firebaseAuth.getCurrentUser().getEmail()+"/friend").document(email)
                .set(myHashMap)
                .addOnSuccessListener(documentReference -> DlogUtil.d(TAG, "성공"))
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    DlogUtil.d(TAG, "실패");
                });

        //남의 데이터베이스에 내가 친구요청을 했다는 사실을 남기는 데이터베이스
        HashMap<String, Object> friendHashMap = new HashMap<>();
        friendHashMap.put("email", MemberBean.getInstance().email);
        friendHashMap.put("code", MemberBean.getInstance().code);
        friendHashMap.put("state", "hold");
        DlogUtil.d(TAG, "MemberBean.getInstance.email : " + MemberBean.getInstance().email);
        db.collection("/user/"+email+"/friend").document(MemberBean.getInstance().email)
                .set(friendHashMap)
                .addOnSuccessListener(documentReference -> DlogUtil.d(TAG, "성공"))
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    DlogUtil.d(TAG, "실패");
                });
    }
}
