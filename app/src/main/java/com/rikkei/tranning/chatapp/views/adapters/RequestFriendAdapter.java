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
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.RequestFriendRepository;
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel;

public class RequestFriendAdapter extends ListAdapter<AllUserModel, RequestFriendAdapter.RequestFriendViewHolder> {
    Context context;
    SharedFriendViewModel sharedFriendViewModel;
    boolean isSectionSend = false;
//    var requestFriendRepository = RequestFriendRepository.instance

    public RequestFriendAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        sharedFriendViewModel= ViewModelProviders.of((FragmentActivity) context).get(SharedFriendViewModel.class);
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
    public RequestFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new RequestFriendViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RequestFriendAdapter.RequestFriendViewHolder holder, int position) {
        final AllUserModel user = getItem(position);
        if (user.getUserImage().equals("default")) {
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.cimgUser);
        } else {
            Glide.with(context).load(user.getUserImage()).circleCrop()
                    .into(holder.cimgUser);
        }
//        holder.txtSection.setText("Lời mời kết bạn");
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
        if (position == 0 && user.getUserType().equals("friendRequest")) {
//            holder.txtSection.setVisibility(View.GONE);
            holder.txtSection.setText("Lời mời kết bạn");
        } else  if (position == 0 && user.getUserType().equals("sendRequest")){
            holder.txtSection.setText("Đã gửi kết bạn");
        } else if (getItem(position - 1).getUserType().equals(user.getUserType()))
            {
                holder.txtSection.setVisibility(View.GONE);


            } else if ( !getItem(position - 1).getUserType().equals(user.getUserType())){
            holder.txtSection.setText("Đã gửi kết bạn");

        }
        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.btnRequest.getText().toString()) {
                    case "Hủy":
                        sharedFriendViewModel.deleteFriend(user);
                        break;
                    case "Đồng ý":
                        sharedFriendViewModel.updateFriend(user);
                        break;
                }
            }
        });
    }

    public AllUserModel getNoteAt(int position) {
        return getItem(position);
    }

    public class RequestFriendViewHolder extends RecyclerView.ViewHolder {
        ImageView cimgUser;
        TextView txtUserName, txtSection;
        Button btnRequest;

        public RequestFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            cimgUser = itemView.findViewById(R.id.CircleImageViewItem);
            txtUserName = itemView.findViewById(R.id.TextViewNameUserItem);
            btnRequest = itemView.findViewById(R.id.ButtonRequestItem);
            txtSection = itemView.findViewById(R.id.textViewHeader);
        }
    }
}
