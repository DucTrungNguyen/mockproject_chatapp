package com.rikkei.tranning.chatapp.views.uis.message;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentChatBinding;
import com.rikkei.tranning.chatapp.services.models.MessageModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.views.adapters.ChatAdapter;
import com.rikkei.tranning.chatapp.views.uis.friend.FriendViewModel;

import java.util.ArrayList;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatViewModel> {
    FragmentChatBinding mFragmentChatBinding;
    ChatViewModel mChatViewModel;
    ChatAdapter chatAdapter;
    String id;
    @Override
    public int getBindingVariable() {
        return BR.viewModelChat;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public ChatViewModel getViewModel() {
        mChatViewModel = ViewModelProviders.of(this, new ViewModelProviderFactory()).get(ChatViewModel.class);
        return mChatViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentChatBinding=getViewDataBinding();
        mFragmentChatBinding.ImageButtonBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        mFragmentChatBinding.imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=getArguments();
                String iD=bundle.getString("idUser");
                String message= mFragmentChatBinding.editTextMessage.getText().toString().trim();
                mChatViewModel.sendMessage(iD, message);
                mFragmentChatBinding.editTextMessage.setText("");
                mChatViewModel.displayMessage(id);
            }
        });
        mFragmentChatBinding.imageButtonSend.setEnabled(false);
        mFragmentChatBinding.editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s.toString())){
                    mFragmentChatBinding.imageButtonSend.setEnabled(false);
                    mFragmentChatBinding.imageButtonSend.setImageResource(R.drawable.ic_send_unable);
                }
                else {
                    mFragmentChatBinding.imageButtonSend.setEnabled(true);
                    mFragmentChatBinding.imageButtonSend.setImageResource(R.drawable.ic_send);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle=getArguments();
        id=bundle.getString("idUser");
        mChatViewModel.getInfoUserChat(id);
        mChatViewModel.userChatLiveData.observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel.getUserImgUrl().equals("default")){
                    Glide.with(getContext()).load(R.mipmap.ic_launcher).circleCrop().into(mFragmentChatBinding.imageViewTitleChat);
                }
                else {
                    Glide.with(getContext()).load(userModel.getUserImgUrl()).circleCrop().into(mFragmentChatBinding.imageViewTitleChat);
                }
                mFragmentChatBinding.textViewUserNameChat.setText(userModel.getUserName());
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                chatAdapter=new ChatAdapter(getContext(),userModel.getUserImgUrl());
                mFragmentChatBinding.recyclerChat.setLayoutManager(layoutManager);
                mFragmentChatBinding.recyclerChat.setAdapter(chatAdapter);
            }
        });
        mChatViewModel.displayMessage(id);
        mChatViewModel.messageListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<MessageModel>>() {
            @Override
            public void onChanged(ArrayList<MessageModel> messageModels) {
                chatAdapter.submitList(messageModels);
            }
        });
    }
    public void removeFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.frameLayoutChat);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
