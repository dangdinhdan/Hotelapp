package com.example.hotelloginapp.models;

import java.time.LocalDateTime;

public class DatPhong {
    private int maDP;
    private String maKH;
    private String maP;
    private int maNV;
    private LocalDateTime ngayThue;
    private LocalDateTime ngayTra;

    public DatPhong(int maDP, String maKH, String maP, int maNV, LocalDateTime ngayThue, LocalDateTime ngayTra) {
        this.maDP = maDP;
        this.maKH = maKH;
        this.maP = maP;
        this.maNV = maNV;
        this.ngayThue = ngayThue;
        this.ngayTra = ngayTra;
    }

    // Getter và Setter

    public int getMaDP() {
        return maDP;
    }

    public void setMaDP(int maDP) {
        this.maDP = maDP;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaP() {
        return maP;
    }

    public void setMaP(String maP) {
        this.maP = maP;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }


    public LocalDateTime getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(LocalDateTime ngayThue) {
        this.ngayThue = ngayThue;
    }

    public LocalDateTime getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDateTime ngayTra) {
        this.ngayTra = ngayTra;
    }
}
