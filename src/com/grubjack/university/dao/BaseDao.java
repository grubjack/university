package com.grubjack.university.dao;

import com.grubjack.university.DaoException;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public interface BaseDao<T> {

    void delete(int id) throws DaoException;

    T find(int id) throws DaoException;

    List<T> findAll() throws DaoException;
}
