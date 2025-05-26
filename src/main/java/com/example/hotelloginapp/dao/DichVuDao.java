package com.example.hotelloginapp.dao;

import com.example.hotelloginapp.models.DichVu;
import com.example.hotelloginapp.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DichVuDao {
    public static ObservableList<DichVu> getAllDichVu() {
        ObservableList<DichVu> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM DichVu";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DichVu dv = new DichVu(
                        rs.getString("MaDV"),
                        rs.getString("TenDV"),
                        rs.getBigDecimal("GiaDV")
                );
                list.add(dv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ObservableList<String> getAllTenDichVu() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = "SELECT TenDV FROM DichVu";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("TenDV"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static boolean insertDichVu(DichVu dichvu) {
        String sql = "INSERT INTO DichVu (MaDV, TenDV, GiaDV) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dichvu.getMaDV());
            ps.setString(2, dichvu.getTenDV());
            ps.setBigDecimal(3, dichvu.getGiaDV());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean xoaDichVu(String maDV) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM DichVu WHERE MaDV = ?";
            var ps = conn.prepareStatement(sql);
            ps.setString(1, maDV);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static DichVu getDichVuByTen(String tenDV) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DichVu WHERE TenDV = ?")) {
            stmt.setString(1, tenDV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DichVu(
                        rs.getString("MaDV"),
                        rs.getString("TenDV"),
                        rs.getBigDecimal("GiaDV")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
