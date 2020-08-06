package com.rikkei.tranning.chatapp.views.uis.message;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

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

import java.util.ArrayList;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatViewModel> {
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

        mViewDataBinding.ImageButtonBackChat.setOnClickListener(v -> removeFragment());
        mViewDataBinding.imageButtonSend.setOnClickListener(v -> {
            Bundle bundle = getArguments();
            String iD = null;
            if (bundle != null) {
                iD = bundle.getString("idUser");
            }
            String message = mViewDataBinding.editTextMessage.getText().toString().trim();
            mChatViewModel.sendMessage(iD, message);
            mViewDataBinding.editTextMessage.setText("");
            mChatViewModel.displayMessage(id);
        });
        mViewDataBinding.imageButtonSend.setEnabled(false);
        mViewDataBinding.editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    mViewDataBinding.imageButtonSend.setEnabled(false);
                    mViewDataBinding.imageButtonSend.setImageResource(R.drawable.ic_send_unable);
                }
                else {
                    mViewDataBinding.imageButtonSend.setEnabled(true);
                    mViewDataBinding.imageButtonSend.setImageResource(R.drawable.ic_send);
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
                if (userModel.getUserImgUrl().equals("default")) {
                    Glide.with(getContext()).load(R.mipmap.ic_launcher).circleCrop().into(mViewDataBinding.imageViewTitleChat);
                } else {
                    Glide.with(getContext()).load(userModel.getUserImgUrl()).circleCrop().into(mViewDataBinding.imageViewTitleChat);
                }
                mViewDataBinding.textViewUserNameChat.setText(userModel.getUserName());
                mViewDataBinding.recyclerChat.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setStackFromEnd(true);
                chatAdapter = new ChatAdapter(getContext(), userModel.getUserImgUrl());
                mViewDataBinding.recyclerChat.setLayoutManager(layoutManager);
                mViewDataBinding.recyclerChat.setAdapter(chatAdapter);
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
