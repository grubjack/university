package com.grubjack.university.dao.impl;

import com.grubjack.university.dao.DepartmentDao;
import com.grubjack.university.model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by grubjack on 03.11.2016.
 */
@Repository
@Transactional
public class DepartmentDaoImpl implements DepartmentDao {

    private static Logger log = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Department department, int facultyId) {
        if (department != null) {
            log.info("Creating new department " + department.getName() + " by faculty with id " + facultyId);
            getSession().save(department);
            log.info("Department is created with id = " + department.getId());
        }
    }

    @Override
    public void update(Department department, int facultyId) {
        if (department != null) {
            Department departmentToUpdate = find(department.getId());
            if (departmentToUpdate != null) {
                departmentToUpdate.setName(department.getName());
                log.info("Updating department with id " + department.getId() + " by faculty with id " + facultyId);
                getSession().update(departmentToUpdate);
            }
        }
    }

    @Override
    public void delete(int id) {
        Department department = find(id);
        if (department != null) {
            log.info("Deleting department with id " + id);
            getSession().delete(department);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Department find(int id) {
        log.info("Finding department with id " + id);
        return getSession().get(Department.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        log.info("Finding all departments");
        return getSession().createQuery("from Department order by name").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll(int facultyId) {
        log.info("Finding all departments with facultyId " + facultyId);
        return getSession().createQuery("from Department d where d.faculty.id=:facultyId order by d.name")
                .setParameter("facultyId", facultyId).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findByName(String name) {
        log.info("Finding department with name " + name);
        return getSession().createQuery("from Department where lower(name) like :name order by name")
                .setParameter("name", "%" + name.toLowerCase() + "%").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findByName(String name, int facultyId) {
        log.info("Finding department with name " + name + " by faculty with id " + facultyId);
        return getSession().createQuery("from Department d where d.faculty.id=:facultyId and lower(d.name) like :name order by d.name")
                .setParameter("facultyId", facultyId)
                .setParameter("name", "%" + name.toLowerCase() + "%").list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
