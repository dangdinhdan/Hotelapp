package com.example.hotelloginapp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
public class NhanVien {
    private IntegerProperty maNV;
    private StringProperty tenNV;
    private StringProperty cccd;
    private StringProperty sdt;
    private StringProperty taiKhoan;
    private StringProperty matKhau;
    private StringProperty phanCap;
    private StringProperty gioiTinh;

    public NhanVien(int maNV, String tenNV, String sdt, String cccd, String taiKhoan,
                    String matKhau, String phanCap, String gioiTinh) {
        this.maNV = new SimpleIntegerProperty(maNV);
        this.tenNV = new SimpleStringProperty(tenNV);
        this.cccd = new SimpleStringProperty(cccd);
        this.sdt = new SimpleStringProperty(sdt);
        this.taiKhoan = new SimpleStringProperty(taiKhoan);
        this.matKhau = new SimpleStringProperty(matKhau);
        this.phanCap = new SimpleStringProperty(phanCap);
        this.gioiTinh = new SimpleStringProperty(gioiTinh);
    }

    public NhanVien() {
        this.maNV = new SimpleIntegerProperty();
    }

    public IntegerProperty maNVProperty() { return maNV; }
    public StringProperty tenNVProperty() { return tenNV; }
    public StringProperty cccdProperty() { return cccd; }
    public StringProperty sdtProperty() { return sdt; }
    public StringProperty taiKhoanProperty() { return taiKhoan; }
    public StringProperty matKhauProperty() { return matKhau; }
    public StringProperty phanCapProperty() { return phanCap; }
    public StringProperty gioiTinhProperty() { return gioiTinh; }

    public int getMaNV() {
        return maNV.get();
    }
    public void setMaNV(int maNV) {
        this.maNV.set(maNV);
    }
    public String getTenNV() {
        return tenNV.get();
    }
    public void setTenNV(String tenNV) {
        this.tenNV.set(tenNV);
    }
    public String getCccd() {
        return cccd.get();
    }
    public void setCccd(String cccd) {
        this.cccd.set(cccd);
    }
    public String getSdt() {
        return sdt.get();
    }
    public void setSdt(String sdt) {
        this.sdt.set(sdt);
    }
    public String getTaiKhoan() {
        return taiKhoan.get();
    }
    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan.set(taiKhoan);
    }
    public String getMatKhau() {
        return matKhau.get();
    }
    public void setMatKhau(String matKhau) {
        this.matKhau.set(matKhau);
    }
    public String getPhanCap() {
        return phanCap.get();
    }
    public void setPhanCap(String phanCap) {
        this.phanCap.set(phanCap);
    }
    public String getGioiTinh() {
        return gioiTinh.get();
    }
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh.set(gioiTinh);
    }


}
