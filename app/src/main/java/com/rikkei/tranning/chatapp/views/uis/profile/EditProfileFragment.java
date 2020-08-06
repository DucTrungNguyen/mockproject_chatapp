package com.rikkei.tranning.chatapp.views.uis.profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
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
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.ViewModelProviderFactory;
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.databinding.FragmentEditprofileBinding;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends BaseFragment<FragmentEditprofileBinding, EditProfileViewModel>{
    EditProfileViewModel mEditProfileViewModel;
    private static final int IMAGE_REQUEST=1;
    StorageReference storageReference= FirebaseStorage.getInstance().getReference("uploads");
    private Uri imageUri;
    String uriImage;
    private StorageTask uploadTask;

    final Calendar myCalendar = Calendar.getInstance();

    //    EditText edittext= (EditText) findViewById(R.id.Birthday);
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
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.ImageViewBack.setOnClickListener(v -> removeFragment());
        mViewDataBinding.ImageButtonCamera.setOnClickListener(v -> openImage());
        mViewDataBinding.ButtonSave.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(uriImage)) {
                mEditProfileViewModel.updateInfoUser("userImgUrl", uriImage);
            }
            String name = mViewDataBinding.editTextNameProfile.getText().toString().trim();
            String phone = mViewDataBinding.editPhoneProfile.getText().toString().trim();
            String date = mViewDataBinding.editDateOfBirthProfile.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                name = "default";
            }
            if (TextUtils.isEmpty(phone)) {
                phone = "default";
            }
            if (TextUtils.isEmpty(date)) {
                date = "default";
            }
            mEditProfileViewModel.updateInfoUser("userName", name);
            mEditProfileViewModel.updateInfoUser("userPhone", phone);
            mEditProfileViewModel.updateInfoUser("userDateOfBirth", date);
            removeFragment();
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        mViewDataBinding.editDateOfBirthProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }
    private void updateLabel() {
        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mViewDataBinding.editDateOfBirthProfile.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditProfileViewModel.getInfoUser();
        mEditProfileViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel user) {
                mViewDataBinding.editTextNameProfile.setText(user.getUserName());
                if(user.getUserPhone().equals("default")){
                    mViewDataBinding.editPhoneProfile.setText("");
                }
                else{
                    mViewDataBinding.editPhoneProfile.setText(user.getUserPhone());
                }
                if(user.getUserDateOfBirth().equals("default")){
                    mViewDataBinding.editDateOfBirthProfile.setText("");
                }
                else {
                    mViewDataBinding.editDateOfBirthProfile.setText(user.getUserDateOfBirth());
                }
                if(user.getUserImgUrl().equals("default")){
                    mViewDataBinding.CircleImageUserEdit.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Picasso.with(getContext()).load(user.getUserImgUrl()).into(mViewDataBinding.CircleImageUserEdit);
                }
            }
        });
    }

    //    @Override
    public void removeFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.frameLayoutChat);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
    public void openImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
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
        if(imageUri!=null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();
                    uriImage = mUri;
                    Glide.with(getContext()).load(mUri).into(mViewDataBinding.CircleImageUserEdit);
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
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