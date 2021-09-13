package gts.medical.gtsmedicalcompany.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityHomeBinding;
import gts.medical.gtsmedicalcompany.ui.fragments.UserAccountFragment;
import gts.medical.gtsmedicalcompany.ui.fragments.XrayFragment;

@SuppressLint("NonConstantResourceId")
public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        if (savedInstanceState == null) {
          getSupportFragmentManager().beginTransaction().replace(R.id.dashboardContainer, new XrayFragment()).commit();
          binding.bottomNavigationView.setSelectedItemId(R.id.tabLaboratories);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tabLaboratories:
                    getSelectedFragment(new XrayFragment());
                    break;
                case R.id.tabUserAccount:
                    getSelectedFragment(new UserAccountFragment());
                    break;

            }
            return true;
        });
    }

    private void getSelectedFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dashboardContainer, fragment).commit();
    }

}