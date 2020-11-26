package com.mary.homingbird.main.fragment;

import android.os.Bundle;
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
    private ImageView imageViewMail;
    private ImageView imageViewHighlight1;
    private ImageView imageViewHighlight2;
    private ImageView imageViewHighlight3;

    private LinearLayout linearLayoutMail;

    private int i = 0;

    private Animation.AnimationListener mailBoxListener;
    private Animation.AnimationListener mailListener;

    Animation highlightAnimation;
    Animation highlightAnimation2;
    Animation highlightAnimation3;


    public static FragmentPostbox newInstance() {
        FragmentPostbox fragmentPostbox = new FragmentPostbox();
        return fragmentPostbox;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_postbox, container, false);

        findView();
        updateView();
        setListener();
        setAnimationListener();
        setAnimation();

        return view;
    }

    private void findView() {
        imageViewPostbox = view.findViewById(R.id.imageViewPostBox);
        imageViewMail = view.findViewById(R.id.imageViewMail);
        linearLayoutMail = view.findViewById(R.id.linearLayoutMail);
        imageViewHighlight1 = view.findViewById(R.id.imageViewHighLight1);
        imageViewHighlight2 = view.findViewById(R.id.imageViewHighLight2);
        imageViewHighlight3 = view.findViewById(R.id.imageViewHighLight3);
    }

    private void updateView() {


    }


    private void setListener() {
        imageViewPostbox.setOnClickListener(v -> {
            imageViewPostbox.setClickable(false);
            DlogUtil.d(TAG, "이미지뷰 클릭 - 애니메이션 시작");

            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.mailbox_shake);
            animation.setAnimationListener(mailBoxListener);
            imageViewPostbox.setAnimation(animation);
            imageViewPostbox.startAnimation(animation);
        });

    }

    private void setAnimationListener(){

        mailBoxListener = new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                i++;
                DlogUtil.d(TAG, "애니메이션 끝남 : " + i);
                if (i > 1) {
                    imageViewMail.setVisibility(View.VISIBLE);
                    mailLoadAction();
                } else {
                    imageViewPostbox.setClickable(true);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        };

        mailListener = new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageViewHighlight1.setVisibility(View.VISIBLE);
                imageViewHighlight2.setVisibility(View.VISIBLE);
                imageViewHighlight3.setVisibility(View.VISIBLE);
                DlogUtil.d(TAG, "메일리스너 끝");
                highlightAction();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        };


    }

    private void mailLoadAction(){
        Animation mailAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.mail_jumpimg);
        mailAnimation.setAnimationListener(mailListener);
        linearLayoutMail.setAnimation(mailAnimation);
        linearLayoutMail.startAnimation(mailAnimation);
    }

    void setAnimation(){

        imageViewHighlight1.setVisibility(View.INVISIBLE);
        imageViewHighlight2.setVisibility(View.INVISIBLE);
        imageViewHighlight3.setVisibility(View.INVISIBLE);

        Animation highlightAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.highlight_fade);
        imageViewHighlight1.setAnimation(highlightAnimation);

        Animation highlightAnimation2 = AnimationUtils.loadAnimation(getContext(), R.anim.highlight_fade2);
        imageViewHighlight2.setAnimation(highlightAnimation2);


        Animation highlightAnimation3 = AnimationUtils.loadAnimation(getContext(), R.anim.highlight_fade3);
        imageViewHighlight3.setAnimation(highlightAnimation3);
    }

    private void highlightAction(){

        imageViewHighlight1.startAnimation(highlightAnimation);
        imageViewHighlight2.startAnimation(highlightAnimation2);
        imageViewHighlight3.startAnimation(highlightAnimation3);

    }

}
