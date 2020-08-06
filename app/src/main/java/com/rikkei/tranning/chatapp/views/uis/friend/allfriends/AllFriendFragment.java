package com.rikkei.tranning.chatapp.views.uis.friend.allfriends;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentAllFriendsBinding;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.views.adapters.AllFriendAdapter;
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel;

import java.util.ArrayList;

public class AllFriendFragment extends BaseFragment<FragmentAllFriendsBinding, SharedFriendViewModel> {
    private SharedFriendViewModel sharedFriendViewModel;
    public AllFriendAdapter allFriendAdapter;


    @Override
    public int getBindingVariable() {
        return BR.viewModelAllFriend;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all_friends;
    }

    @Override
    public SharedFriendViewModel getViewModel() {
        sharedFriendViewModel= ViewModelProviders.of(getActivity()).get(SharedFriendViewModel.class);
       // sharedFriendViewModel= new ViewModelProvider(requireActivity()).get(SharedFriendViewModel.class);
       // sharedFriendViewModel = ViewModelProviders.of(this, new ViewModelProviderFactory()).get(SharedFriendViewModel.class);
        return sharedFriendViewModel;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewDataBinding.RecyclerAllFriend.setLayoutManager(layoutManager);
        allFriendAdapter = new AllFriendAdapter(getContext());
        mViewDataBinding.RecyclerAllFriend.setAdapter(allFriendAdapter);
//        allFriendAdapter.setOnItemClickListener(new AllFriendAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(AllUserModel userModel, String t) {
//                switch (t) {
//                    case "Kết bạn":
//                        sharedFriendViewModel.createFriend(userModel);
//                        break;
//                    case "Hủy":
//                    case "Bạn bè":
//                        sharedFriendViewModel.deleteFriend(userModel);
//                        break;
//                    case "Đồng ý":
//                        sharedFriendViewModel.updateFriend(userModel);
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedFriendViewModel.allUserListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<AllUserModel>>() {
            @Override
            public void onChanged(ArrayList<AllUserModel> allUserModels) {
                ArrayList<AllUserModel> allUserModelsMid = new ArrayList<>(allUserModels);
                if(allUserModelsMid.isEmpty()){
                    mViewDataBinding.ImageViewNoResultAllFriend.setVisibility(View.VISIBLE);
                }
                else{
                    mViewDataBinding.ImageViewNoResultAllFriend.setVisibility(View.GONE);
                }
                sharedFriendViewModel.collectionArray(allUserModelsMid);
                allFriendAdapter.submitList(allUserModelsMid);
            }
        });
    }
}
