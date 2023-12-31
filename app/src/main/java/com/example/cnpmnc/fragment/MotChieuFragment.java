package com.example.cnpmnc.fragment;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnpmnc.R;
import com.example.cnpmnc.activity.ChonChuyenBayActivity;
import com.example.cnpmnc.activity.DangNhapActivity;

import com.example.cnpmnc.activity.TimKiemActivity;
import com.example.cnpmnc.model.ChuyenBay;
import com.example.cnpmnc.model.DiaDiem;
import com.example.cnpmnc.model.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MotChieuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotChieuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ChuyenBay chuyenBay;
    public MotChieuFragment() {
        // Required empty public constructor
    }
    public MotChieuFragment(ChuyenBay chuyenBay) {
        this.chuyenBay = chuyenBay;
    }

    public static MotChieuFragment newInstance(String param1, String param2) {
        MotChieuFragment fragment = new MotChieuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private TextView tv_CalendarNgayDi;
    int countNguoiLon=1;
    int countTreEm2_12Tuoi=0;
    int countTreEmDuoi2tuoi=0;
    private String NgayDi;
    private Button btnTimKiemMotChieu;
    private String currentDate;
    private LocalDate curdate;
    FirebaseUser firebaseUser;
    private static final int LOGIN_REQUEST_CODE = 123;


    private ImageButton btn_plus1MotChieu,btn_plus2MotChieu,btn_plus3MotChieu,btn_minus1MotChieu,btn_minus2MotChieu,btn_minus3MotChieu;
    private TextView tv_countNguoiLon,tv_countTreEm2_12T,tv_countTreEm2T,tv_idsanbaydiemdi,tv_tensanbaydiemdi,tv_idsanbaydiemden,tv_tensanbaydiemden;
    Firebase firebase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mot_chieu, container, false);
        Anhxa(view);
        Action();
        setdata();
        if(chuyenBay!=null)
        {
            Toast.makeText(getContext(), "Co chuyen bay", Toast.LENGTH_SHORT).show();
        }
        else if (DiaDiem.getInstance().getDiemDen()!=null) {
            Toast.makeText(getContext(), "Co diem den", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Khong co gi ca", Toast.LENGTH_SHORT).show();
        }


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_idsanbaydiemdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), TimKiemActivity.class);
                intent.putExtra("Timkiem", "diemdi");
                startActivity(intent);
            }
        });
        tv_idsanbaydiemden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), TimKiemActivity.class);
                intent.putExtra("Timkiem","diemden");
                startActivity(intent);
            }
        });
        tv_CalendarNgayDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarNgayDi();
            }
        });

        btnTimKiemMotChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser!=null)
                {
                    if(tv_tensanbaydiemdi==null||tv_idsanbaydiemden==null)
                    {
                        Toast.makeText(getContext(), "Khong co dia diem", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ThucHienHanhDong2();
                    }
                    Toast.makeText(getContext(), "Co user", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "ko co user", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Xác nhận");
                    builder.setMessage("Quý khách cần phải đăng nhập để thực hiện đặt chuyến bay?");


                    builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(getContext(), DangNhapActivity.class);
                            startActivityForResult(intent, LOGIN_REQUEST_CODE);
                        }
                    });

                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Thực hiện hành động khi người dùng chọn "Không" ở đây
                            dialog.dismiss(); // Dismiss dialog khi chọn "Không"
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                ThucHienHanhDong2();
            } else {
                Toast.makeText(getContext(), "Khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DiaDiem.getInstance().reset();
    }

    private void ThucHienHanhDong2()
    {

            DiaDiem.getInstance().setSoLuongNguoiLon(tv_countNguoiLon.getText().toString());
            DiaDiem.getInstance().setSoLuongTreEm2Ttoi12T(tv_countTreEm2_12T.getText().toString());
            DiaDiem.getInstance().setSoLuongTreEmDuoi2T(tv_countTreEm2T.getText().toString());
            DiaDiem.getInstance().setDiemDi(tv_tensanbaydiemdi.getText().toString());
            DiaDiem.getInstance().setDiemDen(tv_tensanbaydiemden.getText().toString());
            DiaDiem.getInstance().setNgayDi(tv_CalendarNgayDi.getText().toString());
            if (chuyenBay!=null)
            {
                Intent intent = new Intent(getContext(), ChonChuyenBayActivity.class);
                getContext().startActivity(intent);
            }
            if (DiaDiem.getInstance().getDiemDi()!=null||DiaDiem.getInstance().getDiemDen()!=null)
            {
                Intent intent = new Intent(getContext(), ChonChuyenBayActivity.class);
                getContext().startActivity(intent);
            }





    }

        private void setdata(){
            if (chuyenBay != null){
                
                String tenCanTimKiem=chuyenBay.getDiemDi();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Query query=db.collection("SanBay").whereEqualTo("TenSanBay",tenCanTimKiem);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Lấy id của document
                                String documentId = document.getId();
                                tv_idsanbaydiemdi.setText(documentId);
                                Log.d(TAG, "Document ID: " + documentId);
                            }
                        }else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });
                tv_tensanbaydiemdi.setText(chuyenBay.getDiemDi());

                firebase.getIdSanBayByTenSanBay(chuyenBay.getDiemDen(), new Firebase.getIdSanBayByTenSanBayCallback() {
                    @Override
                    public void onCallBack(String idSanBay1) {
                        tv_idsanbaydiemden.setText(idSanBay1);
                    }
                });
                tv_tensanbaydiemden.setText(chuyenBay.getDiemDen());
            }
            if (DiaDiem.getInstance().getDiemDi() != null){
                String diemdi = DiaDiem.getInstance().getDiemDi();
                tv_idsanbaydiemdi.setText(diemdi);
                firebase.getTenSanBayBySanBayId(diemdi, new Firebase.getTenSanBayBySanBayIdCallback() {
                    @Override
                    public void onCallback(String tensanbay) {
                        tv_tensanbaydiemdi.setText(tensanbay);
                    }
                });
            }
            if (DiaDiem.getInstance().getDiemDen() != null){
                String diemden = DiaDiem.getInstance().getDiemDen();
                tv_idsanbaydiemden.setText(diemden);
                firebase.getTenSanBayBySanBayId(diemden, new Firebase.getTenSanBayBySanBayIdCallback() {
                    @Override
                    public void onCallback(String tensanbay) {
                        tv_tensanbaydiemden.setText(tensanbay);
                    }
                });
            }
            
        }
    private void showCalendarNgayDi(){
        final Calendar c = Calendar.getInstance();
        long currentDateInMillis = c.getTimeInMillis();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog rentDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        NgayDi = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
                        LocalDate ngaydi = LocalDate.parse(NgayDi, formatter);
                        if (curdate.isAfter(ngaydi)){
                            Toast.makeText(getContext(), "Ngày thuê không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tv_CalendarNgayDi.setText(NgayDi);
                    }
                }, mYear, mMonth, mDay);
        rentDatePickerDialog.setTitle("Chọn ngày thuê");
        rentDatePickerDialog.getDatePicker().setMinDate(currentDateInMillis);
        rentDatePickerDialog.show();
    }
    private void updateCount(TextView text,int count) {
        text.setText(String.format("%02d", count));
    }

    private  void Action()
    {
        btn_minus1MotChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countNguoiLon>1)
                {
                    countNguoiLon--;
                    updateCount(tv_countNguoiLon,countNguoiLon);
                }

            }
        });
        btn_minus2MotChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTreEm2_12Tuoi>0)
                {
                    countTreEm2_12Tuoi--;
                    updateCount(tv_countTreEm2_12T,countTreEm2_12Tuoi);
                }

            }
        });
        btn_minus3MotChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTreEmDuoi2tuoi>0)
                {
                    countTreEmDuoi2tuoi--;
                    updateCount(tv_countTreEm2T,countTreEmDuoi2tuoi);
                }

            }
        });
        btn_plus1MotChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countNguoiLon<5)
                {
                    countNguoiLon++;
                    updateCount(tv_countNguoiLon,countNguoiLon);
                }else {
                    Toast.makeText(getContext(), "Số lượng hàng khách đã đạt tối đa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_plus2MotChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTreEm2_12Tuoi<4)
                {
                    countTreEm2_12Tuoi++;
                    updateCount(tv_countTreEm2_12T,countTreEm2_12Tuoi);
                }
                else {
                    Toast.makeText(getContext(), "Số lượng hàng khách đã đạt tối đa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_plus3MotChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTreEmDuoi2tuoi<4)
                {
                    countTreEmDuoi2tuoi++;
                    updateCount(tv_countTreEm2T,countTreEmDuoi2tuoi);
                }
                else {
                    Toast.makeText(getContext(), "Số lượng hàng khách đã đạt tối đa", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void Anhxa(View view) {
        btn_plus1MotChieu=view.findViewById(R.id.btn_plusMotChieu);
        btn_plus2MotChieu=view.findViewById(R.id.btn_plus2MotChieu);
        btn_plus3MotChieu=view.findViewById(R.id.btn_plus3MotChieu);
        btn_minus1MotChieu=view.findViewById(R.id.btn_minusMotChieu);
        btn_minus2MotChieu=view.findViewById(R.id.btn_minus2MotChieu);
        btn_minus3MotChieu=view.findViewById(R.id.btn_minus3MotChieu);
        tv_countNguoiLon=view.findViewById(R.id.tv_countNguoiLonMotChieu);
        tv_countTreEm2T=view.findViewById(R.id.tv_countTrEm2TMotChieu);
        tv_countTreEm2_12T=view.findViewById(R.id.tv_countTreEm2_12TMotChieu);
        tv_CalendarNgayDi=view.findViewById(R.id.tv_CalendarNgayDi);
        tv_idsanbaydiemdi=view.findViewById(R.id.tv_idsanbaydiemdi);
        tv_idsanbaydiemden=view.findViewById(R.id.tv_idsanbaydiemden);
        tv_tensanbaydiemdi=view.findViewById(R.id.tv_tensanbaydiemdi);
        btnTimKiemMotChieu=view.findViewById(R.id.btnTimKiemMotChieu);
        tv_tensanbaydiemden=view.findViewById(R.id.tv_tensanbaydiemden);
        firebase = new Firebase(getContext());
        currentDate = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
        curdate = LocalDate.parse(currentDate, formatter);
        tv_CalendarNgayDi.setText(currentDate);
    }
}