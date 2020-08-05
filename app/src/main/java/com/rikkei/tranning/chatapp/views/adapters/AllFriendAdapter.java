package com.rikkei.tranning.chatapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.allfriends.AllFriendFragment;

import java.util.ArrayList;

public class AllFriendAdapter extends ListAdapter<AllUserModel,AllFriendAdapter.ViewHolder> {
    SharedFriendViewModel sharedFriendViewModel;
    Context context;
    public AllFriendAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
        sharedFriendViewModel=ViewModelProviders.of((FragmentActivity) context).get(SharedFriendViewModel.class);
    }
    private static final DiffUtil.ItemCallback<AllUserModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<AllUserModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull AllUserModel oldItem, @NonNull AllUserModel newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull AllUserModel oldItem, @NonNull AllUserModel newItem) {
            return  oldItem.getUserName().equals(newItem.getUserName())
                    && oldItem.getUserImage().equals(newItem.getUserImage())
                    && oldItem.getUserType().equals(newItem.getUserType());
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
        final AllUserModel user=getItem(position);
        if(user.getUserImage().equals("default")){
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.cimgUser);
        }
        else{
            Glide.with(context).load(user.getUserImage()).circleCrop()
                    .into(holder.cimgUser);
        }
        holder.txtSection.setText(user.getUserName().substring(0,1));
        holder.txtUserName.setText(user.getUserName());

        switch (user.getUserType()){
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
                        sharedFriendViewModel.createFriend(user);
                        holder.btnRequest.setText("Hủy");
                        break;
                    case "Hủy":
                        sharedFriendViewModel.deleteFriend(user);
                        holder.btnRequest.setText("Kết bạn");
                        break;
                    case "Bạn bè":
                        sharedFriendViewModel.deleteFriend(user);
                        holder.btnRequest.setText("Kết bạn");
                        sharedFriendViewModel.getInfoAllFriend();
                        break;
                    case "Đồng ý":
                        sharedFriendViewModel.updateFriend(user);
                        sharedFriendViewModel.getInfoAllFriend();
                        holder.btnRequest.setText("Bạn bè");
                        break;
                }
            }
        });
    }
    public AllUserModel getNoteAt(int position) {
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
