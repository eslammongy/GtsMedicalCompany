package gts.medical.gtsmedicalcompany.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.adapters.ConfirmedRequestAdapter;
import gts.medical.gtsmedicalcompany.adapters.XrayPostAdapter;
import gts.medical.gtsmedicalcompany.databinding.FragmentConfirmUserRequetsBinding;
import gts.medical.gtsmedicalcompany.databinding.FragmentXraysPostsBinding;
import gts.medical.gtsmedicalcompany.model.ConfirmedRequests;
import gts.medical.gtsmedicalcompany.model.PostModel;

public class ConfirmUserRequestsFragment extends Fragment {

    private FragmentConfirmUserRequetsBinding binding;
    public ArrayList<ConfirmedRequests> listOfOrdersConfirm;
    public ConfirmedRequestAdapter confirmedRequestAdapter;
    private RecyclerView recyclerView;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference rootDb = firebaseDatabase.getReference().child("OrderConfirm");
    public ConfirmUserRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmUserRequetsBinding.inflate(inflater , container , false);

        listOfOrdersConfirm = new ArrayList<>();
        confirmedRequestAdapter = new ConfirmedRequestAdapter(listOfOrdersConfirm , getActivity());
        binding.rvConfirmedRequests.setHasFixedSize(true);
        binding.rvConfirmedRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvConfirmedRequests.setAdapter(confirmedRequestAdapter);
        binding.progressBar.setVisibility(View.VISIBLE);
        rootDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ConfirmedRequests confirmedRequests = dataSnapshot.getValue(ConfirmedRequests.class);
                    listOfOrdersConfirm.add(confirmedRequests);
                }

                binding.progressBar.setVisibility(View.GONE);
                confirmedRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }

    private void setDisplayRecyclerView(){
        listOfOrdersConfirm = new ArrayList<>();
        confirmedRequestAdapter = new ConfirmedRequestAdapter(listOfOrdersConfirm , getActivity());
        binding.rvConfirmedRequests.setHasFixedSize(true);
        binding.rvConfirmedRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvConfirmedRequests.setAdapter(confirmedRequestAdapter);
        binding.progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}