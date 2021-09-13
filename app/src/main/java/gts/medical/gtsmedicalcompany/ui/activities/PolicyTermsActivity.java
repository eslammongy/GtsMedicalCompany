package gts.medical.gtsmedicalcompany.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityPolicyTermsBinding;

public class PolicyTermsActivity extends AppCompatActivity {

    ActivityPolicyTermsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPolicyTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCompleteLogin.setOnClickListener(v -> {
            Intent intent = new Intent(PolicyTermsActivity.this , HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}