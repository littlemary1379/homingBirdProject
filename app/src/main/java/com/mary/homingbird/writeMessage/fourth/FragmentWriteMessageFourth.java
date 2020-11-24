package com.mary.homingbird.writeMessage.fourth;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mary.homingbird.R;
import com.mary.homingbird.main.MainActivity;
import com.mary.homingbird.util.ActivityUtil;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.util.SharedPreferenceUtil;

import java.util.HashMap;

public class FragmentWriteMessageFourth extends Fragment {

    private static final String TAG = "FragmentWriteMessageFou";

    private View view;

    private TextView textViewFromName;
    private TextView textViewContent;
    private TextView textViewToName;
    private TextView textViewNext;

    private LinearLayout linearLayout;

    private String toName;
    private String fromName;
    private String content;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    public static FragmentWriteMessageFourth newInstance() {
        FragmentWriteMessageFourth fragmentWriteMessageFirst = new FragmentWriteMessageFourth();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_fourth, container, false);

        findView();
        getSharedPrefer();
        updateView();
        setListener();
        initGoogle();

        return view;
    }

    private void getSharedPrefer() {
        toName = SharedPreferenceUtil.getStringSharedPreference(getContext(), "ToName");
        fromName = SharedPreferenceUtil.getStringSharedPreference(getContext(), "FromName");
        content = SharedPreferenceUtil.getStringSharedPreference(getContext(), "Content");
    }

    private void findView() {
        textViewToName = view.findViewById(R.id.textViewToName);
        textViewContent = view.findViewById(R.id.textViewContent);
        textViewFromName = view.findViewById(R.id.textViewFromName);
        textViewNext = view.findViewById(R.id.textViewNext);
        linearLayout = view.findViewById(R.id.linearLayout);
    }

    private void updateView() {

        Point point = new Point();

        Display windowMetrics = getContext().getDisplay();
        windowMetrics.getRealSize(point);
        int width = (int) (point.x * 0.8);

        linearLayout.getLayoutParams().width = width;
        linearLayout.setLayoutParams(linearLayout.getLayoutParams());
        linearLayout.requestLayout();

        textViewToName.setText("받는 사람 : " + toName);
        textViewFromName.setText("보내는 사람 : " + fromName);
        textViewContent.setText("내용 : " + content);
    }

    private void initGoogle() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    private void setListener() {
        textViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlogUtil.d(TAG, "야옹");
                sendMessage();
                storeMessage();

            }
        });

    }

    private void sendMessage() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("to", toName);
        hashMap.put("toName", firebaseAuth.getCurrentUser().getEmail());
        hashMap.put("fromName", fromName);
        hashMap.put("content", content);
        hashMap.put("state", "읽지 않음");


        db.collection("/user/" + toName + "/mailList")
                .add(hashMap)
                .addOnSuccessListener(documentReference -> {
                    DlogUtil.d(TAG, "성공");
                    ActivityUtil.startActivityWithFinish(getContext(), MainActivity.class);
                })
                .addOnFailureListener(e -> DlogUtil.d(TAG, "실패"));
    }

    private void storeMessage() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("to", toName);
        hashMap.put("toName", firebaseAuth.getCurrentUser().getEmail());
        hashMap.put("fromName", fromName);
        hashMap.put("content", content);
        hashMap.put("state", "발송 완료");


        db.collection("/user/" + firebaseAuth.getCurrentUser().getEmail() + "/storeMail")
                .add(hashMap)
                .addOnSuccessListener(documentReference -> DlogUtil.d(TAG, "성공"))
                .addOnFailureListener(e -> DlogUtil.d(TAG, "실패"));
    }
}
