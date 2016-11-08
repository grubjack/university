package com.grubjack.university.dao;

import com.grubjack.university.exception.DaoException;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public interface PersonDao<T> extends BaseDao<T> {

    void create(T t, int unitId) throws DaoException;

    void update(T t, int unitId) throws DaoException;

    List<T> findByFirstName(String firstName) throws DaoException;

    List<T> findByFirstName(int unitId, String firstName) throws DaoException;

    List<T> findByLastName(String lastName) throws DaoException;

    List<T> findByLastName(int unitId, String lastName) throws DaoException;

    List<T> findByName(String firstName, String lastName) throws DaoException;

    List<T> findByName(int unitId, String firstName, String lastName) throws DaoException;

    List<T> findAll(int unitId) throws DaoException;

}
