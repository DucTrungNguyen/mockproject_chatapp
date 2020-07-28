package com.rikkei.tranning.chatapp.views.uis;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.Base.BaseFragment;
import com.rikkei.tranning.chatapp.Navigator.SignUpNavigator;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentSignupBinding;
import com.rikkei.tranning.chatapp.viewmodels.SignUpViewModel;

public class SignUpFragment extends BaseFragment<FragmentSignupBinding, SignUpViewModel> implements SignUpNavigator {
    private FragmentSignupBinding mFragmentSignUpBinding;
    private SignUpViewModel mSignUpViewModel;
    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getBindingVariable() {
        return BR.viewModels;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_signup;
    }

    @Override
    public SignUpViewModel getViewModel() {
        mSignUpViewModel= ViewModelProviders.of(this, new ViewModelProviderFactory()).get(SignUpViewModel.class);
        return mSignUpViewModel;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignUpViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSignUpBinding=getViewDataBinding();
        String htmlcontentcheckbox="Tôi đồng ý với các<font color=\"#4356B4\"> chính sách</font> và <font color=\"#4356B4\"> điều khoản</font>";
        mFragmentSignUpBinding.checkboxRegister.setText(android.text.Html.fromHtml(htmlcontentcheckbox));
        String htmlcontentbutton="Đã có tài khoản? <font color=\"#4356B4\"> Đăng nhập ngay</font>";
        mFragmentSignUpBinding.ButtonMoveLogin.setText(android.text.Html.fromHtml(htmlcontentbutton));
    }

    @Override
    public void replaceFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        LoginFragment login=new LoginFragment();
        fragmentTransaction.replace(R.id.FrameLayout,login,null);
        fragmentTransaction.commit();
    }

    @Override
    public void signup() {
        String name=mFragmentSignUpBinding.editTextNameRegister.getText().toString().trim();
        String email=mFragmentSignUpBinding.editTextEmailRegister.getText().toString().trim();
        String password=mFragmentSignUpBinding.editTextPassRegister.getText().toString().trim();
        if(mSignUpViewModel.validateEmailPassword(email,password)){
            mSignUpViewModel.createUserFireBase(email,password,name);
           // Toast.makeText(getContext(), "sucess", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(), "Invalid email or password!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setEnableButton() {
        if (mFragmentSignUpBinding.checkboxRegister.isChecked()
                && !TextUtils.isEmpty(mFragmentSignUpBinding.editTextNameRegister.getText().toString())
                && !TextUtils.isEmpty(mFragmentSignUpBinding.editTextEmailRegister.getText().toString())
                && !TextUtils.isEmpty(mFragmentSignUpBinding.editTextPassRegister.getText().toString())) {
            mFragmentSignUpBinding.ButtonRegister.setEnabled(true);
            mFragmentSignUpBinding.ButtonRegister.setBackgroundResource(R.drawable.custom_button_enable);
        } else {
            mFragmentSignUpBinding.ButtonRegister.setEnabled(false);
            mFragmentSignUpBinding.ButtonRegister.setBackgroundResource(R.drawable.custom_button_login);
        }
    }

    @Override
    public void requireName(String message) {
        message="REQUIRE";
        mFragmentSignUpBinding.editTextNameRegister.setError(message);
    }

    @Override
    public void showMessageSignUp(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}