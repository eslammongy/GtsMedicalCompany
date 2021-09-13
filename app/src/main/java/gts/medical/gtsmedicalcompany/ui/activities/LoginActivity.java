package gts.medical.gtsmedicalcompany.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnUserCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnUserLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
            startActivity(intent);
            finish();
        });


    }
}