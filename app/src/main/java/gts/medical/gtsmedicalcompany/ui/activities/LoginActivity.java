package gts.medical.gtsmedicalcompany.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;
import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityLoginBinding;
import gts.medical.gtsmedicalcompany.utils.CustomDialog;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String userEmail;
    String userPassword;
    CustomDialog dialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        dialog = new CustomDialog();
        checkFieldsValidation();
        binding.btnUserCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnUserLogin.setOnClickListener(v -> {
           if (awesomeValidation.validate()){
               userSignIn();
           }else {
               Toast.makeText(this, "من فضللك قم بكتابة الايميل و كلمة السر الخاصه بك", Toast.LENGTH_SHORT).show();
           }
        });



    }

    private void checkFieldsValidation(){
        awesomeValidation.addValidation(this , R.id.etUserEmail , Patterns.EMAIL_ADDRESS,R.string.emailInvalied);
        awesomeValidation.addValidation(this , R.id.etUserPassword , ".{6,}",R.string.passwordInvalied);
    }

    private void userSignIn(){
        dialog.showingProgressDialog(this);
        userPassword = Objects.requireNonNull(binding.etUserPassword.getText()).toString().trim();
        userEmail = Objects.requireNonNull(binding.etUserEmail.getText()).toString().trim();
        firebaseAuth.signInWithEmailAndPassword(userEmail , userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                dialog.customDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, PolicyTermsActivity.class);
                startActivity(intent);
                finish();
            }else {
                dialog.customDialog.dismiss();
                Toast.makeText(this, "خطأ في تسجيل الدخول", Toast.LENGTH_SHORT).show();
            }

        });
    }




}