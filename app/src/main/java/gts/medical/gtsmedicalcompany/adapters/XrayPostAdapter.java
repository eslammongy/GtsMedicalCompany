package gts.medical.gtsmedicalcompany.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gts.medical.gtsmedicalcompany.R;
import gts.medical.gtsmedicalcompany.databinding.XrayRecyclerviewItemBinding;
import gts.medical.gtsmedicalcompany.model.PostModel;
import gts.medical.gtsmedicalcompany.ui.activities.FillXrayDetailsActivity;

public class XrayPostAdapter extends RecyclerView.Adapter<XrayPostAdapter.XraysViewHolder>  {

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
        holder.binding.tvPostName.setText(model.getXrays_name());
        holder.binding.tvPostDate.setText(model.getXrays_Date());
        holder.binding.tvPostDesc.setText(model.getContent_xrays());
        Glide.with(context).asBitmap().load(model.getBanner_profile()).placeholder(R.drawable.ic_image_place_holder).into(holder.binding.postDescImage);
        Glide.with(context).asBitmap().load(model.getXrayProfilePhoto()).placeholder(R.drawable.ic_image_place_holder).into(holder.binding.postUserImageView);
        holder.binding.btnRequestXray.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext() , FillXrayDetailsActivity.class);
            intent.putExtra("PostID" , model.getXray_Uid());
            intent.putExtra("PostProfileName" , model.getXrays_name());
            intent.putExtra("PostProfileEmail" , model.getXray_email());
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

    public void filterList(ArrayList<PostModel> filteredList) {
        modelObjectArrayList = filteredList;
        notifyDataSetChanged();
    }
}



