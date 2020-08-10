package com.rikkei.tranning.chatapp.views.uis.message;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentChatBinding;
import com.rikkei.tranning.chatapp.views.adapters.ChatAdapter;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatViewModel> {
    ChatAdapter chatAdapter;
    String id;
    private static final int IMAGE_REQUEST = 1;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("chat");
    private Uri imageUri;
    String uriImage;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    @Override
    public int getBindingVariable() {
        return BR.viewModelChat;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public ChatViewModel getViewModel() {
        return ViewModelProviders.of(requireActivity()).get(ChatViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.ImageButtonBackChat.setOnClickListener(v -> removeFragment());
        mViewDataBinding.imageButtonPhotoChat.setOnClickListener(view12 -> openImage());
        mViewDataBinding.imageButtonSend.setOnClickListener(v -> {
            Bundle bundle = getArguments();
            String iD = null;
            if (bundle != null) {
                iD = bundle.getString("idUser");
            }
            String message = mViewDataBinding.editTextMessage.getText().toString().trim();
            mViewModel.sendMessage(iD, message, "Text");
            mViewDataBinding.editTextMessage.setText("");
        });
        mViewDataBinding.imageButtonSend.setEnabled(false);
        mViewDataBinding.editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    mViewDataBinding.imageButtonSend.setEnabled(false);
                    mViewDataBinding.imageButtonSend.setImageResource(R.drawable.ic_send_unable);
                } else {
                    mViewDataBinding.imageButtonSend.setEnabled(true);
                    mViewDataBinding.imageButtonSend.setImageResource(R.drawable.ic_send);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("idUser");
        }
        mViewModel.getInfoUserChat(id);
        mViewModel.userChatLiveData.observe(getViewLifecycleOwner(), userModel -> {
            if (userModel.getUserImgUrl().equals("default")) {
                Glide.with(requireContext()).load(R.mipmap.ic_launcher).circleCrop().into(mViewDataBinding.imageViewTitleChat);
            } else {
                Glide.with(requireContext()).load(userModel.getUserImgUrl()).circleCrop().into(mViewDataBinding.imageViewTitleChat);
            }
            mViewDataBinding.textViewUserNameChat.setText(userModel.getUserName());
            mViewDataBinding.recyclerChat.setHasFixedSize(true);
            chatAdapter = new ChatAdapter(getContext(), userModel.getUserImgUrl());
            mViewDataBinding.recyclerChat.setAdapter(chatAdapter);
        });
        mViewModel.displayMessage(id);
        mViewModel.messageListLiveData.observe(getViewLifecycleOwner(), messageModels -> chatAdapter.submitList(messageModels));
    }

    public void removeFragment() {
        Fragment fragment = getParentFragmentManager().findFragmentById(R.id.frameLayoutChat);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        assert fragment != null;
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
    public void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Sending");
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();

            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();
                    uriImage = mUri;
                    mViewModel.sendMessage(id, mUri,"Image");
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}
