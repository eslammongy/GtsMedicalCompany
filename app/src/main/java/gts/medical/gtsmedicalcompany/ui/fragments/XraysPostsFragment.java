package gts.medical.gtsmedicalcompany.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.adapters.XrayPostAdapter;
import gts.medical.gtsmedicalcompany.databinding.FragmentXraysPostsBinding;
import gts.medical.gtsmedicalcompany.model.PostModel;

public class XraysPostsFragment extends Fragment {

    private FragmentXraysPostsBinding binding;
    public ArrayList<PostModel> postModels;
    public  XrayPostAdapter xrayPostAdapter;
    public XraysPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentXraysPostsBinding.inflate(inflater, container, false);

        xrayPostAdapter = new XrayPostAdapter(fillPostList() , getContext());
        binding.rvXraysPosts.setHasFixedSize(true);
        binding.rvXraysPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvXraysPosts.setAdapter(xrayPostAdapter);


        return binding.getRoot();

    }

    private ArrayList<PostModel> fillPostList() {
        postModels = new ArrayList<>();
        String name = String.valueOf(getResources().getString(R.string.doctorName));
        String time = String.valueOf(getResources().getString(R.string.postTime));
        String desc = String.valueOf(getResources().getString(R.string.postDesc));

        postModels.add(0, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(1, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(2, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(3, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(4, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(5, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(6, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(7, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(8, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        postModels.add(9, new PostModel(name, time, desc, R.drawable.user_image, R.drawable.post_image));
        return postModels;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}