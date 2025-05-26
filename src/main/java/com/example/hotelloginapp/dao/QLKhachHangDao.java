package com.example.hotelloginapp.dao;
import com.example.hotelloginapp.models.KhachHang;

import com.example.hotelloginapp.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class QLKhachHangDao {
    public static ObservableList<KhachHang> getAllKhachhang() {
        ObservableList<KhachHang> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM khachhang");
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getString("Sdt"),
                        rs.getString("Cccd")
                );
                list.add(kh);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static boolean isKhachHangExists(String maKH) {
        String sql = "SELECT 1 FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // tồn tại nếu có kết quả
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean insertKhachHang(KhachHang kh) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO KhachHang (MaKH, TenKH, Sdt, Cccd) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKH());
            ps.setString(3, kh.getSdt());
            ps.setString(4, kh.getCccd());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String getTenKhachHangByMa(String maKH) {
        String sql = "SELECT TenKH FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("TenKH");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}

