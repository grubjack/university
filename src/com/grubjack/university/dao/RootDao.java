package com.grubjack.university.dao;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public interface RootDao<T> {

    T update(T room);

    boolean delete(int id);

    T find(int id);

    List<T> findAll();
}
