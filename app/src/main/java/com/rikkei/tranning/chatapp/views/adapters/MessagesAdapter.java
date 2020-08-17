package com.rikkei.tranning.chatapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.services.models.ChatModel;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>  {
    Context context;
    ArrayList<ChatModel> chatModelArrayList;
    private OnItemClickListener listener;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    public MessagesAdapter(Context context, ArrayList<ChatModel> chatModelArrayList) {
        this.context = context;
        this.chatModelArrayList = chatModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_massage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chatModelArrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cImgUser;
        TextView txtUserName, txtLastMessage, txtTime, txtSumUnRead;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSumUnRead=itemView.findViewById(R.id.textViewSumUnRead);
            cImgUser = itemView.findViewById(R.id.imageViewCircleChat);
            txtUserName = itemView.findViewById(R.id.textViewUserChatName);
            txtLastMessage=itemView.findViewById(R.id.textViewLastMess);
            txtTime=itemView.findViewById(R.id.textViewTimeMessage);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(chatModelArrayList.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ChatModel userModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
