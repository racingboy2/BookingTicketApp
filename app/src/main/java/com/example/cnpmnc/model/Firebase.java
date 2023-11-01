package com.example.cnpmnc.model;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

//file cua anh Nhan,Duy dung vo chem/ ak.
public class Firebase {
    ProgressBar progressBarid;
    FirebaseFirestore mfirestore;
    FirebaseAuth mfirebaseAuth;
    FirebaseUser mfirebaseUser;
    FirebaseStorage mfirebaseStorage;
    StorageReference mstorageRef;
    Context mcontext;
    public Firebase(Context context) {
        mfirestore = FirebaseFirestore.getInstance();
        mfirebaseAuth = FirebaseAuth.getInstance();
        mfirebaseStorage = FirebaseStorage.getInstance();
        mstorageRef = mfirebaseStorage.getReference();
        this.mcontext = context;
    }

    public interface FirebaseCallback<T> {
        void onCallback(ArrayList<T> list);
    }
    public interface getTenSanBayBySanBayIdCallback {
        void onCallback(String tensanbay);
    }
    public interface getIdSanBayByTenSanBayCallback{
        void onCallBack(String idSanBay);
    }


    public void getAllFlightByDiemDi(String diemdi,FirebaseCallback<ChuyenBay> callback) {
        ArrayList<ChuyenBay> flightlist = new ArrayList<>();
        mfirestore.collection("ChuyenBay")
                .whereEqualTo("DiemDi", diemdi)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ChuyenBay chuyenBay = new ChuyenBay(document.getId(),
                                    document.getString("DiemDen"),
                                    document.getString("DiemDi"),
                                    document.getString("GioBatDau"),
                                    document.getString("HinhAnh"),
                                    document.getString("NgayDi"),
                                    document.getString("NgayVe"),
                                    document.getString("SoLuongGheTrong"),
                                    document.getString("SoLuongGheVipTrong"),
                                    document.getString("TrangThai"),
                                    document.getString("MoTa"),
                                    document.getString("MoTaDiemDap"));
                            flightlist.add(chuyenBay);
                        }
                        callback.onCallback(flightlist);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

    }
    public void getTenSanBayBySanBayId(String sanBayId, getTenSanBayBySanBayIdCallback callback) {
        mfirestore.collection("SanBay")
                .document(sanBayId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String tenSanBay = document.getString("TenSanBay");
                            callback.onCallback(tenSanBay);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.w(TAG, "Error getting document", task.getException());
                    }
                });
    }
    public void getIdSanBayByTenSanBay(String tenSanBay,getIdSanBayByTenSanBayCallback callback )
    {
        mfirestore.collection("SanBay")
                .document(tenSanBay)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot= task.getResult();
                        if(documentSnapshot.exists())
                        {
                            String idSanBay=documentSnapshot.getId();
                            callback.onCallBack(idSanBay);
                        }else {
                            Log.d(TAG,"No such document");
                        }

                    }else {
                        Log.w(TAG,"Error getting document",task.getException());
                    }
                });
    }
    public void getAllSanBay(FirebaseCallback<SanBay> callback) {
        ArrayList<SanBay> sanBayslist = new ArrayList<>();
        mfirestore.collection("SanBay")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            SanBay sanBay = new SanBay(document.getId(),
                                    document.getString("TenSanBay"));
                            sanBayslist.add(sanBay);
                        }
                        callback.onCallback(sanBayslist);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
    public void getAllFlight(FirebaseCallback<ChuyenBay> callback) {
        ArrayList<ChuyenBay> flightlist = new ArrayList<>();
        mfirestore.collection("ChuyenBay")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ChuyenBay chuyenBay = new ChuyenBay(document.getId(),
                                    document.getString("DiemDen"),
                                    document.getString("DiemDi"),
                                    document.getString("GioBatDau"),
                                    document.getString("HinhAnh"),
                                    document.getString("NgayDi"),
                                    document.getString("NgayVe"),
                                    document.getString("SoLuongGheTrong"),
                                    document.getString("SoLuongGheVipTrong"),
                                    document.getString("TrangThai"),
                                    document.getString("MoTa"),
                                    document.getString("MoTaDiemDap"));
                            flightlist.add(chuyenBay);
                        }
                        callback.onCallback(flightlist);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

    }
    public void getAllFlighttoCompare(String diemDi, String diemDen,FirebaseCallback<ChuyenBay> callback) {
        ArrayList<ChuyenBay> flightlist = new ArrayList<>();
        mfirestore.collection("ChuyenBay")
                .whereEqualTo("DiemDi",diemDi)
                .whereEqualTo("DiemDen",diemDen)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ChuyenBay chuyenBay = new ChuyenBay(document.getId(),
                                    document.getString("DiemDen"),
                                    document.getString("DiemDi"),
                                    document.getString("GioBatDau"),
                                    document.getString("HinhAnh"),
                                    document.getString("NgayDi"),
                                    document.getString("NgayVe"),
                                    document.getString("SoLuongGheTrong"),
                                    document.getString("SoLuongGheVipTrong"),
                                    document.getString("TrangThai"),
                                    document.getString("MoTa"),
                                    document.getString("MoTaDiemDap"));
                            flightlist.add(chuyenBay);
                        }
                        callback.onCallback(flightlist);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

    }
}
