package com.rikkei.tranning.chatapp.views.adapters;

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
public class ChatAdapter extends ListAdapter<MessageModel,ChatAdapter.ViewHolder> {
    Context context;
    public int TITLE_LEFT=0;
    public int TITLE_RIGHT=1;
    public String urlImage;
    public ChatAdapter(Context context, String urlImage) {
        super(DIFF_CALLBACK);
        this.context=context;
        this.urlImage=urlImage;
    }
    private static final DiffUtil.ItemCallback<MessageModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<MessageModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MessageModel oldItem, @NonNull MessageModel newItem) {
            return oldItem.getIdReceiver().equals(newItem.getIdReceiver())
                    && oldItem.getIdSender().equals(newItem.getIdSender());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MessageModel oldItem, @NonNull MessageModel newItem) {
            return  oldItem.getMessage().equals(newItem.getMessage())
                    && oldItem.getType().equals(newItem.getType())
                    && oldItem.getDate().equals(newItem.getDate());
        }
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==TITLE_RIGHT){
            View view= LayoutInflater.from(context).inflate(R.layout.item_message_right,parent,false);
            return new ViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.item_message_left,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MessageModel messageModel=getItem(position);
        holder.txtMessage.setText(messageModel.getMessage());
        if(urlImage.equals("default")){
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.imgUserChat);
        }
        else {
            Glide.with(context).load(urlImage).circleCrop().into(holder.imgUserChat);
        }
        holder.txtDate.setText(messageModel.getTime());
        if(position >0){
            int i=position-1;
            MessageModel message=getNoteAt(i);
            if (i<=this.getItemCount() && messageModel.getIdSender().equals(message.getIdSender())
            && messageModel.getIdReceiver().equals(message.getIdReceiver())){
                holder.imgUserChat.setVisibility(View.GONE);
                holder.view.setVisibility(View.VISIBLE);
            }
        }
        if(position<this.getItemCount()-1){
            int j=position+1;
            MessageModel message2=getNoteAt(j);
            if(j>=0 && messageModel.getIdSender().equals(message2.getIdSender())
                    && messageModel.getIdReceiver().equals(message2.getIdReceiver())){
                holder.txtDate.setVisibility(View.GONE);
            }
            else{
                holder.txtDate.setVisibility(View.VISIBLE);
            }
        }
        holder.txtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.txtDate.getVisibility()==View.VISIBLE){
                    holder.txtDate.setVisibility(View.GONE);
                }
                else{
                    holder.txtDate.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgUserChat;
        TextView txtMessage, txtDate;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUserChat=itemView.findViewById(R.id.imageViewUserChat);
            txtMessage=itemView.findViewById(R.id.textViewMessage);
            view=itemView.findViewById(R.id.view);
            txtDate=itemView.findViewById(R.id.textViewDate);
        }
    }
    public MessageModel getNoteAt(int position) {
        return getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(getNoteAt(position).getIdSender().equals(firebaseUser.getUid())){
            return TITLE_RIGHT;
        }
        else{
            return TITLE_LEFT;
        }
    }
}
