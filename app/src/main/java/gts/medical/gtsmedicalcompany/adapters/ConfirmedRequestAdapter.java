package gts.medical.gtsmedicalcompany.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gts.medical.gtsmedicalcompany.databinding.ConfirmedRequestsLayoutBinding;
import gts.medical.gtsmedicalcompany.model.ConfirmedRequests;

public class ConfirmedRequestAdapter extends RecyclerView.Adapter<ConfirmedRequestAdapter.ConfirmViewHolder>  {

    public ArrayList<ConfirmedRequests> modelObjectArrayList;
    public Context context;
    public ConfirmedRequestAdapter(ArrayList<ConfirmedRequests> modelObjectArrayList , Context context) {
        this.modelObjectArrayList = modelObjectArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConfirmViewHolder(ConfirmedRequestsLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmViewHolder holder, int position) {

        ConfirmedRequests model = modelObjectArrayList.get(position);
        holder.binding.tvConfirmedRequestName.setText(model.getXrayName());
        holder.binding.tvConfirmedRequestDate.setText(model.getDate());

    }

    @Override
    public int getItemCount() {
        return modelObjectArrayList.size();
    }


    static class ConfirmViewHolder extends RecyclerView.ViewHolder {
        private final ConfirmedRequestsLayoutBinding binding;

        public ConfirmViewHolder(ConfirmedRequestsLayoutBinding rowBinding) {
            super(rowBinding.getRoot());
            this.binding = rowBinding;
        }
    }

    public void filterList(ArrayList<ConfirmedRequests> filteredList) {
        modelObjectArrayList = filteredList;
        notifyDataSetChanged();
    }
}
