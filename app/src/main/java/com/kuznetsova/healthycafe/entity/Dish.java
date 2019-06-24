package com.kuznetsova.healthycafe.entity;

import java.util.ArrayList;

public class Dish {
    private long id;
    private String name;
    private int price;
    private int weight;
    private int proteins;
    private int fats;
    private int carbohydrates;
    private int calories;
    private Chef chef;
    private Nutritionist nutritionist;
    private DishType type;
    private MenuType menu;
    private ArrayList<Ingredient> ingredients;

    public Dish() {
        ingredients = new ArrayList<>();
    }

    public Dish(String name, int price, int weight, int proteins, int fats, int carbohydrates, int calories, Chef chef, Nutritionist nutritionist, DishType type, MenuType menu) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.chef = chef;
        this.nutritionist = nutritionist;
        this.type = type;
        this.menu = menu;
        ingredients = new ArrayList<>();
    }

    public void setNutritionist(Nutritionist nutritionist) {
        this.nutritionist = nutritionist;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    public void setMenu(MenuType menu) {
        this.menu = menu;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Nutritionist getNutritionist() {
        return nutritionist;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public int getProteins() {
        return proteins;
    }

    public int getFats() {
        return fats;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public int getCalories() {
        return calories;
    }

    public Chef getChef() {
        return chef;
    }

    public DishType getType() {
        return type;
    }

    public MenuType getMenu() {
        return menu;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
