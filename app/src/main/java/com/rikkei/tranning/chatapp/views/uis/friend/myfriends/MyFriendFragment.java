package com.rikkei.tranning.chatapp.views.uis.friend.myfriends;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentMyFriendsBinding;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.views.adapters.MyFriendAdapter;

import java.util.ArrayList;

public class MyFriendFragment extends BaseFragment<FragmentMyFriendsBinding,MyFriendViewModel> {
    FragmentMyFriendsBinding mFragmentMyFriendsBinding;
    MyFriendViewModel mMyFriendViewModel;
    MyFriendAdapter myFriendAdapter;
    @Override
    public int getBindingVariable() {
        return BR.viewModelMyFriend;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_friends;
    }

    @Override
    public MyFriendViewModel getViewModel() {
        mMyFriendViewModel= ViewModelProviders.of(this, new ViewModelProviderFactory()).get(MyFriendViewModel.class);
        return mMyFriendViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentMyFriendsBinding=getViewDataBinding();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myFriendAdapter=new MyFriendAdapter(getContext());
        mFragmentMyFriendsBinding.RecyclerMyFriend.setLayoutManager(layoutManager);
        mFragmentMyFriendsBinding.RecyclerMyFriend.setAdapter(myFriendAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyFriendViewModel.getFriendArray();
        mMyFriendViewModel.listMyFriendMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userArrayList) {
                mMyFriendViewModel.collectionArray(userArrayList);
                myFriendAdapter.submitList(userArrayList);
            }
        });
    }
}
