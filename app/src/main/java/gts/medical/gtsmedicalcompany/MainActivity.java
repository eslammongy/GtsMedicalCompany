package gts.medical.gtsmedicalcompany;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import gts.medical.gtsmedicalcompany.ui.activities.HomeActivity;
import gts.medical.gtsmedicalcompany.ui.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(() -> {
            if (firebaseAuth.getCurrentUser() == null){
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        }, 3000);

    }
}