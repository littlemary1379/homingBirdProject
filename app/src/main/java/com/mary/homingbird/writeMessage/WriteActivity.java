package com.mary.homingbird.writeMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.mary.homingbird.R;

public class WriteActivity extends AppCompatActivity {

    private FrameLayout frameLayoutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        findView();
    }

    private void findView(){

    }
}