package com.mary.homingbird.findFriend.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mary.homingbird.R;
import com.mary.homingbird.bean.MemberBean;
import com.mary.homingbird.bean.UserBean;
import com.mary.homingbird.util.DlogUtil;
import com.mary.homingbird.util.SendFriendUtil;

public class FindFriendViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "FindFriendViewHolder";

    private TextView textViewUsername;
    private TextView textViewEmail;
    private TextView textViewButton;

    public UserBean userBean;

    public FindFriendViewHolder(@NonNull View itemView) {
        super(itemView);
        findView();
        setListener();
    }

    private void findView() {
        textViewEmail = itemView.findViewById(R.id.textViewEmail);
        textViewUsername = itemView.findViewById(R.id.textViewUsername);
        textViewButton = itemView.findViewById(R.id.textViewButton);
    }

    public void updateView() {
        if (userBean.username != null) {
            textViewUsername.setText(userBean.username);
        } else {
            textViewUsername.setVisibility(View.GONE);
        }
        textViewEmail.setText(userBean.email);

        if(userBean.email.equals(MemberBean.getInstance().email)){
            textViewButton.setVisibility(View.GONE);
        } else {
            textViewButton.setVisibility(View.VISIBLE);
        }
    }

    public void setListener(){
        textViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlogUtil.d(TAG, "textViewButton 클릭");
                SendFriendUtil.sendFriend(userBean.email, userBean.code);
            }
        });
    }
}
