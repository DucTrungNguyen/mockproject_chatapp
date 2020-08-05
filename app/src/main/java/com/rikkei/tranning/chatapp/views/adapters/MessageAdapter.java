package com.rikkei.tranning.chatapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.models.MessageModel;

public class MessageAdapter extends ListAdapter<MessageModel, MessageAdapter.ViewHolder > {
    Context context;
    private OnItemClickListener listener;
    public MessageAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
    }
    private static final DiffUtil.ItemCallback<MessageModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<MessageModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MessageModel oldItem, @NonNull MessageModel newItem) {
            return oldItem.getIdSender().equals(newItem.getIdSender());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MessageModel oldItem, @NonNull MessageModel newItem) {
            return  oldItem.getIdReceiver().equals(newItem.getIdReceiver())
                    && oldItem.getIdSender().equals(newItem.getIdSender());
//                    && oldItem.().equals(newItem.getUserType());

        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_massage,parent,false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cImgUser;
        RelativeLayout relativeItemFriend;
        TextView txtUserName, txtSection;
        Button btnRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            relativeItemFriend = itemView.findViewById(R.id.relativeItemFriend);
//            cImgUser = itemView.findViewById(R.id.CircleImageViewItem);
//            txtUserName = itemView.findViewById(R.id.TextViewNameUserItem);
//            btnRequest = itemView.findViewById(R.id.ButtonRequestItem);
//            txtSection = itemView.findViewById(R.id.textViewHeader);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(getItem(position));
//                    }
//                }
//            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MessageModel userModel);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}
