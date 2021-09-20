package gts.medical.gtsmedicalcompany.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import gts.medical.gtsmedicalcompany.R;

public class CustomDialog {

    public Dialog customDialog;

    public void showingProgressDialog(Context context){
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.progress_dialog_layout);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();

    }
}
