package com.example.hotelloginapp.dao;


import com.example.hotelloginapp.models.Phong;
import com.example.hotelloginapp.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class PhongDao {
    public static ObservableList<Phong> getAllphong() {
        ObservableList<Phong> list = FXCollections.observableArrayList();


        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Phong");

                while (rs.next()) {
                    Phong p = new Phong(
                        rs.getString("MaP"),
                        rs.getString("KieuPhong"),
                        rs.getBigDecimal("GiaGio"),
                        rs.getString("TrangThai")
                    );
                    list.add(p);
                }

            } catch(SQLException e){
                e.printStackTrace();
        }

            return list;
    }

    public static boolean insertPhong(Phong phong) {
        String sql = "INSERT INTO Phong (MaP, KieuPhong, GiaGio, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phong.getMaPhong());
            ps.setString(2, phong.getKieuPhong());
            ps.setBigDecimal(3, phong.getGiaGio());
            ps.setString(4, phong.getTrangThai());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<String> getPhongTrong() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = "SELECT MaP FROM Phong WHERE TrangThai = N'Trống'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("MaP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void capNhatTrangThaiPhong(String maPhong, String trangThai) {
        String sql = "UPDATE Phong SET TrangThai = ? WHERE MaP = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThai);
            stmt.setString(2, maPhong);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


