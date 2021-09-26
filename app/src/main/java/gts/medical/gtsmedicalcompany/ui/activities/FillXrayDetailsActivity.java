package gts.medical.gtsmedicalcompany.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import java.util.HashMap;
import java.util.Objects;
import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityFillXrayDetailsBinding;
import gts.medical.gtsmedicalcompany.utils.CustomDialog;
import gts.medical.gtsmedicalcompany.utils.Util;

public class FillXrayDetailsActivity extends AppCompatActivity {

    ActivityFillXrayDetailsBinding binding;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference rootDB = firebaseDatabase.getReference().child("UserRequests");
    private StorageReference firebaseStorage;
    CustomDialog customDialog;
    AwesomeValidation awesomeValidation;
    String[] xraysItems = {"St Scan", "St Scan", "St Scan", "St Scan", "St Scan"};
    ArrayAdapter<String> xraysAdapterItems;
    String[] analysisItems = {"St Scan", "St Scan", "St Scan", "St Scan", "St Scan"};
    ArrayAdapter<String> analysisAdapterItems;
    private Uri imageUri;
    String image;
    String xrayPostID;
    String xrayPostName;
    String xrayPostEmail;
    String xrayPostContent;
    String userID = Util.getUserID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFillXrayDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fillInfoFromAdapter();
        setupXraysSpanner();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        customDialog = new CustomDialog();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        checkFieldsValidation();
        binding.btnRequestXrayOffered.setOnClickListener(v -> {
            if (awesomeValidation.validate() && imageUri != null) {
                requestXrayOffer(userID, xrayPostID, xrayPostName, xrayPostEmail, xrayPostContent);
            } else {
                Util.displayToastMessage(FillXrayDetailsActivity.this, "خطأ في إرسال الطلب الخاص بك من قضلك تأكد من كتابة جميع البيانات", Color.RED);
            }

        });

        binding.imageRequestOffered.setOnClickListener(v -> {
            if (Util.checkUserStoragePermission(FillXrayDetailsActivity.this)) {
                Util.pickUserImage(FillXrayDetailsActivity.this);
            } else {
                Util.requestStoragePermission(FillXrayDetailsActivity.this);
            }
        });

        binding.btnArrowToBack.setOnClickListener(v -> {
            Intent intent = new Intent(FillXrayDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        });
    }

    private void fillInfoFromAdapter() {
        xrayPostID = getIntent().getStringExtra("PostID");
        xrayPostName = getIntent().getStringExtra("PostProfileName");
        xrayPostEmail = getIntent().getStringExtra("PostProfileEmail");
        xrayPostContent = getIntent().getStringExtra("PostDesc");
    }

    private void setupXraysSpanner() {
        xraysAdapterItems = new ArrayAdapter<>(this, R.layout.xrays_type_layout, xraysItems);
        binding.xRayTypeAutoComplete.setAdapter(xraysAdapterItems);
        analysisAdapterItems = new ArrayAdapter<>(this, R.layout.xrays_type_layout, analysisItems);
        binding.analysisTypeAutoComplete.setAdapter(analysisAdapterItems);
    }

    private void checkFieldsValidation() {

        awesomeValidation.addValidation(this, R.id.etUserName, RegexTemplate.NOT_EMPTY, R.string.nameEmpty);
        awesomeValidation.addValidation(this, R.id.etUserAddress, RegexTemplate.NOT_EMPTY, R.string.addressEmpty);
        awesomeValidation.addValidation(this, R.id.etUserPhoneNum, Patterns.PHONE, R.string.phoneNotValied);
        awesomeValidation.addValidation(this, R.id.etUserEmail, Patterns.EMAIL_ADDRESS, R.string.emailInvalied);
        awesomeValidation.addValidation(this, R.id.etUserNationalID, RegexTemplate.NOT_EMPTY, R.string.nationalDEmpty);
        awesomeValidation.addValidation(this, R.id.xRayTypeAutoComplete, RegexTemplate.NOT_EMPTY, R.string.nameEmpty);
        awesomeValidation.addValidation(this, R.id.analysisTypeAutoComplete, RegexTemplate.NOT_EMPTY, R.string.samePassword);
    }

    private void requestXrayOffer(String userID, String xrayPostID, String xrayPostName, String xrayPostEmail, String postDesc) {
        customDialog.showingProgressDialog(this);
        String userName = Objects.requireNonNull(binding.etUserName.getText()).toString().trim();
        String userAddress = Objects.requireNonNull(binding.etUserAddress.getText()).toString().trim();
        String userPhone = Objects.requireNonNull(binding.etUserPhoneNum.getText()).toString().trim();
        String userEmail = Objects.requireNonNull(binding.etUserEmail.getText()).toString().trim();
        String xrayType = Objects.requireNonNull(binding.xRayTypeAutoComplete.getText()).toString().trim();
        String medicalAnalysisType = Objects.requireNonNull(binding.analysisTypeAutoComplete.getText()).toString().trim();

        HashMap<String, String> userMap = new HashMap<>();

        firebaseStorage.child("UsersRequestsImage").child(userName + "/requestImage").putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> taskUri = taskSnapshot.getStorage().getDownloadUrl();
                    taskUri.addOnCompleteListener(task -> {
                        image = Objects.requireNonNull(task.getResult()).toString();
                        if (task.isSuccessful()) {
                            customDialog.customDialog.dismiss();
                            userMap.put("userID", userID);
                            userMap.put("xrayPostID", xrayPostID);
                            userMap.put("xrayPostName", xrayPostName);
                            userMap.put("xrayPostEmail", xrayPostEmail);
                            userMap.put("xrayUserName", userName);
                            userMap.put("xrayUserAddress", userAddress);
                            userMap.put("xrayUserPhone", userPhone);
                            userMap.put("xrayUserEmail", userEmail);
                            userMap.put("xrayPostContent", postDesc);
                            userMap.put("xrayImage", image);
                            userMap.put("xrayType", xrayType);
                            userMap.put("xrayMedicalAnalysisType", medicalAnalysisType);
                            rootDB.push().setValue(userMap).addOnCompleteListener(it -> {
                                if (it.isSuccessful()) {
                                    customDialog.customDialog.dismiss();
                                    Util.displayToastMessage(FillXrayDetailsActivity.this, "تم إرسال الطلب الخاص بك بنجاح", Color.GREEN);
                                    Intent intent = new Intent(FillXrayDetailsActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Util.displayToastMessage(FillXrayDetailsActivity.this, "خطأ في إرسال الطلب الخاص بك من قضلك تأكد من اتصالك بالانترنت", Color.RED);
                                }
                            });
                        }
                    });
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(this).asBitmap().load(imageUri).into(binding.imageRequestOffered);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                String error = result.getError().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Util.pickUserImage(this);
            else Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
        }
    }

}