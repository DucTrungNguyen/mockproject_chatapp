package com.rikkei.tranning.chatapp.views.uis.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.Base.BaseFragment;
import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentProfileBinding;
import com.rikkei.tranning.chatapp.services.network.Network;
import com.rikkei.tranning.chatapp.views.uis.SplashActivity;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> implements ProfileNavigator {
    FragmentProfileBinding mFragmentProfileBinding;
    ProfileViewModel mProfileViewModel;
    @Override
    public int getBindingVariable() {
        return BR.viewModelProfile;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        mProfileViewModel= ViewModelProviders.of(this, new ViewModelProviderFactory()).get(ProfileViewModel.class);
        return mProfileViewModel;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel.setNavigator(this);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProfileBinding=getViewDataBinding();
        new Network().infoUserFromFirebase(new Network.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                mFragmentProfileBinding.TextViewNameUser.setText(user.getUserName());
                mFragmentProfileBinding.TextViewEmailUser.setText(user.getUserEmail());
                if(user.getUserImgUrl().equals("default")){
                    mFragmentProfileBinding.ImageViewImageUser.setImageResource(R.mipmap.ic_launcher);
                    mFragmentProfileBinding.CircleImageViewUser.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Picasso.with(getContext()).load(user.getUserImgUrl()).into(mFragmentProfileBinding.ImageViewImageUser);
                    Picasso.with(getContext()).load(user.getUserImgUrl()).into(mFragmentProfileBinding.CircleImageViewUser);
                }
            }
        });
    }
    @Override
    public void replaceFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        EditProfileFragment editProfile=new EditProfileFragment();
        fragmentTransaction.replace(R.id.FrameLayoutEditProfile,editProfile,null);
        fragmentTransaction.commit();
    }
    @Override
    public void logout() {
        Intent intent=new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
