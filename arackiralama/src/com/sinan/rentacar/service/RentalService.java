package com.sinan.rentacar.service;

import com.sinan.rentacar.entity.Rental;
import com.sinan.rentacar.entity.Vehicle;
import com.sinan.rentacar.repository.RentalRepository;
import java.sql.Date;
import java.time.LocalDate;

public class RentalService {
    private RentalRepository rentalRepo = new RentalRepository();

    public boolean startRental(Vehicle vehicle, int customerId, int days) {
        try {
            double price = vehicle.calculatePrice(days);

            Rental rental = new Rental();
            rental.setVehicleId(vehicle.getId());
            rental.setCustomerId(customerId);
            rental.setTotalPrice(price);
            
            
            LocalDate today = LocalDate.now();
            rental.setStartDate(Date.valueOf(today));
            rental.setEndDate(Date.valueOf(today.plusDays(days)));

            return rentalRepo.rentCar(rental);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}