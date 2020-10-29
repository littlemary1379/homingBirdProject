package com.mary.homingbird.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mary.homingbird.R;
import com.mary.homingbird.main.MainActivity;
import com.mary.homingbird.util.ProgressBarUtil;

public class FragmentLoginEmail extends Fragment {

    private static final String TAG = "FragmentLoginEmail";

    private View view;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordCheck;
    private TextView textViewJoinButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    public static FragmentLoginEmail newInstance() {
        FragmentLoginEmail fragmentPostbox = new FragmentLoginEmail();
        return fragmentPostbox;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_email, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        findView();
        setListener();
        return view;
    }

    private void findView() {
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextPasswordCheck = view.findViewById(R.id.editTextPasswordCheck);
        textViewJoinButton = view.findViewById(R.id.textViewJoinButton);
        progressBar=view.findViewById(R.id.progressBar);
    }

    private void setListener() {
        textViewJoinButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if(email.equals("")||password.equals("")){
                Toast.makeText(getContext(), "이메일과 비밀번호를 비울 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            joinFirebase(email, password);
        });

        editTextPasswordCheck.setOnEditorActionListener((v, actionId, event) -> {
            String password = editTextPassword.getText().toString().trim();
            String passwordCheck = editTextPasswordCheck.getText().toString().trim();
            if (!password.equals(passwordCheck)) {
                Toast.makeText(getContext(), "비밀번호를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
                editTextPasswordCheck.setText("");

                return true;
            }

            return false;
        });

    }

    private void joinFirebase(String email, String password) {
        showProgressBar();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: 성공");
                        hideProgressBar();
                        //FirebaseUser user = firebaseAuth.getCurrentUser();
                        getActivity().finish();
                    } else {
                        Log.d(TAG, "onComplete: 실패");
                        hideProgressBar();
                    }
                });
    }

    private void showProgressBar(){
        ProgressBarUtil progressBarUtil = new ProgressBarUtil(progressBar);
        progressBarUtil.setProgressBar();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        ProgressBarUtil progressBarUtil = new ProgressBarUtil(progressBar);
        progressBarUtil.setProgressBar();
        progressBar.setVisibility(View.GONE);
    }
}
