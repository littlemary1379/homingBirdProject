package com.mary.homingbird.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mary.homingbird.R;

public class ActivityUtil {

    public static void startActivityWithoutFinish(Context context, Class<?> cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void replaceFragment(Activity activity, int id, Fragment fragment){
        FragmentActivity fragmentActivity = (FragmentActivity) activity;
        FragmentManager fragmentManager= fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_to_bottom, R.anim.exit_to_top);
        transaction.replace(id, fragment).commitAllowingStateLoss();
    }


}
