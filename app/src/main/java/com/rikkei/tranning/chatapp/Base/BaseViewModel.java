package com.rikkei.tranning.chatapp.Base;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public class BaseViewModel<N> extends ViewModel {
    private WeakReference<N> mNavigator;

    public BaseViewModel() {
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}
