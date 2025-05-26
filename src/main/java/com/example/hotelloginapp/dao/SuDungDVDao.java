package com.example.hotelloginapp.dao;

import com.example.hotelloginapp.models.SuDungDV;
import com.example.hotelloginapp.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuDungDVDao {

    /**
     * Lấy tất cả bản ghi sử dụng dịch vụ
     */
    public static List<SuDungDV> getAllSuDungDV() {
        List<SuDungDV> list = new ArrayList<>();
        String query = "SELECT * FROM SuDungDV";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SuDungDV sddv = mapResultSetToSuDungDV(rs);
                list.add(sddv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Thêm mới bản ghi sử dụng dịch vụ
     */
    public static boolean insertSuDungDV(SuDungDV sddv) {
        String query = "INSERT INTO SuDungDV (NgayThue, SoLuong, MaDV, MaDP, TongTien) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(sddv.getNgayThue()));
            ps.setInt(2, sddv.getSoLuong());
            ps.setString(3, sddv.getMaDV());
            ps.setInt(4, sddv.getMaDP());
            ps.setBigDecimal(5, sddv.getTongTien());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa bản ghi sử dụng dịch vụ theo MaSDDV
     */
    public static boolean deleteSuDungDV(int maSDDV) {
        String query = "DELETE FROM SuDungDV WHERE MaSDDV = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, maSDDV);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<SuDungDV> getSuDungDVWithTenDVByMaDP(int maDP) {
        List<SuDungDV> list = new ArrayList<>();
        String sql = "SELECT sddv.*, dv.TenDV FROM SuDungDV sddv " +
                "JOIN DichVu dv ON sddv.MaDV = dv.MaDV " +
                "WHERE sddv.MaDP = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDP);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SuDungDV sddv = new SuDungDV(
                            rs.getInt("MaSDDV"),
                            rs.getTimestamp("NgayThue").toLocalDateTime(),
                            rs.getInt("SoLuong"),
                            rs.getString("MaDV"),
                            rs.getInt("MaDP"),
                            rs.getBigDecimal("TongTien")
                    );
                    sddv.setTenDV(rs.getString("TenDV")); // ✅ thêm tên dịch vụ
                    list.add(sddv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Hàm map ResultSet sang đối tượng SuDungDV
     */
    private static SuDungDV mapResultSetToSuDungDV(ResultSet rs) throws SQLException {
        return new SuDungDV(
                rs.getInt("MaSDDV"),
                rs.getTimestamp("NgayThue").toLocalDateTime(),
                rs.getInt("SoLuong"),
                rs.getString("MaDV"),
                rs.getInt("MaDP"),
                rs.getBigDecimal("TongTien")
        );
    }
}
