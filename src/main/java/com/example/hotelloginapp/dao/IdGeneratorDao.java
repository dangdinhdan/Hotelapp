package com.example.hotelloginapp.dao;

import com.example.hotelloginapp.utils.DBConnection;
import java.sql.*;

public class IdGeneratorDao {
    public static int getNextId(String table) {
        String sql = "SELECT IDENT_CURRENT(?) + IDENT_INCR(?) AS NextId";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, table);
            stmt.setString(2, table);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("NextId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // fallback nếu có lỗi
    }

    public static String generateFormattedId(String prefix, String table, int digits) {
        int nextId = getNextId(table);
        return String.format("%s%0" + digits + "d", prefix, nextId);
    }
}