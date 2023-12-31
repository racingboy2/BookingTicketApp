package com.example.cnpmnc.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cnpmnc.R;
import com.example.cnpmnc.activity.ChiTietFlightActivity;
import com.example.cnpmnc.activity.DanhSachChuyenBayDaThich;
import com.example.cnpmnc.model.ChuyenBay;
import com.example.cnpmnc.model.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RcvCateFlightAdapter extends RecyclerView.Adapter<RcvCateFlightAdapter.ViewHolder>{

    private ArrayList<ChuyenBay> flightlist;
    private Context context;
    private Firebase firebase;
    private ArrayList<ChuyenBay> favoriteFlights = new ArrayList<>();

    public RcvCateFlightAdapter(ArrayList<ChuyenBay> flightlist, Context context) {
        this.flightlist = flightlist;
        this.context = context;
        if (favoriteFlights == null) {
            favoriteFlights = new ArrayList<>();
        }
    }

    public RcvCateFlightAdapter(Context context, ArrayList<ChuyenBay> flightlist, Firebase firebase) {
        this.flightlist = flightlist;
        this.context = context;
        this.firebase = firebase;
    }

    @Override
    public RcvCateFlightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcvflight, parent, false);
        return new RcvCateFlightAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvCateFlightAdapter.ViewHolder holder, int position) {
        ChuyenBay currentFlight = flightlist.get(position);
        holder.priceflight.setText(currentFlight.getGiaVe()+ " VNĐ");
        holder.nameflight.setText(currentFlight.getDiemDen());
        Glide.with(context).load(currentFlight.getHinhAnh()).into(holder.imageflight);
        holder.imageflight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Chuyển qua xem chi tiết", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, ChiTietFlightActivity.class);
                intent.putExtra("ThongTinChuyenBay",currentFlight);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return flightlist.size();
    }

        // set sự kiện khi click vào item tym
    public interface OnItemClickListener{
        void onItemClick(ChuyenBay chuyenBay);
        }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener (OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameflight, priceflight;
        ImageView imageflight;
        public ViewHolder(View itemView) {
            super(itemView);

            nameflight = itemView.findViewById(R.id.tv_nameflight);
            priceflight = itemView.findViewById(R.id.tv_priceflight);
            imageflight = itemView.findViewById(R.id.img_rcvflight);
        }
    }


}
