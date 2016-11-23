package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DaoFactory;
import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.domain.Department;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
public class DepartmentDaoImpl implements DepartmentDao {

    private static Logger log = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    private Session getSession() {
        return DaoFactory.getSessionFactory().openSession();
    }

    @Override
    public void create(Department department, int facultyId) {
        if (department != null && department.getFaculty() != null && department.getFaculty().getId() == facultyId) {
            log.info("Creating new department " + department.getName());
            Session session = getSession();
            session.save(department);
            session.flush();
            session.close();
            log.info("Department is created with id = " + department.getId());
        }
    }

    @Override
    public void update(Department department, int facultyId) {
        if (department != null) {
            Department departmentToUpdate = find(department.getId());
            if (departmentToUpdate != null && departmentToUpdate.getFaculty() != null && departmentToUpdate.getFaculty().getId() == facultyId) {
                departmentToUpdate.setName(department.getName());
                log.info("Updating department with id " + department.getId());
                Session session = getSession();
                session.update(departmentToUpdate);
                session.flush();
                session.close();
            }
        }
    }

    @Override
    public void delete(int id) {
        Department department = find(id);
        if (department != null) {
            log.info("Deleting department with id " + id);
            Session session = getSession();
            session.delete(department);
            session.close();
        }
    }

    @Override
    public Department find(int id) {
        log.info("Finding department with id " + id);
        Session session = getSession();
        Department result = session.get(Department.class, id);
        session.close();
        return result;
    }

    @Override
    public List<Department> findAll() {
        log.info("Finding all departments");
        Session session = getSession();
        List<Department> result = session.createQuery("from Department").list();
        session.close();
        return result;
    }

    @Override
    public Department findByName(String name) {
        log.info("Finding department with name " + name);
        Session session = getSession();
        Department result = (Department) session.createQuery("from Department where lower(name)=:name")
                .setParameter("name", name.toLowerCase()).uniqueResult();
        session.close();
        return result;
    }

    @Override
    public List<Department> findAll(int facultyId) {
        log.info("Finding all departments with facultyId " + facultyId);
        Session session = getSession();
        List<Department> result = session.createQuery("from Department d where d.faculty.id=:facultyId")
                .setParameter("facultyId", facultyId).list();
        session.close();
        return result;
    }
}
