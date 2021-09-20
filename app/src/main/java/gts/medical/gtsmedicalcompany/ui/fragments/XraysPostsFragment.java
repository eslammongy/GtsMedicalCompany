package gts.medical.gtsmedicalcompany.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import gts.medical.gtsmedicalcompany.adapters.XrayPostAdapter;
import gts.medical.gtsmedicalcompany.databinding.FragmentXraysPostsBinding;
import gts.medical.gtsmedicalcompany.model.PostModel;

public class XraysPostsFragment extends Fragment {

    private FragmentXraysPostsBinding binding;
    public ArrayList<PostModel> postModels;
    public  XrayPostAdapter xrayPostAdapter;
    private RecyclerView recyclerView;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference rootDb = firebaseDatabase.getReference().child("Xraypost");

    public XraysPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentXraysPostsBinding.inflate(inflater, container, false);

        binding.rvXraysPosts.setHasFixedSize(true);
        binding.rvXraysPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.progressBar.setVisibility(View.VISIBLE);
        rootDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
                    postModels = new ArrayList<>();
                    postModels.add(postModel);
                }
                xrayPostAdapter = new XrayPostAdapter(postModels , getContext());
                binding.rvXraysPosts.setAdapter(xrayPostAdapter);
                binding.progressBar.setVisibility(View.GONE);
                xrayPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //** set search function **//
        binding.contactSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
               filter(s.toString());
            }
        });

        return binding.getRoot();

    }

    private void filter(String text) {
        ArrayList<PostModel> filteredList = new ArrayList<>();

        for (PostModel post : postModels) {
            if (post.getXrays_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(post);
            }
        }

        xrayPostAdapter.filterList(filteredList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}