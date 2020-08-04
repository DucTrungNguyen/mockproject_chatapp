package com.rikkei.tranning.chatapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.services.models.UserModel;

public class MyFriendAdapter extends ListAdapter<UserModel,MyFriendAdapter.ViewHolder> {
    Context context;
    private OnItemClickListener listener;
    public MyFriendAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
    }
    private static final DiffUtil.ItemCallback<UserModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user=getItem(position);
        holder.btnRequest.setVisibility(View.GONE);
        if(user.getUserImgUrl().equals("default")){
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.cImgUser);
        }
        else{
            Glide.with(context).load(user.getUserImgUrl()).circleCrop()
                    .into(holder.cImgUser);
        }
        holder.txtSection.setText(user.getUserName().substring(0,1));
        holder.txtUserName.setText(user.getUserName());
        if(position >0){
            int i=position-1;
            if (i<=this.getItemCount()&&user.getUserName().substring(0,1)
                    .equals(getNoteAt(i).getUserName().substring(0,1))){
                holder.txtSection.setVisibility(View.GONE);
            }
        }
    }
    public UserModel getNoteAt(int position) {
        return getItem(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cImgUser;
        TextView txtUserName, txtSection;
        Button btnRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cImgUser=itemView.findViewById(R.id.CircleImageViewItem);
            txtUserName=itemView.findViewById(R.id.TextViewNameUserItem);
            btnRequest=itemView.findViewById(R.id.ButtonRequestItem);
            txtSection=itemView.findViewById(R.id.textViewHeader);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(UserModel userModel);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
