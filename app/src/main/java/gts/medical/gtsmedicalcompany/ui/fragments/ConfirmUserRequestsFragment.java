package gts.medical.gtsmedicalcompany.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.FragmentConfirmUserRequetsBinding;

public class ConfirmUserRequestsFragment extends Fragment {

    private FragmentConfirmUserRequetsBinding binding;

    public ConfirmUserRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmUserRequetsBinding.inflate(inflater , container , false);
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}