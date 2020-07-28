package com.rikkei.tranning.chatapp.views.uis;

import android.content.Intent;
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
import com.rikkei.tranning.chatapp.Navigator.LoginNavigator;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentLoginBinding;
import com.rikkei.tranning.chatapp.viewmodels.LoginViewModel;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> implements LoginNavigator {
    FragmentLoginBinding mFragmentLoginBinding;
    LoginViewModel mLoginViewModel;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel= ViewModelProviders.of(this, new ViewModelProviderFactory()).get(LoginViewModel.class);
        return mLoginViewModel;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentLoginBinding=getViewDataBinding();
        String htmlcontentbutton="Chưa có tài khoản? <font color=\"#4356B4\"> Đăng ký ngay</font>";
        mFragmentLoginBinding.ButtonMoveRegister.setText(android.text.Html.fromHtml(htmlcontentbutton));
    }

    @Override
    public void replaceFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        SignUpFragment signUp=new SignUpFragment();
        fragmentTransaction.replace(R.id.FrameLayout,signUp,null);
        fragmentTransaction.commit();
    }

    @Override
    public void login() {
        String email=mFragmentLoginBinding.editTextEmailLogin.getText().toString().trim();
        String pass=mFragmentLoginBinding.editTextPassLogin.getText().toString().trim();
        if(mLoginViewModel.validateEmailPassword(email,pass)){
            mLoginViewModel.loginFirebase(email,pass);
        }
        else{
            Toast.makeText(getContext(), "Invalid Email Or Password!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setEnableButton() {
        if(!TextUtils.isEmpty(mFragmentLoginBinding.editTextEmailLogin.getText().toString())
        && !TextUtils.isEmpty(mFragmentLoginBinding.editTextPassLogin.getText().toString())){
            mFragmentLoginBinding.ButtonLogin.setEnabled(true);
            mFragmentLoginBinding.ButtonLogin.setBackgroundResource(R.drawable.custom_button_enable);
        }
        else {
            mFragmentLoginBinding.ButtonLogin.setEnabled(false);
            mFragmentLoginBinding.ButtonLogin.setBackgroundResource(R.drawable.custom_button_login);
        }
    }

    @Override
    public void showMessageLogin(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveIntent() {
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}