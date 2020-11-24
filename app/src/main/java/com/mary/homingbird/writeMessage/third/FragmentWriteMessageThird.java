package com.mary.homingbird.writeMessage.third;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mary.homingbird.R;
import com.mary.homingbird.util.ActivityUtil;
import com.mary.homingbird.util.SharedPreferenceUtil;
import com.mary.homingbird.writeMessage.fourth.FragmentWriteMessageFourth;
import com.mary.homingbird.writeMessage.second.FragmentWriteMessageSecond;

import java.util.ArrayList;

public class FragmentWriteMessageThird extends Fragment {

    private static final String TAG = "FragmentWriteMessageThi";

    private ArrayList<String> exampleArray = new ArrayList<>();

    private View view;

    private EditText editTextContent;
    private TextView textViewNext;
    private LinearLayout linearLayout;

    public static FragmentWriteMessageThird newInstance() {
        FragmentWriteMessageThird fragmentWriteMessageFirst = new FragmentWriteMessageThird();
        return fragmentWriteMessageFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_message_third, container, false);

        findView();
        updateView();
        setListener();

        return view;
    }

    private void findView() {
        editTextContent = view.findViewById(R.id.editTextContent);
        textViewNext = view.findViewById(R.id.textViewNext);
        linearLayout = view.findViewById(R.id.linearLayout);
    }

    private void updateView(){
        Point point = new Point();

        Display windowMetrics = getContext().getDisplay();
        windowMetrics.getRealSize(point);
        int width = (int) (point.x*0.7);

        linearLayout.getLayoutParams().width=width;
        linearLayout.setLayoutParams(linearLayout.getLayoutParams());
        linearLayout.requestLayout();
    }

    private void setListener(){
        textViewNext.setOnClickListener(v -> {
            SharedPreferenceUtil.setSharedPreference(getContext(), "Content", editTextContent.getText().toString());
            ActivityUtil.replaceFragment(getActivity(), R.id.frameLayoutContainer, new FragmentWriteMessageFourth());
        });
    }
}
