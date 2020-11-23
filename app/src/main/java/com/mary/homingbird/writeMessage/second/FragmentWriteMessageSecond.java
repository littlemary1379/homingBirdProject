package com.mary.homingbird.writeMessage.second;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mary.homingbird.R;
import com.mary.homingbird.bean.MemberBean;
import com.mary.homingbird.util.ActivityUtil;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.util.SharedPreferenceUtil;
import com.mary.homingbird.writeMessage.third.FragmentWriteMessageThird;

import java.util.ArrayList;

public class FragmentWriteMessageSecond extends Fragment {

    private static final String TAG = "FragmentWriteMessageSec";

    private ArrayList<String> exampleArray = new ArrayList<>();

    private View view;

    private EditText editTextName;
    private TextView textViewNext;

    public static FragmentWriteMessageSecond newInstance() {
        FragmentWriteMessageSecond fragmentWriteMessageFirst = new FragmentWriteMessageSecond();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_second, container, false);

        findView();
        setListener();

        return view;
    }

    private void findView() {
        editTextName = view.findViewById(R.id.editTextName);
        textViewNext = view.findViewById(R.id.textViewNext);
    }

    private void setListener() {
        textViewNext.setOnClickListener(v -> {
            if (editTextName.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), "이름을 비울 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferenceUtil.clearSharedPreference(getContext());
            SharedPreferenceUtil.setSharedPreference(getContext(), "FromName", editTextName.getText().toString().trim());
            ActivityUtil.replaceFragment(getActivity(), R.id.frameLayoutContainer, new FragmentWriteMessageThird());
        });
    }
}
