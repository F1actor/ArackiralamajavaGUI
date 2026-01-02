package com.sinan.rentacar.view;

import com.sinan.rentacar.entity.Vehicle;
import com.sinan.rentacar.entity.SUV;
import com.sinan.rentacar.service.VehicleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VehicleView extends JFrame {
    private VehicleService vehicleService;
    private JTable table;
    private DefaultTableModel tableModel;

    
    private JTextField txtPlate = new JTextField(10);
    private JTextField txtBrand = new JTextField(10);
    private JTextField txtModel = new JTextField(10);
    private JTextField txtFuel = new JTextField(10);
    private JTextField txtPrice = new JTextField(10);
    private JTextField txtKm = new JTextField(10);
    
    
    private JComboBox<String> cmbType = new JComboBox<>(new String[]{"Otomobil", "SUV"});

    public VehicleView() {
        vehicleService = new VehicleService();
        initUI();
    }

    private void initUI() {
        setTitle("Araç Kiralama Sistemi | Yönetim Paneli");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

      
        String[] columns = {"ID", "Tür", "Plaka", "Marka", "Model", "Yakıt", "Fiyat", "KM", "Müsait"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        
       
        loadVehicleData();
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        
       
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.add(new JLabel("Tür:"));    formPanel.add(cmbType);
        formPanel.add(new JLabel("Plaka:")); formPanel.add(txtPlate);
        formPanel.add(new JLabel("Marka:")); formPanel.add(txtBrand);
        formPanel.add(new JLabel("Model:")); formPanel.add(txtModel);
        formPanel.add(new JLabel("Yakıt:")); formPanel.add(txtFuel);
        formPanel.add(new JLabel("Fiyat:")); formPanel.add(txtPrice);
        formPanel.add(new JLabel("KM:"));    formPanel.add(txtKm);
        
       
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("YENİ ARAÇ EKLE");
        JButton btnReload = new JButton("Listeyi Yenile");
        JButton btnRent = new JButton("SEÇİLİ ARACI KİRALA");
        
      
        btnAdd.setBackground(new Color(46, 204, 113)); // Yeşil
        btnAdd.setForeground(Color.WHITE);
        
        btnRent.setBackground(new Color(52, 152, 219)); // Mavi
        btnRent.setForeground(Color.WHITE);

       
        btnAdd.addActionListener(e -> {
          
            String selectedType = cmbType.getSelectedItem().toString().equals("SUV") ? "SUV" : "CAR";
            
            boolean result = vehicleService.addVehicle(
                txtPlate.getText(), txtBrand.getText(), txtModel.getText(),
                txtFuel.getText(), txtPrice.getText(), txtKm.getText(),
                selectedType
            );

            if (result) {
                JOptionPane.showMessageDialog(this, "Araç Başarıyla Eklendi!");
                loadVehicleData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Hata! Bilgileri kontrol edin (Plaka benzersiz olmalı).", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnReload.addActionListener(e -> loadVehicleData());

      
        btnRent.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            
            if (selectedRow != -1) {
                
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String isAvailable = (String) tableModel.getValueAt(selectedRow, 8); 

                if (isAvailable.equals("Hayır")) {
                    JOptionPane.showMessageDialog(this, "Bu araç zaten kirada!");
                    return;
                }

                
                ArrayList<Vehicle> list = vehicleService.getVehicles();
                Vehicle selectedVehicle = null;
                for(Vehicle v : list) {
                    if(v.getId() == id) { selectedVehicle = v; break; }
                }

                if (selectedVehicle != null) {
                  
                    new RentalDialog(this, selectedVehicle).setVisible(true);
                    
                    loadVehicleData(); 
                }

            } else {
                JOptionPane.showMessageDialog(this, "Lütfen tablodan bir araç seçin!");
            }
        });

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnReload);
        buttonPanel.add(btnRent);

        inputPanel.add(formPanel);
        inputPanel.add(buttonPanel);
        
        add(inputPanel, BorderLayout.SOUTH);
    }

    
    private void loadVehicleData() {
        tableModel.setRowCount(0); 
        ArrayList<Vehicle> vehicles = vehicleService.getVehicles();
        
        for (Vehicle v : vehicles) {
           
            String typeDisplay = (v instanceof SUV) ? "SUV (4x4)" : "Otomobil";
            
            Object[] row = {
                v.getId(),
                typeDisplay,
                v.getPlate(),
                v.getBrand(),
                v.getModel(),
                v.getFuelType(),
                v.getDailyPrice(),
                v.getKm(),
                v.isAvailable() ? "Evet" : "Hayır"
            };
            tableModel.addRow(row);
        }
    }

    private void clearFields() {
        txtPlate.setText(""); txtBrand.setText(""); txtModel.setText("");
        txtFuel.setText(""); txtPrice.setText(""); txtKm.setText("");
    }
}