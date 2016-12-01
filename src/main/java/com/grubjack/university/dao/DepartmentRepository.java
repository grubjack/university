package com.grubjack.university.dao;

import com.grubjack.university.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findByFacultyIdOrderByName(int facultyId);

    List<Department> findAllByOrderByName();

    List<Department> findDepartmentsByName(String name);

    List<Department> findDepartmentsByNameAndFaculty(String name, int facultyId);
}