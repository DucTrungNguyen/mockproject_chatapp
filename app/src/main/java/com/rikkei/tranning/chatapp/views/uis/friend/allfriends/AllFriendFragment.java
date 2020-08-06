package com.rikkei.tranning.chatapp.views.uis.friend.allfriends;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentAllFriendsBinding;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.models.FriendsModel;
import com.rikkei.tranning.chatapp.views.adapters.AllFriendAdapter;
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel;

import java.util.ArrayList;

public class AllFriendFragment extends BaseFragment<FragmentAllFriendsBinding, SharedFriendViewModel> {
    FragmentAllFriendsBinding mFragmentAllFriendsBinding;
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
        mFragmentAllFriendsBinding = getViewDataBinding();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentAllFriendsBinding.RecyclerAllFriend.setLayoutManager(layoutManager);
        allFriendAdapter = new AllFriendAdapter(getContext());
        mFragmentAllFriendsBinding.RecyclerAllFriend.setAdapter(allFriendAdapter);
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
                ArrayList<AllUserModel> allUserModelsMid=new ArrayList<>();
                for(int i=0;i<allUserModels.size();i++){
                        allUserModelsMid.add(allUserModels.get(i));
                }
                if(allUserModelsMid.isEmpty()){
                    mFragmentAllFriendsBinding.ImageViewNoResultAllFriend.setVisibility(View.VISIBLE);
                }
                else{
                    mFragmentAllFriendsBinding.ImageViewNoResultAllFriend.setVisibility(View.GONE);
                }
                sharedFriendViewModel.collectionArray(allUserModelsMid);
                allFriendAdapter.submitList(allUserModelsMid);
            }
        });
    }
}
