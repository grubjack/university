package com.grubjack.university.dao;

import com.grubjack.university.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Transactional(readOnly = true)
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    List<Faculty> findAllByOrderByName();

    List<Faculty> findFacultiesByName(String name);
}
