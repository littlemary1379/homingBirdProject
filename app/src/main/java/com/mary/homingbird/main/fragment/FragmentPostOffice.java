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

import com.mary.homingbird.R;
import com.mary.homingbird.login.LoginActivity;

public class FragmentPostOffice extends Fragment {

    private static final String TAG = "FragmentPostOffice";

    private View view;

    private TextView textViewWriteButton;

    public static FragmentPostOffice newInstance(){
        FragmentPostOffice fragmentPostOffice = new FragmentPostOffice();
        return fragmentPostOffice;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_postoffice,container,false);
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
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
