package com.example.hotelloginapp.models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class KhachHang {
    private StringProperty maKH;
    private StringProperty tenKH;
    private StringProperty cccd;
    private StringProperty sdt;

    public KhachHang(String maKH, String tenKH, String sdt, String cccd) {
        this.maKH = new SimpleStringProperty(maKH);
        this.tenKH = new SimpleStringProperty(tenKH);
        this.cccd = new SimpleStringProperty(cccd);
        this.sdt = new SimpleStringProperty(sdt);
    }

    public StringProperty tenKHProperty() { return tenKH; }
    public StringProperty cccdProperty() { return cccd; }
    public StringProperty sdtProperty() { return sdt; }
    public StringProperty maKHProperty() { return maKH; }

    public String getMaKH() { return maKH.get(); }
    public void setMaKH(String maNV) {
        this.maKH.set(maNV);
    }
    public String getTenKH() { return tenKH.get(); }
    public void setTenKH(String tenNV) {
        this.tenKH.set(tenNV);
    }
    public String getCccd() { return cccd.get(); }
    public void setCccd(String cccd) {
        this.cccd.set(cccd);
    }
    public String getSdt() { return sdt.get(); }
    public void setSdt(String sdt) {
        this.sdt.set(sdt);
    }
}
