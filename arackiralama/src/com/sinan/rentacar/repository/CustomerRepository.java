package com.sinan.rentacar.repository;

import com.sinan.rentacar.core.DbConnection;
import com.sinan.rentacar.entity.Customer;
import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {

    public ArrayList<Customer> findAll() {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY first_name ASC";

        try {
            Connection conn = DbConnection.getInstance();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setTcNo(rs.getString("tc_no"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setPhone(rs.getString("phone"));
                c.setEmail(rs.getString("email"));
                
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}