package com.rikkei.tranning.chatapp.views.uis.friend.myfriends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentMyFriendsBinding;
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.views.adapters.AllFriendAdapter;
import com.rikkei.tranning.chatapp.views.adapters.MyFriendAdapter;
import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.message.ChatFragment;

import java.util.ArrayList;

public class MyFriendFragment extends BaseFragment<FragmentMyFriendsBinding, SharedFriendViewModel> {
    FragmentMyFriendsBinding mFragmentMyFriendsBinding;
    private SharedFriendViewModel sharedFriendViewModel;
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
    public SharedFriendViewModel getViewModel() {
        sharedFriendViewModel=ViewModelProviders.of(getActivity()).get(SharedFriendViewModel.class);
        //sharedFriendViewModel= new ViewModelProvider(requireActivity()).get(SharedFriendViewModel.class);
       // sharedFriendViewModel = ViewModelProviders.of(this, new ViewModelProviderFactory()).get(SharedFriendViewModel.class);
        return sharedFriendViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentMyFriendsBinding = getViewDataBinding();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myFriendAdapter = new MyFriendAdapter(getContext());
        mFragmentMyFriendsBinding.RecyclerMyFriend.setLayoutManager(layoutManager);
        mFragmentMyFriendsBinding.RecyclerMyFriend.setAdapter(myFriendAdapter);
        myFriendAdapter.setOnItemClickListener(new MyFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AllUserModel userModel) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChatFragment chatFragment = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("idUser", userModel.getUserId());
                chatFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.frameLayoutChat, chatFragment, null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedFriendViewModel.allUserListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<AllUserModel>>() {
            @Override
            public void onChanged(ArrayList<AllUserModel> allUserModels) {
                sharedFriendViewModel.collectionArray(allUserModels);
                ArrayList<AllUserModel> allUserModelsMid=new ArrayList<>();
                for(int i=0;i<allUserModels.size();i++){
                    if(allUserModels.get(i).getUserType().equals("friend")){
                        allUserModelsMid.add(allUserModels.get(i));
                    }
                }
                if(allUserModelsMid.isEmpty()){
                    mFragmentMyFriendsBinding.ImageViewNoResultMyFriend.setVisibility(View.VISIBLE);
                }
                else{
                    mFragmentMyFriendsBinding.ImageViewNoResultMyFriend.setVisibility(View.GONE);
                }
                myFriendAdapter.submitList(allUserModelsMid);
            }
        });
    }
}
