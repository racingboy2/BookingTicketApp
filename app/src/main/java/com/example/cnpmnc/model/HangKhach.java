package com.example.cnpmnc.model;

public class HangKhach {
    private String HoTen;
    private String Tuoi;
    private String Type;
    private String CCCD;
    private String SDT;
    private String Email;

    public HangKhach(String type, String hoTen, String tuoi) {
        HoTen = hoTen;
        Tuoi = tuoi;
        Type=type;
    }

    public HangKhach(String type,String hoTen, String tuoi,  String cccd, String SDT, String email) {
        HoTen = hoTen;
        Tuoi = tuoi;
        Type = type;
        CCCD = cccd;
        this.SDT = SDT;
        Email = email;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getTuoi() {
        return Tuoi;
    }

    public void setTuoi(String tuoi) {
        Tuoi = tuoi;
    }
}
