package com.rikkei.tranning.chatapp.views.uis.message;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentChatBinding;
import com.rikkei.tranning.chatapp.views.adapters.ChatAdapter;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatViewModel> {
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
        return ViewModelProviders.of(requireActivity()).get(ChatViewModel.class);
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
            mViewModel.sendMessage(iD, message);
            mViewDataBinding.editTextMessage.setText("");
            mViewModel.displayMessage(id);
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
                } else {
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("idUser");
        }
        mViewModel.getInfoUserChat(id);
        mViewModel.userChatLiveData.observe(getViewLifecycleOwner(), userModel -> {
            if (userModel.getUserImgUrl().equals("default")) {
                Glide.with(requireContext()).load(R.mipmap.ic_launcher).circleCrop().into(mViewDataBinding.imageViewTitleChat);
            } else {
                Glide.with(requireContext()).load(userModel.getUserImgUrl()).circleCrop().into(mViewDataBinding.imageViewTitleChat);
            }
            mViewDataBinding.textViewUserNameChat.setText(userModel.getUserName());
            mViewDataBinding.recyclerChat.setHasFixedSize(true);
            chatAdapter = new ChatAdapter(getContext(), userModel.getUserImgUrl());
            mViewDataBinding.recyclerChat.setAdapter(chatAdapter);
        });
        mViewModel.displayMessage(id);
        mViewModel.messageListLiveData.observe(getViewLifecycleOwner(), messageModels ->
                chatAdapter.submitList(messageModels));

    }

    public void removeFragment() {
        Fragment fragment = getParentFragmentManager().findFragmentById(R.id.frameLayoutChat);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        assert fragment != null;
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
