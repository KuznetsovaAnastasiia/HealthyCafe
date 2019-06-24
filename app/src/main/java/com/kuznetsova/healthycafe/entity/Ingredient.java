package com.kuznetsova.healthycafe.entity;

public class Ingredient {
    private long id;
    private String name;
    private int number;

    public Ingredient(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
