package com.rikkei.tranning.chatapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.repositories.AllFriendRepositories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllFriendAdapter extends ListAdapter<User,AllFriendAdapter.ViewHolder> {
    Context context;
    public AllFriendAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
    }
    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return  oldItem.getUserName().equals(newItem.getUserName())
                    && oldItem.getUserEmail().equals(newItem.getUserEmail())
                    && oldItem.getUserImgUrl().equals(newItem.getUserImgUrl())
                    && oldItem.getUserPhone().equals(newItem.getUserPhone())
                    && oldItem.getUserDateOfBirth().equals(newItem.getUserDateOfBirth());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User user=getItem(position);
        if(user.getUserImgUrl().equals("default")){
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.cimgUser);
        }
        else{
            Glide.with(context).load(user.getUserImgUrl()).circleCrop()
                    .into(holder.cimgUser);
        }
        holder.txtSection.setText(user.getUserName().substring(0,1));
        holder.txtUserName.setText(user.getUserName());
        new AllFriendRepositories().searchFriendType(user, new AllFriendRepositories.typeFriend() {
            @Override
            public void typeFriendIsLoad(String s) {
                switch (s){
                    case "NoFriend":
                        holder.btnRequest.setText("Kết bạn");
                        break;
                    case "friend":
                        holder.btnRequest.setText("Bạn bè");
                        break;
                    case "sendRequest":
                        holder.btnRequest.setText("Hủy");
                        break;
                    case "friendRequest":
                        holder.btnRequest.setText("Đồng ý");
                        break;
                }
            }
        });
        if(position >0){
            int i=position-1;
            if (i<=this.getItemCount()&&user.getUserName().substring(0,1)
                    .equals(getNoteAt(i).getUserName().substring(0,1))){
                holder.txtSection.setVisibility(View.GONE);
            }
        }
        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.btnRequest.getText().toString()){
                    case "Kết bạn":
                        new AllFriendRepositories().createFriend(user);
                        holder.btnRequest.setText("Hủy");
                        break;
                    case "Hủy":
                        new AllFriendRepositories().deleteFriend(user);
                        holder.btnRequest.setText("Kết bạn");
                        break;
                    case "Đồng ý":
                        new AllFriendRepositories().updateFriend(user);
                        holder.btnRequest.setText("Bạn bè");
                        break;
                    case "Bạn bè":
                        new AllFriendRepositories().deleteFriend(user);
                        holder.btnRequest.setText("Kết bạn");
                        break;
                }
            }
        });

    }
    public User getNoteAt(int position) {
        return getItem(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cimgUser;
        TextView txtUserName, txtSection;
        Button btnRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cimgUser=itemView.findViewById(R.id.CircleImageViewItem);
            txtUserName=itemView.findViewById(R.id.TextViewNameUserItem);
            btnRequest=itemView.findViewById(R.id.ButtonRequestItem);
            txtSection=itemView.findViewById(R.id.textViewHeader);
        }
    }
}