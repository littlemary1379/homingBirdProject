package com.mary.homingbird.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mary.homingbird.R;

public class FragmentPostbox extends Fragment {

    public static FragmentPostbox newInstance(){
        FragmentPostbox fragmentPostbox = new FragmentPostbox();
        return fragmentPostbox;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_postbox,container,false);
        return view;
    }
}
