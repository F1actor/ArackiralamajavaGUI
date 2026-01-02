package com.sinan.rentacar.repository;

import com.sinan.rentacar.core.DbConnection;
import com.sinan.rentacar.entity.Rental;
import java.sql.*;

public class RentalRepository {

    public boolean rentCar(Rental rental) {
        String insertSql = "INSERT INTO rentals (vehicle_id, customer_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?)";
        String updateVehicleSql = "UPDATE vehicles SET is_available = FALSE WHERE id = ?";

        try {
            Connection conn = DbConnection.getInstance();
            
            
            PreparedStatement pr = conn.prepareStatement(insertSql);
            pr.setInt(1, rental.getVehicleId());
            pr.setInt(2, rental.getCustomerId());
            pr.setDate(3, rental.getStartDate());
            pr.setDate(4, rental.getEndDate());
            pr.setDouble(5, rental.getTotalPrice());
            pr.executeUpdate();

       
            PreparedStatement prUpdate = conn.prepareStatement(updateVehicleSql);
            prUpdate.setInt(1, rental.getVehicleId());
            prUpdate.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}