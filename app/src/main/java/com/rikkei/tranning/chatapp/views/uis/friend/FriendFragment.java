package com.rikkei.tranning.chatapp.views.uis.friend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.readystatesoftware.viewbadger.BadgeView;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.databinding.FragmentFriendBinding;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.views.adapters.MainViewPaperAdaper;
import com.rikkei.tranning.chatapp.views.uis.friend.allfriends.AllFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendFragment;
import com.rikkei.tranning.chatapp.views.uis.friend.requestfriends.RequestFriendFragment;

import java.util.ArrayList;

public class FriendFragment extends BaseFragment<FragmentFriendBinding, SharedFriendViewModel> {
    ArrayList<AllUserModel> allUserArrayList = new ArrayList<>();
    TabLayout.Tab tabRequest;
    TabLayout.Tab tabFriend;
    TabLayout.Tab tabAll;
    BadgeView badgeViewRequest;
    TextView countNotifi;
    View textRequest;

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
        return ViewModelProviders.of(requireActivity()).get(SharedFriendViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewPaperAdaper mainViewPaperAdaper = new MainViewPaperAdaper(getParentFragmentManager());
        mainViewPaperAdaper.AddFragment(new MyFriendFragment(), "BẠN BÈ");
        mainViewPaperAdaper.AddFragment(new AllFriendFragment(), "TẤT CẢ");
        mainViewPaperAdaper.AddFragment(new RequestFriendFragment(), "Yêu cầu");
        mViewDataBinding.viewPagerFriend.setAdapter(mainViewPaperAdaper);
        mViewDataBinding.tabLayoutFriend.setupWithViewPager(mViewDataBinding.viewPagerFriend);
        mViewDataBinding.editTextSearchFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.searchFriend(s.toString(), allUserArrayList);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        View textFriend = (View) LayoutInflater.from(getContext()).inflate(R.layout.custom_tablayout, null);
        TextView textNotifiFriend = textFriend.findViewById(R.id.text1);
        textNotifiFriend.setText("BẠN BÈ");
        tabFriend = mViewDataBinding.tabLayoutFriend.getTabAt(0);
        tabFriend.setCustomView(textFriend);

        View textAll = (View) LayoutInflater.from(getContext()).inflate(R.layout.custom_tablayout, null);
        TextView textNotifiAll = textAll.findViewById(R.id.text1);
        textNotifiAll.setText("TẤT CẢ");
        tabAll = mViewDataBinding.tabLayoutFriend.getTabAt(1);
        tabAll.setCustomView(textAll);


        textRequest= LayoutInflater.from(getContext()).inflate(R.layout.custom_tablayout, null);
        TextView textNotifiRequest = textRequest.findViewById(R.id.text1);
        countNotifi = textNotifiFriend.findViewById(R.id.textNotifi);
        textNotifiRequest.setText("YÊU CẦU");
        tabRequest = mViewDataBinding.tabLayoutFriend.getTabAt(2);
        tabRequest.setCustomView(textRequest);



    }

    @SuppressLint("ResourceType")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getUserFromLiveData.observe(getViewLifecycleOwner(), allUserModelArrayList -> allUserArrayList = allUserModelArrayList);

        mViewModel.countNotifiRequest.observe(getViewLifecycleOwner(), s -> {
            View viewRequest = mViewDataBinding.tabLayoutFriend.getTabAt(2).getCustomView();

            TextView  count = (TextView)viewRequest.findViewById(R.id.textNotifi);


            if (s.equals("0"))
                count.setVisibility(View.GONE);
            else {
                count.setVisibility(View.VISIBLE);
                count.setText(s);

            }

        });



    }
}
