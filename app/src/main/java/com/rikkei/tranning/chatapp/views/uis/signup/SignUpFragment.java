package com.rikkei.tranning.chatapp.views.uis.signup;

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
import com.rikkei.tranning.chatapp.databinding.FragmentSignupBinding;
import com.rikkei.tranning.chatapp.services.models.loginUser;
import com.rikkei.tranning.chatapp.views.uis.login.LoginFragment;

public class SignUpFragment extends BaseFragment<FragmentSignupBinding, SignUpViewModel> {
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
        mSignUpViewModel = ViewModelProviders.of(this, new ViewModelProviderFactory()).get(SignUpViewModel.class);
        return mSignUpViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSignUpBinding = getViewDataBinding();
        String htmlcontentcheckbox = "Tôi đồng ý với các<font color=\"#4356B4\"> chính sách</font> và <font color=\"#4356B4\"> điều khoản</font>";
        mFragmentSignUpBinding.checkboxRegister.setText(android.text.Html.fromHtml(htmlcontentcheckbox));
        String htmlcontentbutton = "Đã có tài khoản? <font color=\"#4356B4\"> Đăng nhập ngay</font>";
        mFragmentSignUpBinding.ButtonMoveLogin.setText(android.text.Html.fromHtml(htmlcontentbutton));
        eventEnableButton();
        mFragmentSignUpBinding.ButtonBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });
        mFragmentSignUpBinding.ButtonMoveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSignUpViewModel.signUpUserMutableLiveData.observe(getViewLifecycleOwner(), new Observer<loginUser>() {
            @Override
            public void onChanged(loginUser loginUser) {
                if (loginUser.validateEmailPassword() == false) {
                    Toast.makeText(getContext(), "Invalid Email Or Password!", Toast.LENGTH_SHORT).show();
                } else {
                    mSignUpViewModel.createUserFireBase();
                }
            }
        });
        mSignUpViewModel.signUpSuccess.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(getContext(), "Sign Up Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSignUpViewModel.userName.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (TextUtils.isEmpty(s)) {
                    mFragmentSignUpBinding.editTextNameRegister.setError("REQUIRED");
                }
            }
        });
    }

    public void eventEnableButton() {
        EditText[] editTexts = new EditText[]{mFragmentSignUpBinding.editTextNameRegister, mFragmentSignUpBinding.editTextEmailRegister, mFragmentSignUpBinding.editTextPassRegister};
        for (int i = 0; i < editTexts.length; i++) {
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
        mFragmentSignUpBinding.checkboxRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnableButton();
            }
        });
    }

    public void replaceFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment login = new LoginFragment();
        fragmentTransaction.replace(R.id.FrameLayout, login, null);
        fragmentTransaction.commit();
    }

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
}