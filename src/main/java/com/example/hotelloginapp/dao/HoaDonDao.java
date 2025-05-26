package com.example.hotelloginapp.dao;

import com.example.hotelloginapp.models.HoaDon;
import com.example.hotelloginapp.models.HoaDonView;
import com.example.hotelloginapp.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class HoaDonDao {
    public static ObservableList<HoaDonView> getHoaDon() {
        ObservableList<HoaDonView> danhSach = FXCollections.observableArrayList();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT hd.MaHD, dp.MaDP, kh.MaKH, nv.MaNV, dp.NgayThue, dp.NgayTra, hd.TongTien, hd.NgayLapHD FROM HoaDon hd JOIN DatPhong dp ON hd.MaDP = dp.MaDP JOIN KhachHang kh ON dp.MaKH = kh.MaKH LEFT JOIN NhanVien nv ON dp.MaNV = nv.MaNV");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HoaDonView hd = new HoaDonView(
                        rs.getInt("MaHD"),
                        rs.getInt("MaDP"),
                        rs.getString("MaKH"),
                        rs.getString("MaNV"),
                        rs.getDate("NgayThue").toLocalDate(),
                        rs.getDate("NgayTra").toLocalDate(),
                        rs.getBigDecimal("TongTien"),
                        rs.getTimestamp("NgayLapHD").toLocalDateTime()
                );
                danhSach.add(hd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }


    public static boolean insertHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (MaDP, NgayLapHD, TongTien, NgayTT, PhuongThucTT) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, hd.getMaDP());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(hd.getNgayLapHD()));
            preparedStatement.setBigDecimal(3, hd.getTongTien());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(hd.getNgayTT()));
            preparedStatement.setString(5, hd.getPhuongThucTT());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static HoaDon getHoaDonByMaDP(int maDP) {
        String sql = "SELECT * FROM HoaDon WHERE MaDP = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDP);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new HoaDon(
                        rs.getInt("MaHD"),
                        rs.getInt("MaDP"),
                        rs.getTimestamp("NgayLapHD").toLocalDateTime(),
                        rs.getBigDecimal("TongTien"),
                        rs.getTimestamp("NgayTT").toLocalDateTime(),
                        rs.getString("PhuongThucTT")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}