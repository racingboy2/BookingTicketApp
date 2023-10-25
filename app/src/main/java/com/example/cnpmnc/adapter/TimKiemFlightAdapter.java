package com.example.cnpmnc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpmnc.R;
import com.example.cnpmnc.model.Firebase;
import com.example.cnpmnc.model.SanBay;

import java.util.ArrayList;


public class TimKiemFlightAdapter extends RecyclerView.Adapter<TimKiemFlightAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SanBay> sanBays;

    public TimKiemFlightAdapter(Context context, ArrayList<SanBay> sanBays) {
        this.context = context;
        this.sanBays = sanBays;
    }

    @NonNull
    @Override
    public TimKiemFlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muctimkiem, parent, false);
        return new TimKiemFlightAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimKiemFlightAdapter.ViewHolder holder, int position) {
        SanBay sanBay=sanBays.get(position);
        holder.tv_SanBay1.setText(sanBay.getTenSanBay());

    }

    @Override
    public int getItemCount() {
        return sanBays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_SanBay1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_SanBay1=itemView.findViewById(R.id.tv_SanBay1);
        }
    }
}