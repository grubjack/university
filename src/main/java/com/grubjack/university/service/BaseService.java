package com.grubjack.university.service;

import java.util.List;

/**
 * Created by grubjack on 28.11.2016.
 */
public interface BaseService<T> {

    List<T> findAll();

    T findById(int id);

    void delete(int id); 
}
