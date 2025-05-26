package com.example.hotelloginapp.models;

import java.math.BigDecimal;

public class Phong {
    private String MaPhong;
    private String KieuPhong;
    private String TrangThai;
    private BigDecimal GiaGio;


    public Phong(String MaPhong, String KieuPhong, BigDecimal GiaGio, String TrangThai) {
        this.MaPhong = MaPhong;
        this.KieuPhong = KieuPhong;
        this.TrangThai = TrangThai;
        this.GiaGio = GiaGio;

    }
    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getKieuPhong() {
        return KieuPhong;
    }

    public void setKieuPhong(String kieuPhong) {
        KieuPhong = kieuPhong;
    }

    public String getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(String maPhong) {
        MaPhong = maPhong;
    }
    public BigDecimal getGiaGio() {
        return GiaGio;
    }
    public void setGiaGio(BigDecimal giaGio) {
        GiaGio = giaGio;
    }
}
