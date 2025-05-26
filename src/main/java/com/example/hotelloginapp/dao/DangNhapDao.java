package com.example.hotelloginapp.dao;

import com.example.hotelloginapp.models.NhanVien;
import com.example.hotelloginapp.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhapDao {
    public static NhanVien login(String taiKhoan, String matKhau) {
        String sql = "SELECT * FROM NhanVien WHERE TaiKhoan = ? AND MatKhau = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taiKhoan);
            stmt.setString(2, matKhau);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NhanVien(
                        rs.getInt("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("SDT"),
                        rs.getString("CCCD"),
                        rs.getString("TaiKhoan"),
                        rs.getString("MatKhau"),
                        rs.getString("PhanCap"),
                        rs.getString("GioiTinh")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
