package com.rikkei.tranning.chatapp.views.uis.profile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.databinding.FragmentProfileBinding;


public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {
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
        return ViewModelProviders.of(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.ImageButtonEditProfile.setOnClickListener(v -> replaceFragment());
        mViewDataBinding.RelativeLayoutLogout.setOnClickListener(view1 -> {
            DialogLogoutFragment dialog = new DialogLogoutFragment();
            dialog.show(getParentFragmentManager(), null);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getInfoUser();
        mViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), user -> {
            mViewDataBinding.TextViewNameUser.setText(user.getUserName());
            mViewDataBinding.TextViewEmailUser.setText(user.getUserEmail());
            if (user.getUserImgUrl().equals("default")) {
                mViewDataBinding.ImageViewImageUser.setImageResource(R.mipmap.ic_launcher);
                mViewDataBinding.CircleImageViewUser.setImageResource(R.mipmap.ic_launcher);
            } else {
                Glide.with(requireContext()).load(user.getUserImgUrl()).into(mViewDataBinding.ImageViewImageUser);
                Glide.with(requireContext()).load(user.getUserImgUrl()).circleCrop().into(mViewDataBinding.CircleImageViewUser);
            }
        });
    }

    public void replaceFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(R.id.frameLayoutChat, new EditProfileFragment(), null).commit();
        fragmentTransaction.addToBackStack(null);
    }

}
