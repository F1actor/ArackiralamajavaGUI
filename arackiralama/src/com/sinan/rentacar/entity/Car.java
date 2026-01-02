package com.sinan.rentacar.entity;

public class Car extends Vehicle {

    public Car() {
        super();
    }

    public Car(int id, String plate, String brand, String model, String fuelType, double dailyPrice, int km, boolean isAvailable) {
        super(id, plate, brand, model, fuelType, dailyPrice, km, isAvailable);
    }

    
    @Override
    public double calculatePrice(int days) {
       
        return this.getDailyPrice() * days;
    }
}