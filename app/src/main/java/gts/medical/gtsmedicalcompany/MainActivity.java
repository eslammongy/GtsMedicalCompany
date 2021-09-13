package gts.medical.gtsmedicalcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import gts.medical.gtsmedicalcompany.ui.activities.HomeActivity;
import gts.medical.gtsmedicalcompany.ui.activities.LoginActivity;
import gts.medical.gtsmedicalcompany.ui.activities.PolicyTermsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(() -> {
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

    }
}