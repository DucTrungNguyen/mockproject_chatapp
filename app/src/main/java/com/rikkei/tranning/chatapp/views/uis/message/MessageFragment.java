package com.rikkei.tranning.chatapp.views.uis.message;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentMessageBinding;
import com.rikkei.tranning.chatapp.views.adapters.MessageAdapter;

public class MessageFragment extends BaseFragment<FragmentMessageBinding, ChatViewModel> {
    private MessageAdapter messageAdapter;

    @Override
    public int getBindingVariable() {
        return BR.messageViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public ChatViewModel getViewModel() {
        return ViewModelProviders.of(requireActivity()).get(ChatViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageAdapter = new MessageAdapter(getContext());
        mViewDataBinding.recyclerMessage.setAdapter(messageAdapter);
        messageAdapter.setOnItemClickListener(userModel -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            ChatFragment chatFragment = new ChatFragment();
            Bundle bundle = new Bundle();
            bundle.putString("idUser", userModel.getUserId());
            chatFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.frameLayoutChat, chatFragment, null);
            fragmentTransaction.commit();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.arrayInfoUserChatLiveData.observe(getViewLifecycleOwner(), userModels -> {
            if (userModels.isEmpty()) {
                mViewDataBinding.ImageViewNoResultChat.setVisibility(View.VISIBLE);
            } else {
                mViewDataBinding.ImageViewNoResultChat.setVisibility(View.GONE);
                messageAdapter.submitList(userModels);
            }
        });
    }
}
