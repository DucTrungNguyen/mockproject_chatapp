package com.rikkei.tranning.chatapp.views.uis.friend;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentFriendBinding;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.views.adapters.MainViewPaperAdaper;
import com.rikkei.tranning.chatapp.views.uis.friend.allfriends.AllFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.friend.requestfriends.RequestFriendFragment;

import java.util.ArrayList;

public class FriendFragment extends BaseFragment<FragmentFriendBinding, SharedFriendViewModel> {
    private SharedFriendViewModel sharedFriendViewModel;
    ArrayList<AllUserModel> allUserArrayList=new ArrayList<>();
    @Override
    public int getBindingVariable() {
        return BR.viewModelFriend;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    public SharedFriendViewModel getViewModel() {
        sharedFriendViewModel=ViewModelProviders.of(getActivity()).get(SharedFriendViewModel.class);
        return sharedFriendViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewPaperAdaper mainViewPaperAdaper = new MainViewPaperAdaper(getFragmentManager());
        mainViewPaperAdaper.AddFragment(new MyFriendFragment(), "Bạn bè");
        mainViewPaperAdaper.AddFragment(new AllFriendFragment(), "Tất cả");
        mainViewPaperAdaper.AddFragment(new RequestFriendFragment(), "Yêu cầu");
        mViewDataBinding.viewPagerFriend.setAdapter(mainViewPaperAdaper);
        mViewDataBinding.tabLayoutFriend.setupWithViewPager(mViewDataBinding.viewPagerFriend);
        mViewDataBinding.editTextSearchFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sharedFriendViewModel.searchFriend(s.toString(), allUserArrayList);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//        mFragmentFriendBinding.imageViewSearchFriend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFragment();
//            }
//        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedFriendViewModel.getUserFromLiveData.observe(getViewLifecycleOwner(), allUserModelArrayList -> allUserArrayList = allUserModelArrayList);
    }
}
