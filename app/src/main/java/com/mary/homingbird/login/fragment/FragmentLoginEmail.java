package com.mary.homingbird.login.fragment;

import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.mary.homingbird.R;
import com.mary.homingbird.util.LoginUtil;
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
            loginFirebase(email, password);
        });

    }

    private void loginFirebase(String email, String password) {
        showProgressBar();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: 성공");
                        hideProgressBar();
                        //FirebaseUser user = firebaseAuth.getCurrentUser();
                        LoginUtil.checkLogin();
                        getActivity().finish();
                    } else {
                        Log.d(TAG, "onComplete: 실패");
                        hideProgressBar();
                    }
                }).addOnFailureListener(e -> e.printStackTrace());
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
