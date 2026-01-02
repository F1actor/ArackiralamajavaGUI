package com.sinan.rentacar.service;

import com.sinan.rentacar.entity.Car;
import com.sinan.rentacar.entity.SUV;
import com.sinan.rentacar.entity.Vehicle;
import com.sinan.rentacar.repository.VehicleRepository;
import java.util.ArrayList;

public class VehicleService {
    private VehicleRepository vehicleRepo = new VehicleRepository();

    public ArrayList<Vehicle> getVehicles() {
        return vehicleRepo.findAll();
    }

   
    public boolean addVehicle(String plate, String brand, String model, String fuel, String priceStr, String kmStr, String type) {
        try {
            if (plate.isEmpty() || brand.isEmpty()) return false;

            double price = Double.parseDouble(priceStr);
            int km = Integer.parseInt(kmStr);

            Vehicle newVehicle;

            
            if ("SUV".equals(type)) {
                newVehicle = new SUV(); 
            } else {
                newVehicle = new Car(); 
            }

           
            
            newVehicle.setPlate(plate);
            newVehicle.setBrand(brand);
            newVehicle.setModel(model);
            newVehicle.setFuelType(fuel);
            newVehicle.setDailyPrice(price);
            newVehicle.setKm(km);
            newVehicle.setAvailable(true);

            return vehicleRepo.save(newVehicle);

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
            return false;
        }
    }
}