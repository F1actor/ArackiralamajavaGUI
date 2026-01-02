package com.sinan.rentacar.view;

import com.sinan.rentacar.entity.Customer;
import com.sinan.rentacar.entity.Vehicle;
import com.sinan.rentacar.repository.CustomerRepository;
import com.sinan.rentacar.service.RentalService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RentalDialog extends JDialog {
    private Vehicle vehicle;
    private JComboBox<Customer> cmbCustomers;
    private JTextField txtDays;
    private RentalService rentalService;

    public RentalDialog(Frame parent, Vehicle vehicle) {
        super(parent, "Araç Kirala: " + vehicle.getBrand() + " " + vehicle.getModel(), true);
        this.vehicle = vehicle;
        this.rentalService = new RentalService();
        
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2, 10, 10));

        
        add(new JLabel("Müşteri Seç:"));
        cmbCustomers = new JComboBox<>();
        loadCustomers();
        add(cmbCustomers);

        
        add(new JLabel("Kaç Gün Kiralanacak:"));
        txtDays = new JTextField("1");
        add(txtDays);

        
        add(new JLabel("Bilgi:"));
        add(new JLabel("SUV araçlarda %10 fark alınır."));

        
        JButton btnRent = new JButton("KİRALAYI TAMAMLA");
        btnRent.setBackground(new Color(46, 204, 113));
        btnRent.setForeground(Color.WHITE);
        
        btnRent.addActionListener(e -> completeRental());
        
        add(new JLabel("")); 
        add(btnRent);
    }

    private void loadCustomers() {
        CustomerRepository repo = new CustomerRepository();
        ArrayList<Customer> list = repo.findAll();
        for (Customer c : list) {
            cmbCustomers.addItem(c);
        }
    }

    private void completeRental() {
        try {
            Customer selectedCustomer = (Customer) cmbCustomers.getSelectedItem();
            int days = Integer.parseInt(txtDays.getText());

            boolean success = rentalService.startRental(vehicle, selectedCustomer.getId(), days);

            if (success) {
               
                double price = vehicle.calculatePrice(days);
                JOptionPane.showMessageDialog(this, "İşlem Başarılı!\nToplam Tutar: " + price + " TL");
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen gün sayısını rakamla girin.");
        }
    }
}