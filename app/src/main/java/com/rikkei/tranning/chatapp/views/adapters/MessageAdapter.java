package com.rikkei.tranning.chatapp.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.services.models.ChatModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MessageAdapter extends ListAdapter<ChatModel, MessageAdapter.ViewHolder> {
    Context context;
    private OnItemClickListener listener;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    public MessageAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<ChatModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ChatModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ChatModel oldItem, @NonNull ChatModel newItem) {
            return oldItem.getUserModel().equals(newItem.getUserModel());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ChatModel oldItem, @NonNull ChatModel newItem) {
            return oldItem.getMessageModelArrayList().equals(newItem.getMessageModelArrayList());
//            return oldItem.getUserName().equals(newItem.getUserName())
//                    && oldItem.getUserImgUrl().equals(newItem.getUserImgUrl())
//                    && oldItem.getUserEmail().equals(newItem.getUserName())
//                    && oldItem.getUserDateOfBirth().equals(newItem.getUserDateOfBirth())
//                    && oldItem.getUserPhone().equals(newItem.getUserPhone());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_massage, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel userModel = getItem(position);
        holder.txtUserName.setText(userModel.getUserModel().getUserName());
        if (userModel.getUserModel().getUserImgUrl().equals("default")) {
            Glide.with(context).load(R.mipmap.ic_launcher).circleCrop().into(holder.cImgUser);
        } else {
            Glide.with(context).load(userModel.getUserModel().getUserImgUrl()).circleCrop()
                    .into(holder.cImgUser);
        }
        String message=null;
        String date=null;
        String time=null;
        String type=null;
        String idSender=null;
        int count=0;
        for (int i=0;i<userModel.getMessageModelArrayList().size();i++){
            if (userModel.getMessageModelArrayList().get(i).getIdReceiver().equals(firebaseUser.getUid())
            && userModel.getMessageModelArrayList().get(i).getIdSender().equals(userModel.getUserModel().getUserId())
            && !userModel.getMessageModelArrayList().get(i).getCheckSeen()){
                count++;
            }
            idSender=userModel.getMessageModelArrayList().get(i).getIdSender();
            message=userModel.getMessageModelArrayList().get(i).getMessage();
            date=userModel.getMessageModelArrayList().get(i).getDate();
            time=userModel.getMessageModelArrayList().get(i).getTime();
            type=userModel.getMessageModelArrayList().get(i).getType();
        }
        assert idSender != null;
        if (idSender.equals(firebaseUser.getUid())){
            message="Bạn: "+ message;
        }
        assert type != null;
        if (type.equals("Text")){
            holder.txtLastMessage.setText(message);
        }
        else {
            holder.txtLastMessage.setText("Image");
        }
        if (count > 0) {
            holder.txtSumUnRead.setVisibility(View.VISIBLE);
            if (count > 9) {
                holder.txtSumUnRead.setText("" + 9 + "+");
            } else {
                holder.txtSumUnRead.setText(" " + count + " ");
            }
            holder.txtLastMessage.setTextColor(Color.BLACK);
            holder.txtLastMessage.setTypeface(Typeface.DEFAULT_BOLD);
            holder.txtTime.setTextColor(Color.BLACK);
            holder.txtTime.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            holder.txtSumUnRead.setVisibility(View.GONE);
            holder.txtLastMessage.setTypeface(Typeface.DEFAULT);
            holder.txtTime.setTypeface(Typeface.DEFAULT);
            holder.txtLastMessage.setTextColor(ContextCompat.getColor(context, R.color.grey));
            holder.txtTime.setTextColor(ContextCompat.getColor(context, R.color.grey));
        }
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String a = simpleDateFormatDate.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        // String before=simpleDateFormatDate.format(calendar.getTime());
        if (a.equals(date)) {
            holder.txtTime.setText(time);
        }
//            else if (a.trim().equals(before.trim())){
//                holder.txtTime.setText("Hôm qua");
//            }
        else {
            holder.txtTime.setText(date);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
                    listener.onItemClick(getItem(position));
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
