package com.sinan.rentacar.entity;

public abstract class Vehicle { 
    private int id;
    private String plate;
    private String brand;
    private String model;
    private String fuelType;
    private double dailyPrice;
    private int km;
    private boolean isAvailable;

    public Vehicle() {}

    public Vehicle(int id, String plate, String brand, String model, String fuelType, double dailyPrice, int km, boolean isAvailable) {
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
        this.dailyPrice = dailyPrice;
        this.km = km;
        this.isAvailable = isAvailable;
    }
    
    
    public abstract double calculatePrice(int days); 

 
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public double getDailyPrice() { return dailyPrice; }
    public void setDailyPrice(double dailyPrice) { this.dailyPrice = dailyPrice; }
    public int getKm() { return km; }
    public void setKm(int km) { this.km = km; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    @Override
    public String toString() { return brand + " " + model + " (" + plate + ")"; }
}