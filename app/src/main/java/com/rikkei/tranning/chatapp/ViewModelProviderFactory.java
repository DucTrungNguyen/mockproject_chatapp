package com.rikkei.tranning.chatapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rikkei.tranning.chatapp.views.adapters.RequestFriendAdapter;
import com.rikkei.tranning.chatapp.views.uis.friend.FriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.allfriends.AllFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.requestfriends.RequestFriendViewModel;
import com.rikkei.tranning.chatapp.views.uis.friend.searchfriends.SearchFriendViewModel;
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
        if(modelClass.isAssignableFrom(AllFriendViewModel.class)){
            return (T) new AllFriendViewModel();
        }
        if(modelClass.isAssignableFrom(FriendViewModel.class)){
            return (T) new FriendViewModel();
        }
        if(modelClass.isAssignableFrom(MyFriendViewModel.class)){
            return (T) new MyFriendViewModel();
        }
        if(modelClass.isAssignableFrom(ChatViewModel.class)){
            return (T) new ChatViewModel();
        }
        if(modelClass.isAssignableFrom(SearchFriendViewModel.class)){
            return (T) new SearchFriendViewModel();
        }
        if(modelClass.isAssignableFrom(RequestFriendViewModel.class)){
            return (T) new RequestFriendViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
