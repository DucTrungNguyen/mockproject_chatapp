package com.rikkei.tranning.chatapp.views.uis.message;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentMessageBinding;
import com.rikkei.tranning.chatapp.services.models.ChatModel;
import com.rikkei.tranning.chatapp.views.adapters.MessageAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class MessageFragment extends BaseFragment<FragmentMessageBinding, ChatViewModel> {
    private MessageAdapter messageAdapter;
    ArrayList<ChatModel> arraySearch = new ArrayList<>();

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
        SimpleItemAnimator itemAnimator = (SimpleItemAnimator) mViewDataBinding.recyclerMessage.getItemAnimator();
        assert itemAnimator != null;
        itemAnimator.setSupportsChangeAnimations(false);
        messageAdapter = new MessageAdapter(getContext());
        mViewDataBinding.recyclerMessage.setAdapter(messageAdapter);
        messageAdapter.setOnItemClickListener(userModel -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            ChatFragment chatFragment = new ChatFragment();
            Bundle bundle = new Bundle();
            bundle.putString("idUser", userModel.getUserModel().getUserId());
            chatFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.frameLayoutChat, chatFragment, null);
            mViewDataBinding.editTextSearchUserChat.setText(null);
            fragmentTransaction.commit();
        });
        mViewDataBinding.editTextSearchUserChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mViewModel.searchUserChat(charSequence.toString(), arraySearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.arrayInfoUserChatLiveData.observe(getViewLifecycleOwner(), chatModels -> {
//            int size = chatModels.size();
//            for (int i = 0; i < size - 1; i++) {
//                for (int j = 0; j < size - i - 1; j++) {
//                    int size1 = chatModels.get(j).getMessageModelArrayList().size() - 1;
//                    int size2 = chatModels.get(j+1).getMessageModelArrayList().size() - 1;
//                    if (chatModels.get(j).getMessageModelArrayList().get(size1).getTimeLong() <
//                            chatModels.get(j + 1).getMessageModelArrayList().get(size2).getTimeLong()) {
//                        Collections.swap(chatModels, j, j + 1);
//                    }
//                }
//            }
            if (chatModels.isEmpty()) {
                mViewDataBinding.ImageViewNoResultChat.setVisibility(View.VISIBLE);
            } else {
                mViewDataBinding.ImageViewNoResultChat.setVisibility(View.GONE);
            }
            messageAdapter.submitList(chatModels);
        });
        mViewModel.arraySearchLiveData.observe(getViewLifecycleOwner(), chatModels -> arraySearch = chatModels);
    }
}
