package com.mary.homingbird.findFriend.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mary.homingbird.R;
import com.mary.homingbird.bean.UserBean;

public class FindFriendViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewUsername;
    private TextView textViewEmail;

    public UserBean userBean;

    public FindFriendViewHolder(@NonNull View itemView) {
        super(itemView);
        findView();
    }

    private void findView() {
        textViewEmail = itemView.findViewById(R.id.textViewEmail);
        textViewUsername = itemView.findViewById(R.id.textViewUsername);
    }

    public void updateView() {
        if (userBean.username != null) {
            textViewUsername.setText(userBean.username);
        } else {
            textViewUsername.setVisibility(View.GONE);
        }
        textViewEmail.setText(userBean.email);
    }
}
