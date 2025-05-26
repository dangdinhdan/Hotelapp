package com.example.hotelloginapp.dao;

import com.example.hotelloginapp.models.NhanVien;
import com.example.hotelloginapp.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;

public class QLNhanVienDao {

    public static ObservableList<NhanVien> getAllNhanVien() {
        ObservableList<NhanVien> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM NhanVien");

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("SDT"),
                        rs.getString("CCCD"),
                        rs.getString("TaiKhoan"),
                        rs.getString("MatKhau"),
                        rs.getString("PhanCap"),
                        rs.getString("GioiTinh")
                );
                list.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean insertNhanVien(NhanVien nv) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO NhanVien (MaNV, TenNV, CCCD, SDT, TaiKhoan, MatKhau, PhanCap, GioiTinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getCccd());
            ps.setString(4, nv.getSdt());
            ps.setString(5, nv.getTaiKhoan());
            ps.setString(6, nv.getMatKhau());
            ps.setString(7, nv.getPhanCap());
            ps.setString(8, nv.getGioiTinh());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateNhanVien(NhanVien nv) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE NhanVien SET TenNV=?, CCCD=?, SDT=?, TaiKhoan=?, MatKhau=?, PhanCap=?, GioiTinh=? WHERE MaNV=?";
            var ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getCccd());
            ps.setString(3, nv.getSdt());
            ps.setString(4, nv.getTaiKhoan());
            ps.setString(5, nv.getMatKhau());
            ps.setString(6, nv.getPhanCap());
            ps.setString(7, nv.getGioiTinh());
            ps.setInt(8, nv.getMaNV());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteNhanVien(int maNV) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
            var ps = conn.prepareStatement(sql);
            ps.setInt(1, maNV);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
