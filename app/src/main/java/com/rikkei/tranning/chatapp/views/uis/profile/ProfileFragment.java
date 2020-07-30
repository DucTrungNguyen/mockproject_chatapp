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
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.Base.BaseFragment;
import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentProfileBinding;
import com.rikkei.tranning.chatapp.services.network.Network;
import com.rikkei.tranning.chatapp.views.uis.SplashActivity;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel>  {
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
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProfileBinding=getViewDataBinding();
        mFragmentProfileBinding.ImageButtonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });
        mFragmentProfileBinding.RelativeLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProfileViewModel.getInfoUser();
        mProfileViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
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
    public void replaceFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        EditProfileFragment editProfile=new EditProfileFragment();
        fragmentTransaction.replace(R.id.FrameLayoutEditProfile,editProfile,null);
        fragmentTransaction.commit();
    }
    public void logout() {
        Intent intent=new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
