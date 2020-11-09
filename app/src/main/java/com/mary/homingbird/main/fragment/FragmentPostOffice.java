package com.mary.homingbird.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mary.homingbird.R;
import com.mary.homingbird.login.LoginActivity;
import com.mary.homingbird.util.ActivityUtil;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.writeMessage.WriteActivity;

public class FragmentPostOffice extends Fragment {

    private static final String TAG = "FragmentPostOffice";

    private View view;

    private TextView textViewWriteButton;

    private FirebaseAuth firebaseAuth;

    public static FragmentPostOffice newInstance(){
        FragmentPostOffice fragmentPostOffice = new FragmentPostOffice();
        return fragmentPostOffice;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_postoffice,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        findView();
        setListener();
        return view;
    }


    private void findView(){
        textViewWriteButton = view.findViewById(R.id.textViewWriteButton);
    }

    private void setListener(){
        textViewWriteButton.setOnClickListener(view -> {
            Log.d(TAG, "setListener: textViewWriteButton 클릭");

            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if(currentUser==null) {
                DlogUtil.d(TAG, "로그인 안 되어있음");
                ActivityUtil.startActivityWithoutFinish(getContext(), LoginActivity.class);
            }else{
                DlogUtil.d(TAG, "로그인 되어있음");
                ActivityUtil.startActivityWithoutFinish(getContext(), WriteActivity.class);
            }
        });
    }
}
