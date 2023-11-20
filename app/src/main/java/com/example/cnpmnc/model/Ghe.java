package com.example.cnpmnc.model;

public class Ghe {
    private String idGhe;
    private String idChuyenBay;
    private Long soGhe;
    private Boolean state;

    public Long getSoGhe() {
        return soGhe;
    }

    public long setSoGhe(Long soGhe) {
        this.soGhe = soGhe;
        return 0;
    }

    public Ghe(String idGhe, String idChuyenBay, Long soGhe, Boolean state) {
        this.idGhe = idGhe;
        this.idChuyenBay = idChuyenBay;
        this.soGhe=soGhe;
        this.state = state;
    }

    public String getIdGhe() {
        return idGhe;
    }

    public void setIdGhe(String idGhe) {
        this.idGhe = idGhe;
    }

    public String getIdChuyenBay() {
        return idChuyenBay;
    }

    public void setIdChuyenBay(String idChuyenBay) {
        this.idChuyenBay = idChuyenBay;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}