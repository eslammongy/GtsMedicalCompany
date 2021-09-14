package gts.medical.gtsmedicalcompany.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityFillXrayDetailsBinding;

public class FillXrayDetailsActivity extends AppCompatActivity {

    ActivityFillXrayDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFillXrayDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnArrowToBack.setOnClickListener(v -> {
            Intent intent = new Intent(FillXrayDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        });
    }
}