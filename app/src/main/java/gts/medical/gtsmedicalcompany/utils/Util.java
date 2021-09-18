package gts.medical.gtsmedicalcompany.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface Util {

   public static String getUserID(){
       FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
       return firebaseAuth.getUid();
   }
}
