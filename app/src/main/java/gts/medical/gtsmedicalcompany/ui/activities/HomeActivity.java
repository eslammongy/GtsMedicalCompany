package gts.medical.gtsmedicalcompany.ui.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.SearchView;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.ActivityHomeBinding;
import gts.medical.gtsmedicalcompany.ui.fragments.ConfirmUserRequestsFragment;
import gts.medical.gtsmedicalcompany.ui.fragments.UserAccountFragment;
import gts.medical.gtsmedicalcompany.ui.fragments.XraysPostsFragment;

@SuppressLint("NonConstantResourceId")
public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        if (savedInstanceState == null) {
          getSupportFragmentManager().beginTransaction().replace(R.id.dashboardContainer, new XraysPostsFragment()).commit();
          binding.bottomNavigationView.setSelectedItemId(R.id.tabLaboratories);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tabLaboratories:
                    getSelectedFragment(new XraysPostsFragment());
                    break;
                case R.id.tabUserAccount:
                    getSelectedFragment(new UserAccountFragment());
                    break;
                case R.id.tabConfirmedRequests:
                    getSelectedFragment(new ConfirmUserRequestsFragment());
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