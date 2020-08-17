package com.rikkei.tranning.chatapp.views.uis.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.views.uis.SplashActivity;

import java.util.Objects;

public class DialogLogoutFragment extends DialogFragment {
    Button btnLogout, btnNo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_logout,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout=view.findViewById(R.id.buttonDialogLogout);
        btnNo=view.findViewById(R.id.buttonDialogNo);
        btnNo.setOnClickListener(view1 -> Objects.requireNonNull(getDialog()).dismiss());
        btnLogout.setOnClickListener(v -> logout());
    }
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        requireActivity().finish();
    }
}