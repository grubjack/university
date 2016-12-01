package com.grubjack.university.dao;

import com.grubjack.university.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 30.11.2016.
 */
@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByOrderByLastNameAscFirstNameAsc();

    List<Student> findByGroupIdOrderByLastNameAscFirstNameAsc(int groupId);

    List<Student> findStudentsByName(String name);

    List<Student> findStudentsByNameAndGroup(String name, int groupId);

}