package com.grubjack.university.dao;

import com.grubjack.university.dao.mock.ClassroomDaoMockImpl;
import com.grubjack.university.domain.Classroom;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by grubjack on 04.11.2016.
 */
public class ClassroomDaoImplTest {

    ClassroomDao classroomDao = new ClassroomDaoMockImpl();

    @Test
    public void testCreate() {
        Classroom classroom = new Classroom("101b", "1 floor", 100);
        classroomDao.create(classroom);
        Assert.assertEquals(1, classroomDao.findAll().size());
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testDelete() {

    }

    @Test
    public void testFind() {

    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void testFindByNumber() {

    }

}