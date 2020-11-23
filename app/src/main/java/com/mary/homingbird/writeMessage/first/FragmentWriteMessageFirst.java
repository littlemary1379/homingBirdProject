package com.mary.homingbird.writeMessage.first;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.homingbird.R;
import com.mary.homingbird.bean.MemberBean;
import com.mary.homingbird.util.ActivityUtil;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.writeMessage.second.FragmentWriteMessageSecond;

import java.util.ArrayList;

public class FragmentWriteMessageFirst extends Fragment {

    private static final String TAG = "FragmentWriteMessageFir";

    private ArrayList<String> exampleArray = new ArrayList<>();

    private View view;

    private Spinner spinner;
    private TextView textViewNext;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    public static FragmentWriteMessageFirst newInstance() {
        FragmentWriteMessageFirst fragmentWriteMessageFirst = new FragmentWriteMessageFirst();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_first, container, false);

        initGoogle();
        findView();
        initData();
        setListener();

        return view;
    }

    private void initGoogle() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void initData() {

        DlogUtil.d(TAG, "/user/" + MemberBean.getInstance().email + "/friend");

        db.collection("/user/" + MemberBean.getInstance().email + "/friend")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            DlogUtil.d(TAG, documentSnapshot.getData().get("email"));
                            exampleArray.add((String) documentSnapshot.getData().get("email"));
                        }
                        DlogUtil.d(TAG, exampleArray);
                        initSpinner();
                    }
                })
                .addOnFailureListener(e -> e.printStackTrace());
    }

    private void findView() {

        spinner = view.findViewById(R.id.spinner);
        textViewNext = view.findViewById(R.id.textViewNext);
    }

    private void setListener(){
        textViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlogUtil.d(TAG, "하");
                ActivityUtil.replaceFragment(getActivity(), R.id.frameLayoutContainer, new FragmentWriteMessageSecond());
            }
        });
    }

    private void initSpinner() {
        spinner.setVisibility(View.VISIBLE);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, exampleArray);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }
}
