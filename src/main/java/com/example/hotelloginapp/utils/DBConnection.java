package com.example.hotelloginapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String SERVER_NAME = "DESKTOP-K1JFJOM"; // Thay thế bằng tên server của bạn
    private static final String DATABASE_NAME = "HOTEL"; // Thay thế bằng tên database của bạn
    private static final String USERNAME = "sa"; // Thay thế bằng username của bạn
    private static final String PASSWORD = "1"; // Thay thế bằng password của bạn

    private static final String CONNECTION_URL = "jdbc:sqlserver://" + SERVER_NAME + ";databaseName=" + DATABASE_NAME + ";user=" + USERNAME + ";password=" + PASSWORD + ";encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL);
    }

}
