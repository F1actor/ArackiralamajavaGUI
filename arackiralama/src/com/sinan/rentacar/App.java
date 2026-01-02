package com.sinan.rentacar;

import com.sinan.rentacar.view.VehicleView;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(() -> {
            VehicleView view = new VehicleView();
            view.setVisible(true);
        });
    }
}