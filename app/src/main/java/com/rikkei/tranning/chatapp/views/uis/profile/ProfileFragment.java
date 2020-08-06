package com.rikkei.tranning.chatapp.views.uis.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentProfileBinding;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.views.uis.SplashActivity;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel>  {
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
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.ImageButtonEditProfile.setOnClickListener(v -> replaceFragment());
        mViewDataBinding.RelativeLayoutLogout.setOnClickListener(v -> logout());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProfileViewModel.getInfoUser();
        mProfileViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel user) {
                mViewDataBinding.TextViewNameUser.setText(user.getUserName());
                mViewDataBinding.TextViewEmailUser.setText(user.getUserEmail());
                if (user.getUserImgUrl().equals("default")) {
                    mViewDataBinding.ImageViewImageUser.setImageResource(R.mipmap.ic_launcher);
                    mViewDataBinding.CircleImageViewUser.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getContext()).load(user.getUserImgUrl()).into(mViewDataBinding.ImageViewImageUser);
                    Glide.with(getContext()).load(user.getUserImgUrl()).circleCrop().into(mViewDataBinding.CircleImageViewUser);
                }
            }
        });
    }
    public void replaceFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        EditProfileFragment editProfile=new EditProfileFragment();
        fragmentTransaction.add(R.id.frameLayoutChat,editProfile,null);
        fragmentTransaction.commit();
    }
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
