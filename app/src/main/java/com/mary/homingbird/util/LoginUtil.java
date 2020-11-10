package com.mary.homingbird.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;

public class LoginUtil {

    private static final String TAG = "LoginUtil";

    private static FirebaseFirestore db;
    private static FirebaseAuth firebaseAuth;

    private static void initGoogleLogin() {

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }


    public static void checkLogin() {

        DlogUtil.d(TAG, "checkLogin");
        initGoogleLogin();
        HashMap<Object, Object> hashMap = new HashMap<>();
        db.collection("user")
                .whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DlogUtil.d(TAG, "성공 : 여기로 오나?");
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            hashMap.put("code", documentSnapshot.getData().get("code"));
                        }

                        if (hashMap.isEmpty()) {
                            //코드를 생성하고, 코드가 있는지 재확인한다.
                            makeUserCode();
                        }

                    } else {
                        DlogUtil.d(TAG, "실패 : 여기로 오나?");
                    }
                })
                .addOnFailureListener(e -> {
                    DlogUtil.d(TAG, "여기니..?");
                    e.printStackTrace();
                });

    }

    private static void giveUserCode(int code) {
        DlogUtil.d(TAG, "giveUserCode");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", firebaseAuth.getCurrentUser().getEmail());
        hashMap.put("username", firebaseAuth.getCurrentUser().getDisplayName());
        hashMap.put("code", String.valueOf(code));
        db.collection("user").document(firebaseAuth.getCurrentUser().getEmail())
                .set(hashMap)
                .addOnSuccessListener(aVoid -> DlogUtil.d(TAG, "성공 : " + hashMap.get("code")))
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    DlogUtil.d(TAG, "실패");
                });
    }

    private static void makeUserCode() {
        DlogUtil.d(TAG, "makeUserCode");

        HashMap<String, Object> hashMap = new HashMap<>();
        int code = (int) (Math.random() * 10000000);
        db.collection("user")
                .whereEqualTo("code", code)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            DlogUtil.d(TAG, documentSnapshot.getData());
                            hashMap.put("code", documentSnapshot.getData().get("code"));
                        }

                        if (hashMap.isEmpty()) {
                            giveUserCode(code);
                        } else {
                            makeUserCode();
                        }

                    } else {
                        DlogUtil.d(TAG, "실패");
                        makeUserCode();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    DlogUtil.d(TAG, "실패");
                    makeUserCode();
                });

    }


}
