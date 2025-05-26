package com.example.hotelloginapp.models;

import java.math.BigDecimal;

public class DichVu {
    private String maDV;
    private String tenDV;
    private BigDecimal giaDV;

    public DichVu(String maDV, String tenDV, BigDecimal giaDV) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.giaDV = giaDV;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public BigDecimal getGiaDV() {
        return giaDV;
    }

    public void setGiaDV(BigDecimal giaDV) {
        this.giaDV = giaDV;
    }

    @Override
    public String toString() {
        return tenDV + " - " + giaDV + " VND";
    }



}
