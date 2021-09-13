package gts.medical.gtsmedicalcompany.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnArrowBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnUserSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this , PolicyTermsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}