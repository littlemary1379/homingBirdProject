package com.mary.homingbird.writeMessage.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mary.homingbird.R;
import com.mary.homingbird.bean.MemberBean;
import com.mary.homingbird.util.DlogUtil;

import java.util.ArrayList;

public class FragmentWriteMessageSecond extends Fragment {

    private static final String TAG = "FragmentWriteMessageFir";

    private ArrayList<String> exampleArray = new ArrayList<>();

    private View view;

    private Spinner spinner;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    public static FragmentWriteMessageSecond newInstance() {
        FragmentWriteMessageSecond fragmentWriteMessageFirst = new FragmentWriteMessageSecond();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_second, container, false);

        findView();

        return view;
    }

    private void findView() {

    }


}
