package com.rikkei.tranning.chatapp.views.uis.friend;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentFriendBinding;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.views.adapters.MainViewPaperAdaper;
import com.rikkei.tranning.chatapp.views.uis.friend.allfriends.AllFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.friend.allfriends.AllFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.requestfriends.RequestFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.friend.searchfriends.SearchFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.message.ChatFragment;

import java.util.ArrayList;
public class FriendFragment extends BaseFragment<FragmentFriendBinding, FriendViewModel> {
    FragmentFriendBinding mFragmentFriendBinding;
    FriendViewModel mFriendsViewmodel;

    @Override
    public int getBindingVariable() {
        return BR.viewModelFriend;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    public FriendViewModel getViewModel() {
        mFriendsViewmodel = ViewModelProviders.of(this, new ViewModelProviderFactory()).get(FriendViewModel.class);
        return mFriendsViewmodel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentFriendBinding = getViewDataBinding();
        MainViewPaperAdaper mainViewPaperAdaper = new MainViewPaperAdaper(getFragmentManager());
        mainViewPaperAdaper.AddFragment(new MyFriendFragment(), "Bạn bè");
        mainViewPaperAdaper.AddFragment(new AllFriendFragment(), "Tất cả");
        mainViewPaperAdaper.AddFragment(new RequestFriendFragment(), "Yêu cầu");
        mFragmentFriendBinding.viewPagerFriend.setAdapter(mainViewPaperAdaper);
        mFragmentFriendBinding.tabLayoutFriend.setupWithViewPager(mFragmentFriendBinding.viewPagerFriend);
        mFragmentFriendBinding.editTextSearchFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFriendsViewmodel.updateArray(s.toString());
                mFriendsViewmodel.updateArrayMyFriend(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mFragmentFriendBinding.imageViewSearchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFriendsViewmodel.userArrayLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userArrayList) {
                AllFriendViewModel.listUserMutableLiveData.setValue(userArrayList);
            }
        });
        mFriendsViewmodel.myFriendSearchLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userArrayList) {
                MyFriendViewModel.listMyFriendMutableLiveData.setValue(userArrayList);
            }
        });
    }
    public void addFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        SearchFriendFragment searchFriendFragment=new SearchFriendFragment();
        fragmentTransaction.add(R.id.frameLayoutChat,searchFriendFragment,null);
        fragmentTransaction.commit();
    }
}
