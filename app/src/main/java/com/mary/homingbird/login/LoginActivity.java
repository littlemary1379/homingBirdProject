package com.mary.homingbird.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.homingbird.R;
import com.mary.homingbird.login.fragment.FragmentJoinEmail;
import com.mary.homingbird.login.fragment.FragmentLoginEmail;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.util.LoginUtil;
import com.mary.homingbird.util.ProgressBarUtil;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private TextView textViewJoinEmailButton;
    private FrameLayout frameLayoutLoginFragment;
    private SignInButton googleSignInButton;
    private ProgressBar progressBar;
    private TextView textViewLoginEmailButton;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();
        setListener();
        initGoogleLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }

    private void findView() {
        textViewJoinEmailButton = findViewById(R.id.textViewJoinEmailButton);
        frameLayoutLoginFragment = findViewById(R.id.frameLayoutLoginFragment);
        googleSignInButton = findViewById(R.id.googleSignButton);
        progressBar = findViewById(R.id.progressBar);
        textViewLoginEmailButton = findViewById(R.id.textViewLoginEmailButton);
    }

    private void setListener() {
        textViewJoinEmailButton.setOnClickListener(v -> {
            DlogUtil.d(TAG, "textViewJoinEmailButton 클릭");
            Fragment joinFragment = FragmentJoinEmail.newInstance();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_to_top, R.anim.enter_to_bottom).replace(R.id.frameLayoutLoginFragment, joinFragment).commit();
        });

        googleSignInButton.setOnClickListener(v -> {
            DlogUtil.d(TAG, "googleSignInButton 클릭");
            signIn();
        });

        textViewLoginEmailButton.setOnClickListener(v -> {
            DlogUtil.d(TAG, "textViewLoginEmailButton 클릭");
            Fragment loginFragment = FragmentLoginEmail.newInstance();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_to_top, R.anim.enter_to_bottom).replace(R.id.frameLayoutLoginFragment, loginFragment).commit();
        });
    }

    private void initGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    private void signIn() {

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthGoogle(String idToken) {
        showProgressBar();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                DlogUtil.d(TAG, "로그인 성공");
                //로그인 이력을 확인합니다.
                LoginUtil.checkLogin();
                LoginActivity.this.finish();

            } else {
                DlogUtil.d(TAG, "로그인 실패");
                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();

            }

            hideProgressBar();
        });

    }

    private void showProgressBar() {
        ProgressBarUtil progressBarUtil = new ProgressBarUtil(progressBar);
        progressBarUtil.setProgressBar();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        ProgressBarUtil progressBarUtil = new ProgressBarUtil(progressBar);
        progressBarUtil.setProgressBar();
        progressBar.setVisibility(View.GONE);
    }
}