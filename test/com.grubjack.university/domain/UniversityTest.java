package com.grubjack.university.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by grubjack on 31.10.2016.
 */
public class UniversityTest {

    University university = University.getInstance();

    @Before
    public void resetSingleton() throws Exception {
        Field instance = University.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testNoRooms() {
        Assert.assertEquals(0, university.getRooms().size());
    }

    @Test
    public void testCreateRoom() {
        Classroom room1 = new Classroom("101b", "Floor 1", 150);
        university.createRoom(room1);
        university.createRoom(room1);
        Assert.assertEquals(1, university.getRooms().size());
    }

    @Test
    public void testCreateNullRoom() {
        university.createRoom(null);
        Assert.assertEquals(0, university.getRooms().size());
    }

    @Test
    public void testDeleteRoom() {
        Classroom room1 = new Classroom("101b", "Floor 1", 150);
        Classroom room2 = new Classroom("159", "Floor 4", 10);
        university.createRoom(room1);
        university.createRoom(room2);
        university.deleteRoom(room1);
        Assert.assertEquals(1, university.getRooms().size());
    }

    @Test
    public void testDeleteNullRoom() {
        Classroom room1 = new Classroom();
        university.createRoom(room1);
        university.deleteRoom(null);
        Assert.assertEquals(1, university.getRooms().size());
    }

    @Test
    public void testUpdateRoom() {
        Classroom room1 = new Classroom("101b", "Floor 1", 150);
        Classroom room2 = new Classroom("159", "Floor 4", 10);
        university.createRoom(room1);
        university.createRoom(room2);
        room1.setCapacity(100);
        university.updateRoom(room1);
        List<Classroom> classrooms = university.getRooms();
        if (classrooms != null && classrooms.size() > 1) {
            Classroom firstRoom = classrooms.get(0);
            if (firstRoom != null)
                Assert.assertEquals(100, firstRoom.getCapacity());
        }
    }

    @Test
    public void testUpdateNullRoom() {
        Classroom room1 = new Classroom("101b", "Floor 1", 150);
        Classroom room2 = new Classroom("159", "Floor 4", 10);
        university.createRoom(room1);
        university.createRoom(room2);
        room1 = null;
        university.updateRoom(room1);
        Assert.assertEquals(2, university.getRooms().size());
    }


    @Test
    public void testNoFaculties() {
        Assert.assertEquals(0, university.getFaculties().size());
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty1 = new Faculty("FEL");
        university.createFaculty(faculty1);
        university.createFaculty(faculty1);
        Assert.assertEquals(1, university.getFaculties().size());
    }


    @Test
    public void testCreateNullFaculty() {
        university.createFaculty(null);
        Assert.assertEquals(0, university.getFaculties().size());
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty1 = new Faculty("FEL");
        Faculty faculty2 = new Faculty("VITI");
        university.createFaculty(faculty1);
        university.createFaculty(faculty2);
        university.deleteFaculty(faculty1);
        Assert.assertEquals(1, university.getFaculties().size());
    }

    @Test
    public void testDeleteNullFaculty() {
        Faculty faculty1 = new Faculty("FEL");
        university.createFaculty(faculty1);
        university.deleteRoom(null);
        Assert.assertEquals(1, university.getFaculties().size());
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty1 = new Faculty("FEL");
        Faculty faculty2 = new Faculty("VITI");
        university.createFaculty(faculty1);
        university.createFaculty(faculty2);
        faculty1.setName("RTF");
        university.updateFaculty(faculty1);
        List<Faculty> faculties = university.getFaculties();
        if (faculties != null && faculties.size() > 1) {
            Faculty firstFaculty = faculties.get(0);
            if (firstFaculty != null)
                Assert.assertEquals("RTF", firstFaculty.getName());
        }
    }

    @Test
    public void testUpdateNullFaculty() {
        Faculty faculty1 = new Faculty("FEL");
        Faculty faculty2 = new Faculty("VITI");
        university.createFaculty(faculty1);
        university.createFaculty(faculty2);
        faculty1 = null;
        university.updateFaculty(faculty1);
        Assert.assertEquals(2, university.getFaculties().size());
    }


}