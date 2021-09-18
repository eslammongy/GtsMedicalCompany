package gts.medical.gtsmedicalcompany.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.constants.AppConstants;
import gts.medical.gtsmedicalcompany.databinding.ActivityRegisterBinding;
import gts.medical.gtsmedicalcompany.model.UserModel;
import gts.medical.gtsmedicalcompany.utils.CustomDialog;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private Uri imageUri;
    String userName;
    String userPhone;
    String image ;
    String userAddress;
    String userEmail;
    String userNationalID;
    String userPassword;
    String confirmPassword;
    CustomDialog dialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    private StorageReference firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        dialog = new CustomDialog();
        checkFieldsValidation();
        binding.btnArrowBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnUserSignUp.setOnClickListener(v -> {

            if (awesomeValidation.validate()){
                userReregistration();
            }else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageView.setOnClickListener(v -> {
            if (checkUserStoragePermission()) {
                pickUserImage();
            } else {
                requestStoragePermission();
            }
        });
    }

    private void checkFieldsValidation(){

        awesomeValidation.addValidation(this , R.id.etUserName , RegexTemplate.NOT_EMPTY ,R.string.nameEmpty);
        awesomeValidation.addValidation(this , R.id.etUserAddress , RegexTemplate.NOT_EMPTY ,R.string.addressEmpty);
        awesomeValidation.addValidation(this , R.id.etUserPhoneNum , Patterns.PHONE ,R.string.phoneNotValied);
        awesomeValidation.addValidation(this , R.id.etUserEmail , Patterns.EMAIL_ADDRESS,R.string.emailInvalied);
        awesomeValidation.addValidation(this , R.id.etUserNationalID , RegexTemplate.NOT_EMPTY,R.string.nationalDEmpty);
        awesomeValidation.addValidation(this , R.id.etUserPassword , ".{6,}",R.string.passwordInvalied);
        awesomeValidation.addValidation(this , R.id.etConfirmUserPassword , R.id.etUserPassword ,R.string.samePassword);
    }
    private void userReregistration() {
        dialog.showingProgressDialog(this);
        userPassword = Objects.requireNonNull(binding.etUserPassword.getText()).toString().trim();
        userEmail = Objects.requireNonNull(binding.etUserEmail.getText()).toString().trim();
        userName = Objects.requireNonNull(binding.etUserName.getText()).toString().trim();
        userAddress = Objects.requireNonNull(binding.etUserAddress.getText()).toString().trim();
        userPhone = Objects.requireNonNull(binding.etUserPhoneNum.getText()).toString().trim();
        userNationalID = Objects.requireNonNull(binding.etUserNationalID.getText()).toString().trim();
        confirmPassword = Objects.requireNonNull(binding.etConfirmUserPassword.getText()).toString().trim();
        firebaseStorage.child(firebaseAuth.getUid() + AppConstants.ProfileImagePath).putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> taskUri = taskSnapshot.getStorage().getDownloadUrl();
                    taskUri.addOnCompleteListener(uri -> {
                        image = uri.getResult().toString();
                        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        dialog.customDialog.dismiss();
                                        UserModel userModel = new UserModel(firebaseAuth.getUid(),
                                                userName,
                                                image,
                                                userPhone,
                                                userAddress,
                                                userEmail, userNationalID,
                                                userPassword,
                                                confirmPassword);
                                        databaseReference.child(firebaseAuth.getUid()).setValue(userModel);
                                        Intent intent = new Intent(RegisterActivity.this, PolicyTermsActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        dialog.customDialog.dismiss();
                                        Toast.makeText(this, "خطأ في انشاء الحساب الخاص بك من قضلك تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    });
                });
    }

    private boolean checkUserStoragePermission() {
        return ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(this).asBitmap().load(imageUri).into(binding.imageView);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void requestStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(
                this, permissions
                , 1000
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                pickUserImage();
            else Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickUserImage() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this);
    }

}