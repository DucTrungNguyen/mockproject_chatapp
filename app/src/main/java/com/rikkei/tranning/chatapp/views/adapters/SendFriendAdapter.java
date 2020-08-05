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
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
//import com.rikkei.tranning.chatapp.services.models.UserModel;


public class SendFriendAdapter extends ListAdapter<AllUserModel, SendFriendAdapter.SendFriendViewHolder> {
    Context context;

    public SendFriendAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
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
    public SendFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new SendFriendViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final SendFriendAdapter.SendFriendViewHolder holder, int position) {
        final AllUserModel user = getItem(position);
        if (user.getUserImage().equals("default")) {
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.cimgUser);
        } else {
            Glide.with(context).load(user.getUserImage()).circleCrop()
                    .into(holder.cimgUser);
        }
        holder.txtSection.setText("Đã gửi kết bạn");
        holder.txtUserName.setText(user.getUserName());
        holder.btnRequest.setText("Hủy");
        if (position != 0) {
            holder.txtSection.setVisibility(View.GONE);
//            int i=position-1;
//            if (i<=this.getItemCount()&&user.getUserName().substring(0,1)
//                    .equals(getNoteAt(i).getUserName().substring(0,1))){
//
//            }
        }
        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.btnRequest.getText().toString()) {

//                    case "Kết bạn":
//                        new RequestFriendRepository().createFriend(user);
//                        holder.btnRequest.setText("Hủy");
//                        break;
//                    case "Hủy":
//                        new RequestFriendRepository().deleteFriend(user);
//                        holder.btnRequest.setText("Kết bạn");
//                        break;
//                    case "Đồng ý":
//                        new RequestFriendRepository().updateFriend(user);
////                            new MyFriendViewModel().getFriendArray();
//                        holder.btnRequest.setText("Bạn bè");
//                        break;
//                    case "Bạn bè":
//                        new RequestFriendRepository().deleteFriend(user);
//                        holder.btnRequest.setText("Kết bạn");
//                        break;
                }
            }
        });
    }

    public AllUserModel getNoteAt(int position) {
        return getItem(position);
    }

    public class SendFriendViewHolder extends RecyclerView.ViewHolder {
        ImageView cimgUser;
        TextView txtUserName, txtSection;
        Button btnRequest;

        public SendFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            cimgUser = itemView.findViewById(R.id.CircleImageViewItem);
            txtUserName = itemView.findViewById(R.id.TextViewNameUserItem);
            btnRequest = itemView.findViewById(R.id.ButtonRequestItem);
            txtSection = itemView.findViewById(R.id.textViewHeader);
        }
    }
}
