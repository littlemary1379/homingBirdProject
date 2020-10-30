package com.mary.homingbird.writeMessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.mary.homingbird.R;
import com.mary.homingbird.writeMessage.first.FragmentWriteMessageFirst;

public class WriteActivity extends AppCompatActivity {

    private FrameLayout frameLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        findView();
        showFirstFragment();
    }

    private void findView(){
        frameLayoutContainer = findViewById(R.id.frameLayoutContainer);
    }

    private void showFirstFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameLayoutContainer, FragmentWriteMessageFirst.newInstance()).commit();
    }

}