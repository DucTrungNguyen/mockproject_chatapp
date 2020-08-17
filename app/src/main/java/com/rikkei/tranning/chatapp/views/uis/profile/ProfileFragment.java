package com.rikkei.tranning.chatapp.views.uis.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.databinding.FragmentProfileBinding;

import java.util.Locale;


public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {
    Locale myLocale;
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
        mViewDataBinding.imageButtonChangeLanguage.setOnClickListener(v -> showMenu());
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
    private  void showMenu(){
        PopupMenu popupMenu=new PopupMenu(getActivity(),mViewDataBinding.imageButtonChangeLanguage);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_language,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.language_en:
                    myLocale=new Locale("en","US");
                    mViewDataBinding.TextViewLanguage.setText(R.string.txt_language_en);
                    break;
                case R.id.language_vi:
                    myLocale=new Locale("vi", "VN");
                    mViewDataBinding.TextViewLanguage.setText(R.string.txt_language_vi);
                    break;
            }
           // onChangeLanguage(myLocale);
            return false;
        });
        popupMenu.show();
    }
//    public  void onChangeLanguage(Locale locale){
//        DisplayMetrics displayMetrics= getContext().getResources().getDisplayMetrics();
//        Configuration configuration=new Configuration();
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
//            configuration.locale=locale;
//        }
//        else {
//            configuration.locale=locale;
//        }
//        getContext().getResources().updateConfiguration(configuration,displayMetrics);
//    }
//    public void onChangeLanguage(Locale locale){
//        Resources resources= MyAppOFFLINE.self().getResources();
//        Configuration configuration=resources.getConfiguration();
//        configuration.setLocale(locale);
//        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
//    }
}
