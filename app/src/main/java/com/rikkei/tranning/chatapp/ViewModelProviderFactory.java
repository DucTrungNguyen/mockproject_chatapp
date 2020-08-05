package com.rikkei.tranning.chatapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rikkei.tranning.chatapp.views.uis.friend.SharedFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.requestfriends.RequestFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.login.LoginViewModel;
import com.rikkei.tranning.chatapp.views.uis.message.ChatViewModel;
import com.rikkei.tranning.chatapp.views.uis.profile.EditProfileViewModel;
import com.rikkei.tranning.chatapp.views.uis.profile.ProfileViewModel;
import com.rikkei.tranning.chatapp.views.uis.signup.SignUpViewModel;

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
        if(modelClass.isAssignableFrom(EditProfileViewModel.class)){
            return (T) new EditProfileViewModel();
        }
        if(modelClass.isAssignableFrom(ChatViewModel.class)){
            return (T) new ChatViewModel();
        }
        if(modelClass.isAssignableFrom(RequestFriendViewModel.class)){
            return (T) new RequestFriendViewModel();
        }
        if(modelClass.isAssignableFrom(SharedFriendViewModel.class)){
            return (T) new SharedFriendViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
