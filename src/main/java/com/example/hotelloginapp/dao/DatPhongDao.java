package com.example.hotelloginapp.dao;

import com.example.hotelloginapp.models.DatPhong;
import com.example.hotelloginapp.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class DatPhongDao {

    public static ObservableList<DatPhong> getAllDatPhong() {
        ObservableList<DatPhong> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM DatPhong";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DatPhong dp = new DatPhong(
                        rs.getInt("MaDP"),
                        rs.getString("MaKH"),
                        rs.getString("MaP"),
                        rs.getInt("MaNV"),
                        rs.getTimestamp("NgayThue").toLocalDateTime(),  // ✅ chuyển đổi
                        rs.getTimestamp("NgayTra") != null ? rs.getTimestamp("NgayTra").toLocalDateTime() : null  // ✅ kiểm tra null
                );
                list.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Chỉ insert thông tin đặt phòng và sinh mã hiển thị MaDatPhongHienThi tự động như "DP001"
     */
    public static boolean insertDatPhong(String maKH, String maP, int maNV,LocalDateTime ngayThue) {
        String insertSQL = "INSERT INTO DatPhong ( MaKH, MaP, MaNV, NgayThue) VALUES (?, ?, ?, ?)";
        String updateSQL = "UPDATE DatPhong SET MaDatPhongHienThi = ? WHERE MaDP = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {


            insertStmt.setString(1, maKH);
            insertStmt.setString(2, maP);
            insertStmt.setInt(3, maNV);
            insertStmt.setTimestamp(4, Timestamp.valueOf(ngayThue));


            int rows = insertStmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = insertStmt.getGeneratedKeys();
                if (rs.next()) {
                    int maDP = rs.getInt(1);
                    String maDatPhongHienThi = String.format("DP%03d", maDP);

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                        updateStmt.setString(1, maDatPhongHienThi);
                        updateStmt.setInt(2, maDP);
                        updateStmt.executeUpdate();
                    }

                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateNgayTra(int maDP, LocalDateTime ngayTra) {
        String sql = "UPDATE DatPhong SET NgayTra = ? WHERE MaDP = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(ngayTra));
            ps.setInt(2, maDP);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getLastInsertedMaDP() {
        String sql = "SELECT MAX(MaDP) AS LastMaDP FROM DatPhong";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("LastMaDP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về -1 nếu không tìm thấy hoặc lỗi
        return -1;
    }
    public static DatPhong getDatPhongByMaPhong(String maPhong) {
        String sql = "SELECT * FROM DatPhong WHERE MaP = ? AND NgayTra IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new DatPhong(
                        rs.getInt("MaDP"),
                        rs.getString("MaKH"),
                        rs.getString("MaP"),
                        rs.getInt("MaNV"),
                        rs.getTimestamp("NgayThue").toLocalDateTime(),
                        rs.getTimestamp("NgayTra") != null ? rs.getTimestamp("NgayTra").toLocalDateTime() : null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
