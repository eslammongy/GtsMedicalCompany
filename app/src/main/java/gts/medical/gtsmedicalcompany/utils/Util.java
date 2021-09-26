package gts.medical.gtsmedicalcompany.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import gts.medical.gtsmedicalcompany.ui.activities.FillXrayDetailsActivity;

public interface Util {

   static String getUserID(){
       FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
       return firebaseAuth.getUid();
   }

    static void displayToastMessage(Activity activity, String message , int toastColor){
        new StyleableToast.Builder(activity)
                .text(message)
                .textColor(Color.WHITE)
                .textBold()
                .cornerRadius(15)
                .backgroundColor(toastColor)
                .gravity(Gravity.TOP)
                .show();
    }

    static void pickUserImage(Activity activity) {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(activity);
    }

    static boolean checkUserStoragePermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    static void requestStoragePermission(Activity activity) {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(
                activity, permissions
                , 1000
        );
    }
}

