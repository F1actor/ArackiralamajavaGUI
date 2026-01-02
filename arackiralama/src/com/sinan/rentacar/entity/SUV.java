package com.sinan.rentacar.entity;

public class SUV extends Vehicle {

    public SUV() {
        super();
    }

    public SUV(int id, String plate, String brand, String model, String fuelType, double dailyPrice, int km, boolean isAvailable) {
        super(id, plate, brand, model, fuelType, dailyPrice, km, isAvailable);
    }

    @Override
    public double calculatePrice(int days) {
    
        double basePrice = this.getDailyPrice() * days;
        return basePrice + (basePrice * 0.10); 
    }
}