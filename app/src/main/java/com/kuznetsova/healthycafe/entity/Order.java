package com.kuznetsova.healthycafe.entity;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private long id;
    private int tableNumber;
    private String leadTime;
    private Date orderTime;
    private int price;
    private Waiter waiter;
    private boolean readiness;
    private boolean referred;
    private ArrayList<Dish> dishes;
    private ArrayList<Integer> numbers;
    private ArrayList<Boolean> done;

    public Order(Waiter waiter) {
        this.waiter = waiter;
        this.dishes = new ArrayList<>();
        this.numbers = new ArrayList<>();
        this.done = new ArrayList<>();
    }

    public Order(int tableNumber, String leadTime, Date orderTime, int price, Waiter waiter, boolean referred) {
        this.tableNumber = tableNumber;
        this.leadTime = leadTime;
        this.orderTime = orderTime;
        this.price = price;
        this.waiter = waiter;
        this.readiness = false;
        this.referred = referred;
        this.dishes = new ArrayList<>();
        this.numbers = new ArrayList<>();
        this.done = new ArrayList<>();
    }

    public boolean checkReadiness(){
        boolean flag = true;
        for(boolean d : done)
            flag &= d;
        readiness = flag;
        return flag;
    }

    public void setReferred(boolean referred) {
        this.referred = referred;
    }

    public boolean isReadiness() {
        return readiness;
    }

    public boolean isReferred() {
        return referred;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public void setReadiness(boolean readiness) {
        this.readiness = readiness;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public void setDone(ArrayList<Boolean> done) {
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getLeadTime() {
        return leadTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public int getPrice() {
        return price;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public boolean isReady() {
        return readiness;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public ArrayList<Boolean> getDone() {
        return done;
    }

    public void checkDone() {
        for(int i=0; i<dishes.size(); i++)
            done.add(false);
    }
}
