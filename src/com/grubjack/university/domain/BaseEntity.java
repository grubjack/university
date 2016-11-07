package com.grubjack.university.domain;

/**
 * Created by grubjack on 03.11.2016.
 */
public class BaseEntity {
    protected int id;

    public BaseEntity() {
    }

    public BaseEntity(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
