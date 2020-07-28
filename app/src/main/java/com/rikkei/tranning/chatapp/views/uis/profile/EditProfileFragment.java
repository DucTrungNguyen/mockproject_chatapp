package com.rikkei.tranning.chatapp.views.uis.profile;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rikkei.tranning.chatapp.BR;
import com.rikkei.tranning.chatapp.Base.BaseFragment;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.databinding.FragmentEditprofileBinding;
import com.rikkei.tranning.chatapp.services.network.Network;
import com.squareup.picasso.Picasso;
import static android.app.Activity.RESULT_OK;
public class EditProfileFragment extends BaseFragment<FragmentEditprofileBinding, EditProfileViewModel> implements EditProfileNavigator {
    FragmentEditprofileBinding mFragmentEditProfileBinding;
    EditProfileViewModel mEditProfileViewModel;
    private static final int IMAGE_REQUEST=1;
    StorageReference storageReference= FirebaseStorage.getInstance().getReference("uploads");
    private Uri imageUri;
    String uriImage;
    private StorageTask uploadTask;
    @Override
    public int getBindingVariable() {
        return BR.viewModelEditProfile;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_editprofile;
    }

    @Override
    public EditProfileViewModel getViewModel() {
        mEditProfileViewModel= ViewModelProviders.of(this, new ViewModelProviderFactory()).get(EditProfileViewModel.class);
        return mEditProfileViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditProfileViewModel.setNavigator(this);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentEditProfileBinding=getViewDataBinding();
        new Network().infoUserFromFirebase(new Network.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                mFragmentEditProfileBinding.editTextNameProfile.setText(user.getUserName());
                if(user.getUserPhone().equals("default")){
                    mFragmentEditProfileBinding.editPhoneProfile.setText("");
                }
                else{
                    mFragmentEditProfileBinding.editPhoneProfile.setText(user.getUserPhone());
                }
                if(user.getUserDateOfBirth().equals("default")){
                    mFragmentEditProfileBinding.editDateOfBirthProfile.setText("");
                }
                else {
                    mFragmentEditProfileBinding.editDateOfBirthProfile.setText(user.getUserDateOfBirth());
                }
                if(user.getUserImgUrl().equals("default")){
                    mFragmentEditProfileBinding.CircleImageUserEdit.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Picasso.with(getContext()).load(user.getUserImgUrl()).into(mFragmentEditProfileBinding.CircleImageUserEdit);
                }
            }
        });
    }
    @Override
    public void removeFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.FrameLayoutEditProfile);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    @Override
    public void updateInfoUser() {
        String name=mFragmentEditProfileBinding.editTextNameProfile.getText().toString().trim();
        String phone=mFragmentEditProfileBinding.editPhoneProfile.getText().toString().trim();
        String date=mFragmentEditProfileBinding.editDateOfBirthProfile.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            name="default";
        }
        if(TextUtils.isEmpty(phone)){
            phone="default";
        }
        if(TextUtils.isEmpty(date)){
            date="default";
        }
        new Network().updateInforFromFirebase("userName",name);
        new Network().updateInforFromFirebase("userPhone",phone);
        new Network().updateInforFromFirebase("userDateOfBirth",date);
        if(!TextUtils.isEmpty(uriImage)){
            new Network().updateInforFromFirebase("userImgUrl",uriImage);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private  void uploadImage(){
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        if(imageUri!=null){
            final  StorageReference fileReference=storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));
            uploadTask=fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        String mUri=downloadUri.toString();
                        uriImage=mUri;
                        Glide.with(getContext()).load(mUri).into(mFragmentEditProfileBinding.CircleImageUserEdit);
                        progressDialog.dismiss();
                    }
                    else{
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
        else{
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode== RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            if(uploadTask!=null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImage();
            }
        }
    }
}