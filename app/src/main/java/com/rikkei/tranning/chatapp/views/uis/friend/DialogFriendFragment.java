package com.rikkei.tranning.chatapp.views.uis.friend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rikkei.tranning.chatapp.R;

import java.util.Objects;

public class DialogFriendFragment extends DialogFragment {
    Button btnOk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_friend,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnOk=view.findViewById(R.id.buttonDialogFriendOk);
        btnOk.setOnClickListener(view1 -> Objects.requireNonNull(getDialog()).dismiss());
    }
}
