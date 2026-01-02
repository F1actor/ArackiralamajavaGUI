package com.sinan.rentacar.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    // Veritabanı Ayarları
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/idpproject";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "650650"; 

    private static Connection connection = null;


    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantı hatası: " + e.getMessage());
        }
        return connection;
    }
}
