package com.mary.homingbird.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mary.homingbird.R;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.util.ProgressBarUtil;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private TextView textViewJoinEmailButton;
    private FrameLayout frameLayoutLoginFragment;
    private SignInButton googleSignInButton;
    private ProgressBar progressBar;

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
    }

    private void setListener() {
        textViewJoinEmailButton.setOnClickListener(v -> {
            DlogUtil.d(TAG, "textViewJoinEmailButton 클릭");
            Fragment loginFragment = FragmentLoginEmail.newInstance();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_to_top, R.anim.enter_to_bottom).replace(R.id.frameLayoutLoginFragment, loginFragment).commit();
        });

        googleSignInButton.setOnClickListener(v -> {
            DlogUtil.d(TAG, "googleSignInButton 클릭");
            signIn();
        });
    }

    private void initGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance();
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
                FirebaseUser user = firebaseAuth.getCurrentUser();
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