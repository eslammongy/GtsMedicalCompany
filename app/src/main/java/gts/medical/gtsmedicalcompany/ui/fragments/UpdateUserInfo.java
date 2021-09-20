package gts.medical.gtsmedicalcompany.ui.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;
import java.util.Objects;
import gts.medical.gtsmedicalcompany.databinding.UpdateUserInfoDialogBinding;
import gts.medical.gtsmedicalcompany.ui.activities.FillXrayDetailsActivity;
import gts.medical.gtsmedicalcompany.utils.Util;

public class UpdateUserInfo extends DialogFragment {

    private UpdateUserInfoDialogBinding binding;
    String name;
    String email;
    String phone;
    String address;
    public UpdateUserInfo(String name, String email, String phone, String address){
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = UpdateUserInfoDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCurrentUserInfo();
        binding.btnExitDialog.setOnClickListener(v -> {
           Objects.requireNonNull(getDialog()).dismiss();
        });

        binding.btnUpdateInfo.setOnClickListener(v -> {
               updateUserInfo();

        });
    }

    private void displayCurrentUserInfo() {
        binding.tvUserName.setText(name);
        binding.tvUserEmail.setText(email);
        binding.tvUserAddress.setText(address);
        binding.tvUserPhone.setText(phone);

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void updateUserInfo(){
        String userName = Objects.requireNonNull(binding.tvUserName.getText()).toString();
        String userEmail = Objects.requireNonNull(binding.tvUserEmail.getText()).toString();
        String userPhone = Objects.requireNonNull(binding.tvUserPhone.getText()).toString();
        String userAddress = Objects.requireNonNull(binding.tvUserAddress.getText()).toString();
        if (userAddress.equals(address) && userEmail.equals(email) && userName.equals(name)&& userPhone.equals(phone)){
            Util.displayToastMessage(requireActivity() , "لم تقم بتعديل البيانات الخاصة بك" , Color.parseColor("#F3C34B"));
        }else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Util.getUserID());
            Map<String , Object> mapUser = Map.of("name", userName, "email", userEmail, "phone",userPhone, "address" ,userAddress);
            databaseReference.updateChildren(mapUser);
            Objects.requireNonNull(getDialog()).dismiss();
            Util.displayToastMessage(requireActivity() , "نم تعديل البيانات الخاصة بك" , Color.GREEN);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ViewGroup viewGroup = (ViewGroup) requireView().getParent();
        viewGroup.getLayoutParams().width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        viewGroup.getLayoutParams().height = (int) (getResources().getDisplayMetrics().heightPixels * 0.70);
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        viewGroup.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
