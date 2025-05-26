package com.example.hotelloginapp.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HoaDonView {
    private int maHD;
    private int maDP;
    private String maKH;
    private String maNV;
    private LocalDate ngayThue;
    private LocalDate ngayTra;
    private BigDecimal tongTien;
    private LocalDateTime ngayLapHD;

    // Constructor
    public HoaDonView(int maHD, int maDP, String maKH, String maNV,
                      LocalDate ngayThue, LocalDate ngayTra,
                      BigDecimal tongTien, LocalDateTime ngayLapHD) {
        this.maHD = maHD;
        this.maDP = maDP;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayThue = ngayThue;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
        this.ngayLapHD = ngayLapHD;
    }

    // Getters
    public int getMaHD() { return maHD; }
    public int getMaDP() { return maDP; }
    public String getMaKH() { return maKH; }
    public String getMaNV() { return maNV; }
    public LocalDate getNgayThue() { return ngayThue; }
    public LocalDate getNgayTra() { return ngayTra; }
    public BigDecimal getTongTien() { return tongTien; }
    public LocalDateTime getNgayLapHD() { return ngayLapHD; }
}

