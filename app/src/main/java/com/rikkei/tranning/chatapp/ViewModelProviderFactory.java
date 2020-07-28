package com.rikkei.tranning.chatapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rikkei.tranning.chatapp.viewmodels.LoginViewModel;
import com.rikkei.tranning.chatapp.viewmodels.ProfileViewModel;
import com.rikkei.tranning.chatapp.viewmodels.SignUpViewModel;

public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {
    public ViewModelProviderFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SignUpViewModel.class)){
            return (T) new SignUpViewModel();
        }
        if(modelClass.isAssignableFrom(LoginViewModel.class)){
            return (T) new LoginViewModel();
        }
        if(modelClass.isAssignableFrom(ProfileViewModel.class)){
            return (T) new ProfileViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
