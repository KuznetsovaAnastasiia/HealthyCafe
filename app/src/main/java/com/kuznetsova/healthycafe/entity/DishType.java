package com.kuznetsova.healthycafe.entity;

public class DishType {
    private long id;
    private String name;

    public DishType(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
