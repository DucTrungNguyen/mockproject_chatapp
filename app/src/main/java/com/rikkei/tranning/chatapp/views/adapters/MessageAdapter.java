package com.rikkei.tranning.chatapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.views.uis.message.ChatViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MessageAdapter extends ListAdapter<UserModel, MessageAdapter.ViewHolder> {
    Context context;
    private OnItemClickListener listener;
    ChatViewModel chatViewModel;
    public MessageAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        chatViewModel=ViewModelProviders.of((FragmentActivity) context).get(ChatViewModel.class);
    }

    private static final DiffUtil.ItemCallback<UserModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {
            return oldItem.getUserName().equals(newItem.getUserName())
                    && oldItem.getUserImgUrl().equals(newItem.getUserImgUrl());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_massage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = getItem(position);
        holder.txtUserName.setText(userModel.getUserName());
        if (userModel.getUserImgUrl().equals("default")) {
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.cImgUser);
        } else {
            Glide.with(context).load(userModel.getUserImgUrl()).circleCrop()
                    .into(holder.cImgUser);
        }
        chatViewModel.getListMessage(userModel.getUserId(), (message, time, date) -> {
            holder.txtLastMessage.setText(message);
            final Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String a = simpleDateFormatDate.format(calendar.getTime());
            calendar.add(Calendar.DATE, -1);
            String before=simpleDateFormatDate.format(calendar.getTime());
            if(a.equals(date)){
                holder.txtTime.setText(time);
            }
//            else if (a.trim().equals(before.trim())){
//                holder.txtTime.setText("Hôm qua");
//            }
            else {
                holder.txtTime.setText(date);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cImgUser;
        TextView txtUserName, txtLastMessage, txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cImgUser = itemView.findViewById(R.id.imageViewCircleChat);
            txtUserName = itemView.findViewById(R.id.textViewUserChatName);
            txtLastMessage=itemView.findViewById(R.id.textViewLastMess);
            txtTime=itemView.findViewById(R.id.textViewTimeMessage);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
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
