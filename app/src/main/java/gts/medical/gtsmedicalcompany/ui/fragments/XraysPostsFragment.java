package gts.medical.gtsmedicalcompany.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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

import gts.medical.gtsmedicalcompany.R;
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

        postModels = new ArrayList<>();
        xrayPostAdapter = new XrayPostAdapter(postModels , getContext());
        binding.rvXraysPosts.setHasFixedSize(true);
        binding.rvXraysPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvXraysPosts.setAdapter(xrayPostAdapter);
        binding.progressBar.setVisibility(View.VISIBLE);
        rootDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
                    postModels.add(postModel);
                }
                binding.progressBar.setVisibility(View.GONE);
                xrayPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();

    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getLayoutInflater().inflate(R.menu.search_menu , (ViewGroup) menu);
        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("أدخل إسم المعمل هنا..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               //recyclerView.fil
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}