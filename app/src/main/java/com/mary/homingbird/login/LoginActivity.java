package com.mary.homingbird.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mary.homingbird.R;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewJoinEmailButton;
    private FrameLayout frameLayoutLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();
        setListener();
    }

    private void findView(){
        textViewJoinEmailButton = findViewById(R.id.textViewJoinEmailButton);
        frameLayoutLoginFragment = findViewById(R.id.frameLayoutLoginFragment);
    }

    private void setListener(){
        textViewJoinEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = FragmentLoginEmail.newInstance();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_to_top, R.anim.enter_to_bottom).replace(R.id.frameLayoutLoginFragment, loginFragment).commit();
            }
        });
    }
}