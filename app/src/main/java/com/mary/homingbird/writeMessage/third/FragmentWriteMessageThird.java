package com.mary.homingbird.writeMessage.third;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mary.homingbird.R;
import com.mary.homingbird.util.SharedPreferenceUtil;

import java.util.ArrayList;

public class FragmentWriteMessageThird extends Fragment {

    private static final String TAG = "FragmentWriteMessageThi";

    private ArrayList<String> exampleArray = new ArrayList<>();

    private View view;

    private EditText editTextName;
    private TextView textViewNext;

    public static FragmentWriteMessageThird newInstance() {
        FragmentWriteMessageThird fragmentWriteMessageFirst = new FragmentWriteMessageThird();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_third, container, false);

        findView();
        setListener();

        return view;
    }

    private void findView() {
        textViewNext = view.findViewById(R.id.textViewNext);
    }

    private void setListener(){
        textViewNext.setOnClickListener(v -> {
            SharedPreferenceUtil.clearSharedPreference(getContext());
            //SharedPreferenceUtil.setSharedPreference(getContext(), "FromName", editTextName.getText().toString());
        });
    }
}
