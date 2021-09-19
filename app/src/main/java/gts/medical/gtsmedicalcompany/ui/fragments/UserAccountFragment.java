package gts.medical.gtsmedicalcompany.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Map;
import java.util.Objects;

import gts.medical.gtsmedicalcompany.constants.AppConstants;
import gts.medical.gtsmedicalcompany.databinding.FragmentUserAccountBinding;
import gts.medical.gtsmedicalcompany.model.UserModel;
import gts.medical.gtsmedicalcompany.utils.CustomDialog;
import gts.medical.gtsmedicalcompany.utils.Util;

@RequiresApi(api = Build.VERSION_CODES.R)
public class UserAccountFragment extends Fragment {

    private FragmentUserAccountBinding binding;
    UserModel userModel;
    CustomDialog dialog;


    private final ActivityResultContract cropActivityResultContract = new ActivityResultContract<Object, Object>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Object input) {
            return CropImage.activity()
                    .setAspectRatio(16, 9)
                    .getIntent(requireContext());
        }

        @Override
        public Object parseResult(int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent intent) {
            return Objects.requireNonNull(CropImage.getActivityResult(intent)).getUri();
        }
    };
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    uploadUserImage(uri);
                }
            });

    public UserAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserAccountBinding.inflate(inflater, container, false);

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

            UpdateUserInfo updateUserInfo = new UpdateUserInfo(userModel.getName(), userModel.getEmail(), userModel.getPhone(), userModel.getAddress());
            updateUserInfo.show(requireActivity().getSupportFragmentManager(), "TAG");
        });

        binding.changeUserProfilePhoto.setOnClickListener(v -> {
            if (checkUserStoragePermission()) {
                mGetContent.launch("image/*");
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

    }

    private void fetchUserInfo() {
        binding.profileProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Util.getUserID());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
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

    private void updateUserImage(String imagePath) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Util.getUserID());
        Map<String, Object> map = Map.of("image", imagePath);

        databaseReference.updateChildren(map);
    }

    private void uploadUserImage(Uri imageUri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(FirebaseAuth.getInstance().getUid() + AppConstants.ProfileImagePath).putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnCompleteListener(uriTask -> {
                        if (uriTask.isSuccessful()) {
                            String imagePath = Objects.requireNonNull(uriTask.getResult()).toString();
                            updateUserImage(imagePath);
                        } else {
                            Toast.makeText(requireActivity(), Objects.requireNonNull(uriTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });
    }

    private boolean checkUserStoragePermission() {
        return ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    mGetContent.launch("image/*");
                } else {
                    requestStoragePermission();
                }
            });

    private void requestStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(
                requireActivity(), permissions
                , 1000
        );
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