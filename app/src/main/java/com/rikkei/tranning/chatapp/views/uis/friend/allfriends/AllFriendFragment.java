package com.rikkei.tranning.chatapp.views.uis.friend.allfriends;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.databinding.FragmentAllFriendsBinding;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.views.adapters.AllFriendAdapter;
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel;

import java.util.ArrayList;

public class AllFriendFragment extends BaseFragment<FragmentAllFriendsBinding, SharedFriendViewModel> {
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
        return ViewModelProviders.of(getActivity()).get(SharedFriendViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SimpleItemAnimator itemAnimator = (SimpleItemAnimator) mViewDataBinding.RecyclerAllFriend.getItemAnimator();
        assert itemAnimator != null;
        itemAnimator.setSupportsChangeAnimations(false);
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
        mViewModel.allUserListLiveData.observe(getViewLifecycleOwner(), allUserModels -> {
            ArrayList<AllUserModel> allUserModelsMid = new ArrayList<>(allUserModels);
            if (allUserModelsMid.isEmpty()) {
                mViewDataBinding.ImageViewNoResultAllFriend.setVisibility(View.VISIBLE);
            } else {
                mViewDataBinding.ImageViewNoResultAllFriend.setVisibility(View.GONE);
            }
            mViewModel.collectionArray(allUserModelsMid);
            allFriendAdapter.submitList(allUserModelsMid);
        });
    }
}
