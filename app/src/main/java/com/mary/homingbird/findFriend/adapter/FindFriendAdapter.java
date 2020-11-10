package com.mary.homingbird.findFriend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mary.homingbird.R;
import com.mary.homingbird.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class FindFriendAdapter extends RecyclerView.Adapter {

    private List<UserBean> userBeans=new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_find_friend_item, parent, false);
        return new FindFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FindFriendViewHolder findFriendViewHolder = (FindFriendViewHolder) holder;
        findFriendViewHolder.userBean = userBeans.get(position);
        findFriendViewHolder.updateView();

    }

    public void setList(List<UserBean> userBeans){
        this.userBeans.clear();
        this.userBeans.addAll(userBeans);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userBeans.size();
    }
}
