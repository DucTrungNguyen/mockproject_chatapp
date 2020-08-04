package com.rikkei.tranning.chatapp.views.uis.friend.searchfriends;

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

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentSearchFriendBinding;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.views.adapters.MyFriendAdapter;
import com.rikkei.tranning.chatapp.views.uis.friend.FriendViewModel;

import java.util.ArrayList;

public class SearchFriendFragment extends BaseFragment<FragmentSearchFriendBinding, SearchFriendViewModel> {
    FragmentSearchFriendBinding mFragmentSearchFriendBinding;
    SearchFriendViewModel mSearchFriendViewModel;
    MyFriendAdapter myFriendAdapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModelSearchFriend;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_friend;
    }

    @Override
    public SearchFriendViewModel getViewModel() {
        mSearchFriendViewModel = ViewModelProviders.of(this, new ViewModelProviderFactory()).get(SearchFriendViewModel.class);
        return mSearchFriendViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSearchFriendBinding = getViewDataBinding();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myFriendAdapter = new MyFriendAdapter(getContext());
        mFragmentSearchFriendBinding.recyclerSearchFriend.setLayoutManager(layoutManager);
        mFragmentSearchFriendBinding.recyclerSearchFriend.setAdapter(myFriendAdapter);
        mFragmentSearchFriendBinding.imageViewNoResult.setVisibility(View.GONE);
        mFragmentSearchFriendBinding.textViewNoResult.setVisibility(View.GONE);
        //   mFragmentSearchFriendBinding.textViewTitleFriend.setVisibility(View.GONE);
        mFragmentSearchFriendBinding.editTextSearchFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    ArrayList<UserModel> arrayList = new ArrayList<>();
                    mSearchFriendViewModel.listFriendSearchLiveData.setValue(arrayList);
                } else {
                    mSearchFriendViewModel.getListFriendSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mFragmentSearchFriendBinding.textViewCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchFriendViewModel.listFriendSearchLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
//                if(userModels.isEmpty()){
//                    mFragmentSearchFriendBinding.imageViewNoResult.setVisibility(View.VISIBLE);
//                    mFragmentSearchFriendBinding.textViewNoResult.setVisibility(View.VISIBLE);
//                    mFragmentSearchFriendBinding.textViewTitleFriend.setVisibility(View.GONE);
//                }
//                else{
//                    mFragmentSearchFriendBinding.imageViewNoResult.setVisibility(View.GONE);
//                    mFragmentSearchFriendBinding.textViewNoResult.setVisibility(View.GONE);
//                    mFragmentSearchFriendBinding.textViewTitleFriend.setVisibility(View.VISIBLE);
//                }
                myFriendAdapter.submitList(userModels);
            }
        });
    }

    public void removeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutChat);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
