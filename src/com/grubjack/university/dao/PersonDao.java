package com.grubjack.university.dao;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public interface PersonDao<T> extends BaseDao<T> {

    void create(T t, int unitId);

    void update(T t, int unitId);

    List<T> findByFirstName(String firstName);

    List<T> findByFirstName(int unitId, String firstName);

    List<T> findByLastName(String lastName);

    List<T> findByLastName(int unitId, String lastName);

    List<T> findByName(String firstName, String lastName);

    List<T> findByName(int unitId, String firstName, String lastName);

    List<T> findAll(int unitId);

}
