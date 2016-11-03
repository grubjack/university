package com.grubjack.university.dao;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public interface PersonDao<T> extends RootDao<T> {

    List<T> findByFirstName(String firstName);

    List<T> findByLastName(String lastName);

    List<T> findByName(String firstName, String lastName);

    List<T> findAll(int unitId);

}
