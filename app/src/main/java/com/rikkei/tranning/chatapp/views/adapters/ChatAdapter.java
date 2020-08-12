package com.rikkei.tranning.chatapp.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.services.models.MessageModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChatAdapter extends ListAdapter<MessageModel, ChatAdapter.ViewHolder> {
    Context context;
    public int TITLE_LEFT = 0;
    public int TITLE_RIGHT = 1;
    public String urlImage;

    public ChatAdapter(Context context, String urlImage) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.urlImage = urlImage;
    }

    private static final DiffUtil.ItemCallback<MessageModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<MessageModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MessageModel oldItem, @NonNull MessageModel newItem) {
            return oldItem.getTimeLong() == newItem.getTimeLong();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MessageModel oldItem, @NonNull MessageModel newItem) {
            return oldItem.getIdSender().equals(newItem.getIdSender())
                    && oldItem.getIdReceiver().equals(newItem.getIdReceiver())
                    && oldItem.getMessage().equals(newItem.getMessage())
                    && oldItem.getType().equals(newItem.getType())
                    && oldItem.getDate().equals(newItem.getDate());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TITLE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MessageModel messageModel = getItem(position);
        if (messageModel.getType().equals("Text")) {
            holder.txtMessage.setVisibility(View.VISIBLE);
            holder.txtMessage.setText(messageModel.getMessage());
            holder.imgMessage.setVisibility(View.GONE);
        } else {
            holder.imgMessage.setVisibility(View.VISIBLE);
            holder.txtMessage.setVisibility(View.GONE);
            Glide.with(context).load(messageModel.getMessage()).into(holder.imgMessage);
        }
        if (urlImage.equals("default")) {
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.imgUserChat);
        } else {
            Glide.with(context).load(urlImage).circleCrop().into(holder.imgUserChat);
        }
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String a = simpleDateFormatDate.format(calendar.getTime());
        if (messageModel.getDate().equals(a)) {
            holder.txtDate.setText(messageModel.getTime());
            holder.txtChatDate.setText("Hôm nay");
        } else {
            holder.txtChatDate.setText(messageModel.getDate());
            holder.txtDate.setText(messageModel.getDate() + " " + messageModel.getTime());
        }
        if (position > 0) {
            int i = position - 1;
            MessageModel message = getNoteAt(i);
            if (i <= this.getItemCount() && messageModel.getIdSender().equals(message.getIdSender())
                    && messageModel.getIdReceiver().equals(message.getIdReceiver())) {
                holder.imgUserChat.setVisibility(View.GONE);
                holder.view.setVisibility(View.VISIBLE);
            }
            if (i <= this.getItemCount() && messageModel.getDate().equals(message.getDate())) {
                holder.txtChatDate.setVisibility(View.GONE);
            }
        }
        if (position <this.getItemCount()-1) {
            int j = position + 1;
            MessageModel message2 = getNoteAt(j);
            if (j >= 0 && messageModel.getIdSender().equals(message2.getIdSender())
                    && messageModel.getIdReceiver().equals(message2.getIdReceiver())) {
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        }
        holder.txtMessage.setOnClickListener(v -> {
            if (holder.txtDate.getVisibility() == View.VISIBLE) {
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        });
        holder.imgMessage.setOnClickListener(view -> {
            if (holder.txtDate.getVisibility() == View.VISIBLE) {
                holder.txtDate.setVisibility(View.GONE);
            } else {
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUserChat, imgMessage;
        TextView txtMessage, txtDate, txtChatDate;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChatDate = itemView.findViewById(R.id.textViewChatDate);
            imgMessage = itemView.findViewById(R.id.imageViewMessage);
            imgUserChat = itemView.findViewById(R.id.imageViewUserChat);
            txtMessage = itemView.findViewById(R.id.textViewMessage);
            view = itemView.findViewById(R.id.view);
            txtDate = itemView.findViewById(R.id.textViewDate);
        }
    }

    public MessageModel getNoteAt(int position) {
        return getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String id = "";
        if (firebaseUser != null) {
            id = firebaseUser.getUid();
        }
        if (getNoteAt(position).getIdSender().equals(id)) {
            return TITLE_RIGHT;
        } else {
            return TITLE_LEFT;
        }
    }
}
