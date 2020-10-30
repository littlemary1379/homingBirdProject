package com.mary.homingbird.writeMessage.first;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mary.homingbird.R;

import java.util.ArrayList;

public class FragmentWriteMessageFirst extends Fragment {

    private ArrayList<String> exampleArray=new ArrayList<>();

    private View view;

    private Spinner spinner;

    private FirebaseFirestore db;

    public static FragmentWriteMessageFirst newInstance(){
        FragmentWriteMessageFirst fragmentWriteMessageFirst = new FragmentWriteMessageFirst();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_first,container,false);

        findView();
        initDummyData();
        initSpinner();
        initGoogleFireStore();

        return view;
    }

    private void initDummyData(){
        exampleArray.add("ex1");
        exampleArray.add("ex2");
        exampleArray.add("ex3");
    }

    private void findView(){
        spinner = view.findViewById(R.id.spinner);
    }

    private void initSpinner(){
        ArrayAdapter adapter=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,exampleArray);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void initGoogleFireStore(){
        db=FirebaseFirestore.getInstance();
    }
}
