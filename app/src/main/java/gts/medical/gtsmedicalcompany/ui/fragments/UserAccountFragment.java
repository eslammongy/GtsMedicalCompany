package gts.medical.gtsmedicalcompany.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Map;
import java.util.Objects;

import gts.medical.gtsmedicalcompany.constants.AppConstants;
import gts.medical.gtsmedicalcompany.databinding.FragmentUserAccountBinding;
import gts.medical.gtsmedicalcompany.model.UserModel;
import gts.medical.gtsmedicalcompany.ui.activities.HomeActivity;
import gts.medical.gtsmedicalcompany.utils.CustomDialog;
import gts.medical.gtsmedicalcompany.utils.Util;

public class UserAccountFragment extends Fragment {

    private FragmentUserAccountBinding binding;
    UserModel userModel;
    CustomDialog dialog;

    public UserAccountFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentUserAccountBinding.inflate(inflater , container , false);

      return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchUserInfo();
        dialog = new CustomDialog();
        binding.btnShowingUpdateDialog.setOnClickListener(v -> {
                    binding.tvUserName.setText(userModel.getName());
                    binding.tvUserAddress.setText(userModel.getAddress());
                    binding.tvUserPhone.setText(userModel.getPhone());
                    binding.tvUserEmail.setText(userModel.getEmail());

          UpdateUserInfo updateUserInfo = new UpdateUserInfo(userModel.getName() , userModel.getEmail(), userModel.getPhone() , userModel.getAddress());
          updateUserInfo.show(requireActivity().getSupportFragmentManager() , "TAG");
        });

        binding.changeUserProfilePhoto.setOnClickListener(v -> {
            if (checkUserStoragePermission()) {
                pickUserImage();
            } else {
                requestStoragePermission();
            }
        });

    }

    private void fetchUserInfo(){
       binding.profileProgressBar.setVisibility(View.VISIBLE);
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Util.getUserID());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            userModel = snapshot.getValue(UserModel.class);
                            assert userModel != null;
                            Glide.with(requireContext()).asBitmap().load(userModel.getImage()).into(binding.userProfilePhoto);
                            binding.tvUserName.setText(userModel.getName());
                            binding.tvUserAddress.setText(userModel.getAddress());
                            binding.tvUserPhone.setText(userModel.getPhone());
                            binding.tvUserEmail.setText(userModel.getEmail());

                            binding.profileProgressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void updateUserImage(String imagePath){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Util.getUserID());
        Map<String, Object> map = Map.of("image",imagePath);

        databaseReference.updateChildren(map);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void uploadUserImage(Uri imageUri){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        storageReference.child(Util.getUserID() + AppConstants.ProfileImagePath).putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnCompleteListener(uriTask -> {
                        if (uriTask.isSuccessful()){
                            String imagePath =uriTask.getResult().toString();
                            updateUserImage(imagePath);
                        }else {
                            Toast.makeText(requireActivity(), uriTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });
    }

    private boolean checkUserStoragePermission() {
        return ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                 Uri imageUri = result.getUri();
                Glide.with(this).asBitmap().load(imageUri).into(binding.userProfilePhoto);
                uploadUserImage(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                System.out.print(error);
            }
        }
    }

    private void requestStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(
                requireActivity(), permissions
                , 1000
        );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                pickUserImage();
            else Toast.makeText(requireActivity(), "Storage permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickUserImage() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(requireActivity());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}