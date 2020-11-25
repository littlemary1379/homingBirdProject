package com.mary.homingbird.main.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mary.homingbird.R;
import com.mary.homingbird.util.DlogUtil;

public class FragmentPostbox extends Fragment {

    private static final String TAG = "FragmentPostbox";

    private View view;

    private ImageView imageViewPostbox;

    private Animation animation;

    public static FragmentPostbox newInstance(){
        FragmentPostbox fragmentPostbox = new FragmentPostbox();
        return fragmentPostbox;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_postbox,container,false);

        findView();
        updateView();
        setListener();

        return view;
    }

    private void findView(){
        imageViewPostbox = view.findViewById(R.id.imageViewPostBox);
    }

    private void updateView(){


    }


    private void setListener(){
        imageViewPostbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlogUtil.d(TAG, "이미지뷰 클릭 - 애니메이션 시작");
                animation = AnimationUtils.loadAnimation(getContext(), R.anim.mailbox_shake);
                imageViewPostbox.setAnimation(animation);
                imageViewPostbox.startAnimation(animation);
            }
        });
    }
}
