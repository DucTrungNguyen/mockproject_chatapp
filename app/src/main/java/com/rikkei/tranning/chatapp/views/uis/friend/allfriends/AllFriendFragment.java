package com.rikkei.tranning.chatapp.views.uis.friend.allfriends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.Base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentAllFriendsBinding;
import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.repositories.AllFriendRepositories;
import com.rikkei.tranning.chatapp.views.adapters.AllFriendAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class AllFriendFragment extends BaseFragment<FragmentAllFriendsBinding, AllFriendViewModel> {
    FragmentAllFriendsBinding mFragmentAllFriendsBinding;
    AllFriendViewModel mAllFriendViewModel;
    AllFriendAdapter allFriendAdapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModelAllFriend;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all_friends;
    }

    @Override
    public AllFriendViewModel getViewModel() {
        mAllFriendViewModel = ViewModelProviders.of(this, new ViewModelProviderFactory()).get(AllFriendViewModel.class);
        return mAllFriendViewModel;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentAllFriendsBinding = getViewDataBinding();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentAllFriendsBinding.RecyclerAllFriend.setLayoutManager(layoutManager);
        allFriendAdapter = new AllFriendAdapter(getContext());
        mFragmentAllFriendsBinding.RecyclerAllFriend.setAdapter(allFriendAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAllFriendViewModel.getAllUserInfo();
        mAllFriendViewModel.listUserMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userArrayList) {
               mAllFriendViewModel.collectionArray(userArrayList);
                allFriendAdapter.submitList(userArrayList);
            }
        });
    }
}