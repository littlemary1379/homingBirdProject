package com.mary.homingbird.main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.homingbird.R;
import com.mary.homingbird.bean.MailBean;
import com.mary.homingbird.findFriend.FindFriendActivity;
import com.mary.homingbird.main.adapter.MainFragmentAdapter;
import com.mary.homingbird.main.fragment.FragmentPostOffice;
import com.mary.homingbird.main.fragment.FragmentPostbox;
import com.mary.homingbird.main.fragment.MainZoomOutPageTransfomer;
import com.mary.homingbird.util.ActivityUtil;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.util.LoginUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private MainFragmentAdapter mainFragmentAdapter;

    private LinearLayout linearLayoutPostBox;
    private LinearLayout linearLayoutPostOffice;

    private ConstraintLayout constraintLayoutContainer;

    private TextView textViewPostOffice;
    private TextView textViewPostBox;
    private ImageView imageViewPostOffice;
    private ImageView imageViewPostBox;
    private ImageView imageViewMenu;

    private View viewMenuHeader;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<MailBean> mailBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLogin();
        findView();
        initFragment();
        initNavigation();
        setListener();


    }


    private void checkLogin() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser==null){
            return;
        }else{
            checkMailList();
        }
    }

    private void findView() {
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);

        textViewPostBox = findViewById(R.id.textViewPostBox);
        textViewPostOffice = findViewById(R.id.textViewPostOffice);
        imageViewPostBox = findViewById(R.id.imageViewPostBox);
        imageViewPostOffice = findViewById(R.id.imageViewPostOffice);
        imageViewMenu = findViewById(R.id.imageViewMenu);

        linearLayoutPostBox = findViewById(R.id.linearLayoutPostBox);
        linearLayoutPostOffice = findViewById(R.id.linearLayoutPostOffice);

        constraintLayoutContainer = findViewById(R.id.constraintLayoutContainer);
        viewMenuHeader = navigationView.getHeaderView(0);

    }

    private void initFragment() {
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), 1);

        mainFragmentAdapter.addFragment(FragmentPostbox.newInstance());
        mainFragmentAdapter.addFragment(FragmentPostOffice.newInstance());

        viewPager.setAdapter(mainFragmentAdapter);
        viewPager.setPageTransformer(true, new MainZoomOutPageTransfomer());
    }

    private void initNavigation() {
        Menu menu = navigationView.getMenu();
        if (firebaseUser != null) {
            menu.findItem(R.id.menu_login).setVisible(false);
            menu.findItem(R.id.menu_logout).setVisible(true);
        } else {
            menu.findItem(R.id.menu_login).setVisible(true);
            menu.findItem(R.id.menu_logout).setVisible(false);
        }
    }

    private void checkMailList() {
        db.collection("/user/" + firebaseUser.getEmail() + "/mailList")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            mailBeans = new ArrayList<>();
                            mailBeans.add(documentSnapshot.toObject(MailBean.class));
                        }
                        if(mailBeans!=null){
                            //popUpMail();
                        }
                        DlogUtil.d(TAG, "성공 : " + mailBeans);
                    }
                }).addOnFailureListener(e -> DlogUtil.d(TAG, "실패"));
    }

    private void popUpMail(){
        for(MailBean mail : mailBeans){
            if(mail.state.equals("읽지 않음")){
                DlogUtil.d(TAG, "읽지 않은 것이 있음");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setTitle("")
                        .setMessage("읽지 않은 메세지가 있습니다.")
                        .setPositiveButton(R.string.main_popup_positive, (dialog, which) -> {
                            //todo
                            //메일함으로 이동시키기
                        })
                        .setNegativeButton(R.string.main_popup_negative, (dialog, which) -> {

                        });

                builder.show();
            }

        }
    }


    private void setListener() {

        imageViewMenu.setOnClickListener(v -> {
            DlogUtil.d(TAG, "imageViewMenu 클릭");
            drawerLayout.openDrawer(Gravity.LEFT);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            DlogUtil.d(TAG, "navigationView 클릭");

            if (item.getItemId() == R.id.menu_addFriend) {
                DlogUtil.d(TAG, "addFriend 클릭");
                ActivityUtil.startActivityWithoutFinish(MainActivity.this, FindFriendActivity.class);
                return true;
            } else if (item.getItemId() == R.id.menu_logout) {
                DlogUtil.d(TAG, "logout 클릭");
                callSignOut();

            }

            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);

                switch (position) {
                    case 0: {
                        textViewPostBox.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_a79c8e));
                        textViewPostOffice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_f1bbba));
                        imageViewPostBox.setImageResource(R.drawable.postbox_3);
                        imageViewPostOffice.setImageResource(R.drawable.postoffice_1);
                        break;
                    }

                    case 1: {
                        textViewPostBox.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_f1bbba));
                        textViewPostOffice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_a79c8e));
                        imageViewPostBox.setImageResource(R.drawable.postbox_1);
                        imageViewPostOffice.setImageResource(R.drawable.postoffice_3);
                        break;
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        linearLayoutPostBox.setOnClickListener(v -> {
//
//            Log.d(TAG, "setListener: linearLayoutPostBox");
//            textViewPostBox.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_a79c8e));
//            textViewPostOffice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_f1bbba));
//            imageViewPostBox.setImageResource(R.drawable.postbox_3);
//            imageViewPostOffice.setImageResource(R.drawable.postoffice_1);
//
//
//            getSupportFragmentManager().beginTransaction()
//                    .show()
//                    .commit();
//
//        });
//
//        linearLayoutPostOffice.setOnClickListener(v -> {
//
//            Log.d(TAG, "setListener: linearLayoutPostOffice");
//            textViewPostBox.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_f1bbba));
//            textViewPostOffice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_a79c8e));
//            imageViewPostBox.setImageResource(R.drawable.postbox_1);
//            imageViewPostOffice.setImageResource(R.drawable.postoffice_3);
//
//            getSupportFragmentManager().beginTransaction().show(mainFragmentAdapter.fragmentList.get(1))
//                   .commit();
//
//        });
    }

    private void callSignOut() {
        firebaseAuth.signOut();
    }

}