package com.rikkei.tranning.chatapp.views.uis.profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.PopupMenu;
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
import com.rikkei.tranning.chatapp.base.BaseFragment;
import com.rikkei.tranning.chatapp.R;
import com.rikkei.tranning.chatapp.databinding.FragmentEditprofileBinding;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends BaseFragment<FragmentEditprofileBinding, EditProfileViewModel> {
    private static final int IMAGE_REQUEST = 1;
    int CAMERA_REQUEST = 123;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("profile");
    private Uri imageUri;
    String uriImage;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    final Calendar myCalendar = Calendar.getInstance();

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
        return ViewModelProviders.of(requireActivity()).get(EditProfileViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.ImageViewBack.setOnClickListener(v -> removeFragment());
        mViewDataBinding.ImageButtonCamera.setOnClickListener(v -> showMenu());
        mViewDataBinding.ButtonSave.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(uriImage)) {
                mViewModel.updateInfoUser("userImgUrl", uriImage);
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
            if (mViewModel.validatePhoneNumber(phone)){
                mViewModel.updateInfoUser("userName", name);
                mViewModel.updateInfoUser("userPhone", phone);
                mViewModel.updateInfoUser("userDateOfBirth", date);
                Toast.makeText(getContext(), R.string.txt_save_profile_success, Toast.LENGTH_SHORT).show();
                removeFragmentSave();
            }
            else {
                Toast.makeText(getContext(), R.string.txt_notification_phone_number, Toast.LENGTH_SHORT).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        mViewDataBinding.editDateOfBirthProfile.setOnClickListener(v -> {
            // TODO Auto-generated method stub
//            new
            DatePickerDialog datePicker = new DatePickerDialog(requireContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePicker.getDatePicker().setMaxDate(new Date().getTime());

            datePicker.show();

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
        mViewModel.getInfoUser();
        mViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), user -> {
            mViewDataBinding.editTextNameProfile.setText(user.getUserName());
            if (user.getUserPhone().equals("default")) {
                mViewDataBinding.editPhoneProfile.setText("");
            } else {
                mViewDataBinding.editPhoneProfile.setText(user.getUserPhone());
            }
            if (user.getUserDateOfBirth().equals("default")) {
                mViewDataBinding.editDateOfBirthProfile.setText("");
            } else {
                mViewDataBinding.editDateOfBirthProfile.setText(user.getUserDateOfBirth());
            }
            if (user.getUserImgUrl().equals("default")) {
                mViewDataBinding.CircleImageUserEdit.setImageResource(R.mipmap.ic_launcher);
            } else {
                Glide.with(requireContext()).load(user.getUserImgUrl()).circleCrop().into(mViewDataBinding.CircleImageUserEdit);
            }
        });
    }

    public void removeFragment() {
        Fragment fragment = getParentFragmentManager().findFragmentById(R.id.frameLayoutChat);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_left, R.anim.pop_exit_left);
        assert fragment != null;
        fragmentTransaction.remove(fragment).commit();
    }

    public void removeFragmentSave() {
        Fragment fragment = getParentFragmentManager().findFragmentById(R.id.frameLayoutChat);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        assert fragment != null;
        fragmentTransaction.remove(fragment).commit();
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
        progressDialog.setMessage("Uploading");
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
                    assert downloadUri != null;
                    String mUri = downloadUri.toString();
                    uriImage = mUri;
                    Glide.with(requireContext()).load(mUri).circleCrop().into(mViewDataBinding.CircleImageUserEdit);
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

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), mViewDataBinding.ImageButtonCamera);

        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_profile_image, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.camera:
                    takePhoto();
                    break;
                case R.id.choosePhoto:
                    openImage();
                    break;
            }
            return false;
        });

        popupMenu.show();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            assert bitmap != null;
            imageUri = getImageUri(requireContext(), bitmap);
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}