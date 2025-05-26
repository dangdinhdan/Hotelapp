package com.example.hotelloginapp.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SuDungDV {
    private int maSDDV;               // Mã sử dụng dịch vụ
    private LocalDateTime ngayThue;   // Ngày sử dụng
    private int soLuong;              // Số lượng dịch vụ sử dụng
    private String maDV;              // Mã dịch vụ
    private String tenDV;             // Tên dịch vụ (có thể null nếu không join)
    private BigDecimal giaDV;         // Giá dịch vụ 1 đơn vị (có thể null nếu không join)
    private int maDP;                 // Mã đặt phòng
    private BigDecimal tongTien;      // Tổng tiền (giaDV * soLuong)

    public SuDungDV() {
    }

    // Constructor đầy đủ, dùng khi có cả thông tin tên và giá dịch vụ
    public SuDungDV(int maSDDV, LocalDateTime ngayThue, int soLuong, String maDV, String tenDV,
                    BigDecimal giaDV, int maDP, BigDecimal tongTien) {
        this.maSDDV = maSDDV;
        this.ngayThue = ngayThue;
        this.soLuong = soLuong;
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.giaDV = giaDV;
        this.maDP = maDP;
        this.tongTien = tongTien;
    }

    // Constructor tối giản, dùng khi chỉ lấy dữ liệu từ bảng SuDungDV (không có tenDV, giaDV)
    public SuDungDV(int maSDDV, LocalDateTime ngayThue, int soLuong, String maDV, int maDP, BigDecimal tongTien) {
        this.maSDDV = maSDDV;
        this.ngayThue = ngayThue;
        this.soLuong = soLuong;
        this.maDV = maDV;
        this.maDP = maDP;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public int getMaSDDV() {
        return maSDDV;
    }

    public void setMaSDDV(int maSDDV) {
        this.maSDDV = maSDDV;
    }

    public LocalDateTime getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(LocalDateTime ngayThue) {
        this.ngayThue = ngayThue;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
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

    public int getMaDP() {
        return maDP;
    }

    public void setMaDP(int maDP) {
        this.maDP = maDP;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    // Phương thức tính tongTien tự động dựa trên giaDV và soLuong
    public void tinhTongTien() {
        if (giaDV != null && soLuong > 0) {
            this.tongTien = giaDV.multiply(BigDecimal.valueOf(soLuong));
        } else {
            this.tongTien = BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        return "SuDungDV{" +
                "maSDDV=" + maSDDV +
                ", ngayThue=" + ngayThue +
                ", soLuong=" + soLuong +
                ", maDV='" + maDV + '\'' +
                ", tenDV='" + tenDV + '\'' +
                ", giaDV=" + giaDV +
                ", maDP=" + maDP +
                ", tongTien=" + tongTien +
                '}';
    }
}
