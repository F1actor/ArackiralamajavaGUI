package com.sinan.rentacar.repository;

import com.sinan.rentacar.core.DbConnection;
import com.sinan.rentacar.core.IGenericRepository;
import com.sinan.rentacar.entity.Car;
import com.sinan.rentacar.entity.SUV;
import com.sinan.rentacar.entity.Vehicle;
import java.sql.*;
import java.util.ArrayList;

public class VehicleRepository implements IGenericRepository<Vehicle> {

    @Override
    public ArrayList<Vehicle> findAll() {
        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY id ASC";

        try {
            Connection conn = DbConnection.getInstance();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                
                String type = rs.getString("vehicle_type");
                Vehicle obj;

                
                if ("SUV".equalsIgnoreCase(type)) {
                    obj = new SUV();
                } else {
                    obj = new Car();
                }

                // Ortak bilgileri doldur
                obj.setId(rs.getInt("id"));
                obj.setPlate(rs.getString("plate"));
                obj.setBrand(rs.getString("brand"));
                obj.setModel(rs.getString("model"));
                obj.setFuelType(rs.getString("fuel_type"));
                obj.setDailyPrice(rs.getDouble("daily_price"));
                obj.setKm(rs.getInt("km"));
                obj.setAvailable(rs.getBoolean("is_available"));

                vehicleList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }

    @Override
    public boolean save(Vehicle entity) {
        
        String sql = "INSERT INTO vehicles (plate, brand, model, fuel_type, daily_price, km, is_available, vehicle_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DbConnection.getInstance();
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, entity.getPlate());
            pr.setString(2, entity.getBrand());
            pr.setString(3, entity.getModel());
            pr.setString(4, entity.getFuelType());
            pr.setDouble(5, entity.getDailyPrice());
            pr.setInt(6, entity.getKm());
            pr.setBoolean(7, entity.isAvailable());
            
           
            if (entity instanceof SUV) {
                pr.setString(8, "SUV");
            } else {
                pr.setString(8, "CAR");
            }

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   
    @Override public Vehicle getById(int id) { return null; } 
    @Override public boolean update(Vehicle entity) { return false; } 
    @Override public boolean delete(int id) { return false; } 
}