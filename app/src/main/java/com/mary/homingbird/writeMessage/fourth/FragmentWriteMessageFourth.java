package com.mary.homingbird.writeMessage.fourth;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mary.homingbird.R;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.util.SharedPreferenceUtil;

public class FragmentWriteMessageFourth extends Fragment {

    private static final String TAG = "FragmentWriteMessageFou";

    private View view;

    private TextView textViewFromName;
    private TextView textViewContent;
    private TextView textViewToName;
    private TextView textViewNext;

    private LinearLayout linearLayout;

    public static FragmentWriteMessageFourth newInstance() {
        FragmentWriteMessageFourth fragmentWriteMessageFirst = new FragmentWriteMessageFourth();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_fourth, container, false);

        findView();
        updateView();
        setListener();

        return view;
    }

    private void findView() {
        textViewToName = view.findViewById(R.id.textViewToName);
        textViewContent = view.findViewById(R.id.textViewContent);
        textViewFromName = view.findViewById(R.id.textViewFromName);
        textViewNext = view.findViewById(R.id.textViewNext);
        linearLayout = view.findViewById(R.id.linearLayout);
    }

    private void updateView() {

        Point point = new Point();

        Display windowMetrics = getContext().getDisplay();
        windowMetrics.getRealSize(point);
        int width = (int) (point.x*0.8);

        linearLayout.getLayoutParams().width=width;
        linearLayout.setLayoutParams(linearLayout.getLayoutParams());
        linearLayout.requestLayout();

        textViewToName.setText("받는 사람 : " + SharedPreferenceUtil.getStringSharedPreference(getContext(), "ToName"));
        textViewFromName.setText("보내는 사람 : " + SharedPreferenceUtil.getStringSharedPreference(getContext(), "FromName"));
        textViewContent.setText("보내는 사람 : " + SharedPreferenceUtil.getStringSharedPreference(getContext(), "Content"));
    }


    private void setListener() {
        textViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlogUtil.d(TAG, "야옹");
            }
        });

    }
}
