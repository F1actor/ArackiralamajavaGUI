package com.sinan.rentacar.core;

import java.util.ArrayList;


public interface IGenericRepository<T> {
    
    ArrayList<T> findAll();  
    
    T getById(int id);       
    
    boolean save(T entity);  
    
    boolean update(T entity);
    
    boolean delete(int id); 
}