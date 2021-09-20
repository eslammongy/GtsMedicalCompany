package gts.medical.gtsmedicalcompany.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    CustomDialog customDialog;
   AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFillXrayDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userID = Util.getUserID();
        String xrayPostID = getIntent().getStringExtra("PostID");
        String xrayPostName = getIntent().getStringExtra("PostProfileName");
        String xrayPostEmail = getIntent().getStringExtra("PostProfileEmail");
        String xrayPostContent = getIntent().getStringExtra("PostDesc");
        customDialog = new CustomDialog();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        checkFieldsValidation();
        binding.btnRequestXrayOffered.setOnClickListener(v -> {
            if (awesomeValidation.validate()){
                requestXrayOffer(userID , xrayPostID , xrayPostName , xrayPostEmail , xrayPostContent);
            }else {
                Util.displayToastMessage(FillXrayDetailsActivity.this , "خطأ في إرسال الطلب الخاص بك من قضلك تأكد من كتابة جميع البيانات" , Color.RED);
            }

        });
        binding.btnArrowToBack.setOnClickListener(v -> {
            Intent intent = new Intent(FillXrayDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        });
    }

    private void checkFieldsValidation(){

        awesomeValidation.addValidation(this , R.id.etUserName , RegexTemplate.NOT_EMPTY ,R.string.nameEmpty);
        awesomeValidation.addValidation(this , R.id.etUserAddress , RegexTemplate.NOT_EMPTY ,R.string.addressEmpty);
        awesomeValidation.addValidation(this , R.id.etUserPhoneNum , Patterns.PHONE ,R.string.phoneNotValied);
        awesomeValidation.addValidation(this , R.id.etUserEmail , Patterns.EMAIL_ADDRESS,R.string.emailInvalied);
        awesomeValidation.addValidation(this , R.id.etUserNationalID , RegexTemplate.NOT_EMPTY,R.string.nationalDEmpty);
        awesomeValidation.addValidation(this , R.id.etXrayType ,RegexTemplate.NOT_EMPTY,R.string.nameEmpty);
        awesomeValidation.addValidation(this , R.id.etMedicalAnalysisType ,RegexTemplate.NOT_EMPTY ,R.string.samePassword);
    }

    private void requestXrayOffer(String userID , String xrayPostID , String xrayPostName , String xrayPostEmail , String postDesc){
        customDialog.showingProgressDialog(this);
        String userName = Objects.requireNonNull(binding.etUserName.getText()).toString().trim();
        String userAddress = Objects.requireNonNull(binding.etUserAddress.getText()).toString().trim();
        String userPhone = Objects.requireNonNull(binding.etUserPhoneNum.getText()).toString().trim();
        String userNationalID = Objects.requireNonNull(binding.etUserNationalID.getText()).toString().trim();
        String userEmail = Objects.requireNonNull(binding.etUserEmail.getText()).toString().trim();
        String xrayType = Objects.requireNonNull(binding.etXrayType.getText()).toString().trim();
        String medicalAnalysisType = Objects.requireNonNull(binding.etMedicalAnalysisType.getText()).toString().trim();

        HashMap<String , String> userMap = new HashMap<>();

        userMap.put("userID" , userID);
        userMap.put("xrayPostID" , xrayPostID);
        userMap.put("xrayPostName" , xrayPostName);
        userMap.put("xrayPostEmail" , xrayPostEmail);
        userMap.put("xrayUserName" , userName);
        userMap.put("xrayUserAddress" , userAddress);
        userMap.put("xrayUserPhone" , userPhone);
        userMap.put("xrayUserNationalID" , userNationalID);
        userMap.put("xrayUserEmail" , userEmail);
        userMap.put("xrayPostContent" , postDesc);
        userMap.put("xrayType" , xrayType);
        userMap.put("xrayMedicalAnalysisType" , medicalAnalysisType);


        rootDB.push().setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                customDialog.customDialog.dismiss();
                Util.displayToastMessage(FillXrayDetailsActivity.this , "تم إرسال الطلب الخاص بك بنجاح" , Color.GREEN);
                Intent intent = new Intent(FillXrayDetailsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }else {
                Util.displayToastMessage(FillXrayDetailsActivity.this , "خطأ في إرسال الطلب الخاص بك من قضلك تأكد من اتصالك بالانترنت" , Color.RED);
            }
        });

    }
}