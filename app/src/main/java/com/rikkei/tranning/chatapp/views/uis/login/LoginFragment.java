package com.rikkei.tranning.chatapp.views.uis.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.Base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.databinding.FragmentLoginBinding;
import com.rikkei.tranning.chatapp.services.models.LoginUserModel;
import com.rikkei.tranning.chatapp.views.uis.MainActivity;
import com.rikkei.tranning.chatapp.views.uis.signup.SignUpFragment;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {
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
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentLoginBinding = getViewDataBinding();
        String htmlcontentbutton="Chưa có tài khoản? <font color=\"#4356B4\"> Đăng ký ngay</font>";
        mFragmentLoginBinding.ButtonMoveRegister.setText(android.text.Html.fromHtml(htmlcontentbutton));
        eventEditText();
        mFragmentLoginBinding.ButtonMoveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoginViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<LoginUserModel>() {
            @Override
            public void onChanged(LoginUserModel loginUser) {
               if(loginUser.validateEmailPassword()==false){
                   Toast.makeText(getContext(), "Invalid Email Or Password!", Toast.LENGTH_SHORT).show();
               }
                else{
                    mLoginViewModel.loginFirebase();
                }
            }
        });
        mLoginViewModel.isOk.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getContext(), "Login success!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void eventEditText(){
        EditText[] editTexts=new EditText[]{mFragmentLoginBinding.editTextPassLogin,mFragmentLoginBinding.editTextEmailLogin};
        for(int i=0;i<editTexts.length;i++){
            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    setEnableButton();
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }
    public void replaceFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        SignUpFragment signUp=new SignUpFragment();
        fragmentTransaction.replace(R.id.FrameLayout,signUp,null);
        fragmentTransaction.commit();
    }
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
}