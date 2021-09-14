package gts.medical.gtsmedicalcompany.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gts.medical.gtsmedicalcompany.databinding.XrayRecyclerviewItemBinding;
import gts.medical.gtsmedicalcompany.model.PostModel;
import gts.medical.gtsmedicalcompany.ui.activities.FillXrayDetailsActivity;

public class XrayPostAdapter extends RecyclerView.Adapter<XrayPostAdapter.XraysViewHolder>{

    public ArrayList<PostModel> modelObjectArrayList;
    public Context context;
    public XrayPostAdapter(ArrayList<PostModel> modelObjectArrayList , Context context) {
        this.modelObjectArrayList = modelObjectArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public XraysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XraysViewHolder(XrayRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull XraysViewHolder holder, int position) {

        PostModel model = modelObjectArrayList.get(position);
        holder.binding.tvPostName.setText(model.getUserName());
        holder.binding.tvPostDate.setText(model.getPostDate());
        holder.binding.tvPostDesc.setText(model.getPostDesc());
        holder.binding.postDescImage.setImageResource(model.getPostImage());
        holder.binding.postUserImageView.setImageResource(model.getUserImage());
        holder.binding.btnRequestXray.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext() , FillXrayDetailsActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
        });

    }

    @Override
    public int getItemCount() {
        return modelObjectArrayList.size();
    }

    static class XraysViewHolder extends RecyclerView.ViewHolder {
        private final XrayRecyclerviewItemBinding binding;

        public XraysViewHolder(XrayRecyclerviewItemBinding rowBinding) {
            super(rowBinding.getRoot());
            this.binding = rowBinding;
        }
    }
}



