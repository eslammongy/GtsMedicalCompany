package gts.medical.gtsmedicalcompany.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.muddzdev.styleabletoast.StyleableToast;

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
}

