package com.example.hotelloginapp.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDon {
    private int maHD;
    private int maDP;
    private LocalDateTime ngayLapHD;
    private BigDecimal tongTien;
    private LocalDateTime ngayTT;
    private String phuongThucTT;

    // Constructor mặc định (rỗng)
    public HoaDon() {
    }

    // Constructor đầy đủ
    public HoaDon(int maHD, int maDP, LocalDateTime ngayLapHD, BigDecimal tongTien, LocalDateTime ngayTT, String phuongThucTT) {
        this.maHD = maHD;
        this.maDP = maDP;
        this.ngayLapHD = ngayLapHD;
        this.tongTien = tongTien;
        this.ngayTT = ngayTT;
        this.phuongThucTT = phuongThucTT;
    }

    // Constructor khi thêm mới (chưa có maHD)
    public HoaDon(int maDP, LocalDateTime ngayLapHD, BigDecimal tongTien, LocalDateTime ngayTT, String phuongThucTT) {
        this.maDP = maDP;
        this.ngayLapHD = ngayLapHD;
        this.tongTien = tongTien;
        this.ngayTT = ngayTT;
        this.phuongThucTT = phuongThucTT;
    }

    // Getters và setters
    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaDP() {
        return maDP;
    }

    public void setMaDP(int maDP) {
        this.maDP = maDP;
    }

    public LocalDateTime getNgayLapHD() {
        return ngayLapHD;
    }

    public void setNgayLapHD(LocalDateTime ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public LocalDateTime getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(LocalDateTime ngayTT) {
        this.ngayTT = ngayTT;
    }

    public String getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }
}
