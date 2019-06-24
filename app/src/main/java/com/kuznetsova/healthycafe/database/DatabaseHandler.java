package com.kuznetsova.healthycafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kuznetsova.healthycafe.entity.Chef;
import com.kuznetsova.healthycafe.entity.Dish;
import com.kuznetsova.healthycafe.entity.DishType;
import com.kuznetsova.healthycafe.entity.Ingredient;
import com.kuznetsova.healthycafe.entity.MenuType;
import com.kuznetsova.healthycafe.entity.Nutritionist;
import com.kuznetsova.healthycafe.entity.Order;
import com.kuznetsova.healthycafe.entity.Waiter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HealthyCafe";


    private static final String TABLE_CHEFS = "chef";
    private static final String FIELD_CHEF_ID_KEY = "chef_id";
    private static final String FIELD_CHEF_NAME = "chef_name";
    private static final String FIELD_CHEF_SURNAME = "chef_surname";
    private static final String FIELD_CHEF_PATRONYMIC = "chef_patronymic";

    private static final String TABLE_NUTRITIONISTS = "nutritionists";
    private static final String FIELD_NUTRITIONIST_ID_KEY = "nutritionists_id";
    private static final String FIELD_NUTRITIONIST_NAME = "nutritionists_name";
    private static final String FIELD_NUTRITIONIST_SURNAME = "nutritionists_surname";
    private static final String FIELD_NUTRITIONIST_PATRONYMIC = "nutritionists_patronymic";

    private static final String TABLE_WAITERS = "waiter";
    private static final String FIELD_WAITER_ID_KEY = "waiter_id";
    private static final String FIELD_WAITER_NAME = "waiter_name";
    private static final String FIELD_WAITER_SURNAME = "waiter_surname";
    private static final String FIELD_WAITER_PATRONYMIC = "waiter_patronymic";

    private static final String TABLE_ORDERS = "orders";
    private static final String FIELD_ORDER_ID_KEY = "order_id";
    private static final String FIELD_ORDER_TABLE_NUMBER = "order_table_number";
    private static final String FIELD_ORDER_LEAD_TIME = "order_lead_time";
    private static final String FIELD_ORDER_TIME = "order_time";
    private static final String FIELD_ORDER_PRICE = "order_price";
    private static final String FIELD_ORDER_REFERRED = "order_referred";
    private static final String FIELD_ORDER_WAITER_ID = "order_waiter_id";

    private static final String TABLE_DISHES = "dishes";
    private static final String FIELD_DISH_ID_KEY = "dish_id";
    private static final String FIELD_DISH_NAME = "dish_name";
    private static final String FIELD_DISH_PRICE = "dish_price";
    private static final String FIELD_DISH_WEIGHT = "dish_weight";
    private static final String FIELD_DISH_PROTEINS = "dish_proteins";
    private static final String FIELD_DISH_FATS = "dish_fats";
    private static final String FIELD_DISH_CARBOHYDRATES = "dish_carbohydrates";
    private static final String FIELD_DISH_CALORIES = "dish_calories";
    private static final String FIELD_DISH_CHEF_ID = "dish_chef_id";
    private static final String FIELD_DISH_NUTRITIONIST_ID = "dish_nutritionist_id";
    private static final String FIELD_DISH_TYPE_ID = "dish_type_id";
    private static final String FIELD_DISH_MENU_ID = "dish_menu_id";

    private static final String TABLE_DISH_IN_ORDER = "dish_in_order";
    private static final String FIELD_DISH_IN_ORDER_ID_KEY = "dish_in_order_id";
    private static final String FIELD_DISH_IN_ORDER_DISH_ID = "dish_id";
    private static final String FIELD_DISH_IN_ORDER_ORDER_ID = "order_id";
    private static final String FIELD_DISH_IN_ORDER_DISH_NUMB = "dish_numb";
    private static final String FIELD_DISH_IN_ORDER_DISH_DONE = "dish_done";

    private static final String TABLE_DISH_TYPE = "dish_type";
    private static final String FIELD_DISH_TYPE_ID_KEY = "dish_type_id";
    private static final String FIELD_DISH_TYPE_NAME = "dish_type_name";

    private static final String TABLE_INGREDIENTS = "ingredient";
    private static final String FIELD_INGREDIENT_ID_KEY = "ingredient_id";
    private static final String FIELD_INGREDIENT_NAME = "ingredient_name";
    private static final String FIELD_INGREDIENT_NUMBER = "ingredient_number";

    private static final String TABLE_INGREDIENT_IN_DISH = "ingredient_in_dish";
    private static final String FIELD_INGREDIENT_IN_DISH_ID_KEY = "ingredient_in_dish_id";
    private static final String FIELD_INGREDIENT_IN_DISH_DISH_ID = "dish_id";
    private static final String FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID = "ingredient_id";

    private static final String TABLE_MENU = "menu";
    private static final String FIELD_MENU_ID_KEY = "menu_id";
    private static final String FIELD_MENU_NAME = "menu_name";

    String CREATE_CHEFS_TABLE = "CREATE TABLE " + TABLE_CHEFS + "("
            + FIELD_CHEF_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_CHEF_NAME + " TEXT,"
            + FIELD_CHEF_SURNAME + " TEXT," + FIELD_CHEF_PATRONYMIC + " TEXT" + ")";
    String CREATE_WAITERS_TABLE = "CREATE TABLE " + TABLE_WAITERS + "("
            + FIELD_WAITER_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_WAITER_NAME + " TEXT,"
            + FIELD_WAITER_SURNAME + " TEXT," + FIELD_WAITER_PATRONYMIC + " TEXT" + ")";
    String CREATE_NUTRITIONISTS_TABLE = "CREATE TABLE " + TABLE_NUTRITIONISTS + "("
            + FIELD_NUTRITIONIST_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_NUTRITIONIST_NAME + " TEXT,"
            + FIELD_NUTRITIONIST_SURNAME + " TEXT," + FIELD_NUTRITIONIST_PATRONYMIC + " TEXT" + ")";
    String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
            + FIELD_ORDER_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_ORDER_TABLE_NUMBER + " INTEGER,"
            + FIELD_ORDER_LEAD_TIME + " DATETIME," + FIELD_ORDER_TIME + " DATETIME," + FIELD_ORDER_PRICE + " INTEGER,"
            + FIELD_ORDER_WAITER_ID + " INTEGER," + FIELD_ORDER_REFERRED + " INTEGER" + ")";
    String CREATE_DISHES_TABLE = "CREATE TABLE " + TABLE_DISHES + "("
            + FIELD_DISH_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_DISH_NAME + " TEXT,"
            + FIELD_DISH_PRICE + " INTEGER," + FIELD_DISH_WEIGHT + " INTEGER,"
            + FIELD_DISH_PROTEINS + " INTEGER," + FIELD_DISH_FATS + " INTEGER,"
            + FIELD_DISH_CARBOHYDRATES + " INTEGER," + FIELD_DISH_CALORIES + " INTEGER,"
            + FIELD_DISH_CHEF_ID + " INTEGER," + FIELD_DISH_TYPE_ID + " INTEGER,"
            + FIELD_DISH_MENU_ID + " INTEGER," + FIELD_DISH_NUTRITIONIST_ID + " INTEGER" + ")";
    String CREATE_DISH_IN_ORDER_TABLE = "CREATE TABLE " + TABLE_DISH_IN_ORDER + "(" + FIELD_DISH_IN_ORDER_ID_KEY + " INTEGER PRIMARY KEY,"
            + FIELD_DISH_IN_ORDER_DISH_ID + " INTEGER," + FIELD_DISH_IN_ORDER_DISH_DONE + " INTEGER,"
            + FIELD_DISH_IN_ORDER_DISH_NUMB + " INTEGER," + FIELD_DISH_IN_ORDER_ORDER_ID+ " INTEGER" + ")";
    String CREATE_DISH_TYPE_TABLE = "CREATE TABLE " + TABLE_DISH_TYPE + "("
            + FIELD_DISH_TYPE_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_DISH_TYPE_NAME + " INTEGER" + ")";
    String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
            + FIELD_INGREDIENT_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_INGREDIENT_NAME + " TEXT,"
            + FIELD_INGREDIENT_NUMBER + " INTEGER" + ")";
    String CREATE_INGREDIENT_IN_DISH_TABLE = "CREATE TABLE " + TABLE_INGREDIENT_IN_DISH + "("
            + FIELD_INGREDIENT_IN_DISH_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_INGREDIENT_IN_DISH_DISH_ID
            + " INTEGER,"  + FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID + " INTEGER" + ")";
    String CREATE_MENU_TABLE = "CREATE TABLE " + TABLE_MENU + "("
            + FIELD_MENU_ID_KEY + " INTEGER PRIMARY KEY," + FIELD_MENU_NAME + " TEXT" + ")";

    private static DatabaseHandler sInstance;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CHEFS_TABLE);
        db.execSQL(CREATE_WAITERS_TABLE);
        db.execSQL(CREATE_NUTRITIONISTS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_DISHES_TABLE);
        db.execSQL(CREATE_DISH_IN_ORDER_TABLE);
        db.execSQL(CREATE_DISH_TYPE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
        db.execSQL(CREATE_INGREDIENT_IN_DISH_TABLE);
        db.execSQL(CREATE_MENU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHEFS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAITERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRITIONISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH_IN_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_IN_DISH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);

        onCreate(db);
    }

    public long createChef(Chef chef){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_CHEF_NAME, chef.getName());
        values.put(FIELD_CHEF_SURNAME, chef.getSurname());
        values.put(FIELD_CHEF_PATRONYMIC, chef.getPatronymic());

        long id = db.insert(TABLE_CHEFS, null, values);
        chef.setId(id);

        return id;
    }

    public long createWaiter(Waiter waiter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_WAITER_NAME, waiter.getName());
        values.put(FIELD_WAITER_SURNAME, waiter.getSurname());
        values.put(FIELD_WAITER_PATRONYMIC, waiter.getPatronymic());

        long id = db.insert(TABLE_WAITERS, null, values);
        waiter.setId(id);

        return id;
    }

    public long createNutritionist(Nutritionist nutritionist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_NUTRITIONIST_NAME, nutritionist.getName());
        values.put(FIELD_NUTRITIONIST_SURNAME, nutritionist.getSurname());
        values.put(FIELD_NUTRITIONIST_PATRONYMIC, nutritionist.getPatronymic());

        long id = db.insert(TABLE_NUTRITIONISTS, null, values);
        nutritionist.setId(id);

        return id;
    }

    public long createDishType(DishType dishType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DISH_TYPE_NAME, dishType.getName());

        long id = db.insert(TABLE_DISH_TYPE, null, values);
        dishType.setId(id);

        return id;
    }

    public long createDish(Dish dish){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DISH_NAME, dish.getName());
        values.put(FIELD_DISH_PRICE, dish.getPrice());
        values.put(FIELD_DISH_WEIGHT, dish.getWeight());
        values.put(FIELD_DISH_PROTEINS, dish.getProteins());
        values.put(FIELD_DISH_FATS, dish.getFats());
        values.put(FIELD_DISH_CARBOHYDRATES, dish.getCarbohydrates());
        values.put(FIELD_DISH_CALORIES, dish.getCalories());
        values.put(FIELD_DISH_TYPE_ID, dish.getType().getId());
        values.put(FIELD_DISH_CHEF_ID, dish.getChef().getId());
        values.put(FIELD_DISH_MENU_ID, dish.getMenu().getId());
        values.put(FIELD_DISH_NUTRITIONIST_ID, dish.getNutritionist().getId());

        long id = db.insert(TABLE_DISHES, null, values);
        dish.setId(id);

        return id;
    }

    private long createIngredient(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_INGREDIENT_NAME, ingredient.getName());
        values.put(FIELD_INGREDIENT_NUMBER, ingredient.getNumber());

        long id = db.insert(TABLE_INGREDIENTS, null, values);
        ingredient.setId(id);

        return id;
    }

    public long createMenu(MenuType menuType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_MENU_NAME, menuType.getName());

        long id = db.insert(TABLE_MENU, null, values);
        menuType.setId(id);

        return id;
    }

    private long createOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ORDER_TABLE_NUMBER, order.getTableNumber());
        values.put(FIELD_ORDER_LEAD_TIME, order.getLeadTime());
        values.put(FIELD_ORDER_TIME, order.getOrderTime().toString());
        values.put(FIELD_ORDER_PRICE, order.getPrice());
        values.put(FIELD_ORDER_WAITER_ID, order.getWaiter().getId());
        values.put(FIELD_ORDER_REFERRED, (order.isReferred() == false ? 0 : 1));

        long id = db.insert(TABLE_ORDERS, null, values);
        order.setId(id);

        return id;
    }

    public void createOrderWithDishes(Order order){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Dish> dishes = order.getDishes();
        ArrayList<Integer> numbers = order.getNumbers();

        long orderId = createOrder(order);

        for(int i=0; i<dishes.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(FIELD_DISH_IN_ORDER_DISH_ID, dishes.get(i).getId());
            values.put(FIELD_DISH_IN_ORDER_ORDER_ID, orderId);
            values.put(FIELD_DISH_IN_ORDER_DISH_NUMB, numbers.get(i));
            values.put(FIELD_DISH_IN_ORDER_DISH_DONE, order.getDone().get(i) ? 1 : 0);

            db.insert(TABLE_DISH_IN_ORDER, null, values);
        }
    }

    public void createDishWithIngredients(Dish dish){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Ingredient> ingredients = dish.getIngredients();

        long dishId = createDish(dish);
        dish.setId(dishId);

        for(Ingredient ingredient : ingredients) {
            if(!db.rawQuery("SELECT  * FROM " + TABLE_INGREDIENTS
                    + " WHERE " + FIELD_INGREDIENT_ID_KEY + " = " + ingredient.getId(), null).moveToFirst())
                createIngredient(ingredient);

            ContentValues values = new ContentValues();
            values.put(FIELD_INGREDIENT_IN_DISH_DISH_ID, dishId);
            values.put(FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID, ingredient.getId());

            db.insert(TABLE_INGREDIENT_IN_DISH, null, values);
        }
    }

    public boolean emptyTables(){
        SQLiteDatabase db = this.getReadableDatabase();

        return !db.rawQuery("SELECT  * FROM " + TABLE_NUTRITIONISTS, null).moveToFirst();
    }

    public int getCountDishesWithType(DishType dishType){
        String selectQuery = "SELECT  * FROM " + TABLE_DISHES + " WHERE " + FIELD_DISH_TYPE_ID + " = " + dishType.getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int cnt = 0;
        if (c.moveToFirst()) {
            do {
                cnt++;
            } while (c.moveToNext());
        }
        c.close();
        return cnt;
    }

    //UPDATES
    public long updateChef(Chef chef){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_CHEF_NAME, chef.getName());
        values.put(FIELD_CHEF_SURNAME, chef.getSurname());
        values.put(FIELD_CHEF_PATRONYMIC, chef.getPatronymic());

        return db.update(TABLE_CHEFS, values, FIELD_CHEF_ID_KEY + " = ?",
                new String[] { String.valueOf(chef.getId()) });
    }

    public long updateWaiter(Waiter waiter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_WAITER_NAME, waiter.getName());
        values.put(FIELD_WAITER_SURNAME, waiter.getSurname());
        values.put(FIELD_WAITER_PATRONYMIC, waiter.getPatronymic());

        return db.update(TABLE_WAITERS, values, FIELD_WAITER_ID_KEY + " = ?",
                new String[] { String.valueOf(waiter.getId()) });
    }

    public long updateNutritionist(Nutritionist nutritionist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_NUTRITIONIST_NAME, nutritionist.getName());
        values.put(FIELD_NUTRITIONIST_SURNAME, nutritionist.getSurname());
        values.put(FIELD_NUTRITIONIST_PATRONYMIC, nutritionist.getPatronymic());

        return db.update(TABLE_NUTRITIONISTS, values, FIELD_NUTRITIONIST_ID_KEY + " = ?",
                new String[] { String.valueOf(nutritionist.getId()) });
    }

    public long updateDishType(DishType dishType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DISH_TYPE_NAME, dishType.getName());

        return db.update(TABLE_DISH_TYPE, values, FIELD_DISH_TYPE_ID_KEY+ " = ?",
                new String[] { String.valueOf(dishType.getId()) });
    }

    public long updateDish(Dish dish){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DISH_NAME, dish.getName());
        values.put(FIELD_DISH_PRICE, dish.getPrice());
        values.put(FIELD_DISH_WEIGHT, dish.getWeight());
        values.put(FIELD_DISH_PROTEINS, dish.getProteins());
        values.put(FIELD_DISH_FATS, dish.getFats());
        values.put(FIELD_DISH_CARBOHYDRATES, dish.getCarbohydrates());
        values.put(FIELD_DISH_CALORIES, dish.getCalories());
        values.put(FIELD_DISH_TYPE_ID, dish.getType().getId());
        values.put(FIELD_DISH_CHEF_ID, dish.getChef().getId());
        values.put(FIELD_DISH_MENU_ID, dish.getMenu().getId());

        return db.update(TABLE_DISHES, values, FIELD_DISH_ID_KEY + " = ?",
                new String[] { Integer.toString((int)dish.getId()) });
    }

    public void deleteIngredientFromDish(Dish dish, Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.rawQuery("SELECT  * FROM " + TABLE_INGREDIENT_IN_DISH
                + " WHERE " + FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID + " = " + ingredient.getId()
                + " AND " + FIELD_INGREDIENT_IN_DISH_DISH_ID + " = " + dish.getId(), null).moveToFirst()) {
            db.delete(TABLE_INGREDIENT_IN_DISH,FIELD_INGREDIENT_IN_DISH_DISH_ID + " = " + dish.getId()
                    + " AND " + FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID + " = " + ingredient.getId(), null);
        }
    }

    public long updateDishWithIngredients(Dish dish){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DISH_NAME, dish.getName());
        values.put(FIELD_DISH_PRICE, dish.getPrice());
        values.put(FIELD_DISH_WEIGHT, dish.getWeight());
        values.put(FIELD_DISH_PROTEINS, dish.getProteins());
        values.put(FIELD_DISH_FATS, dish.getFats());
        values.put(FIELD_DISH_CARBOHYDRATES, dish.getCarbohydrates());
        values.put(FIELD_DISH_CALORIES, dish.getCalories());
        values.put(FIELD_DISH_TYPE_ID, dish.getType().getId());
        values.put(FIELD_DISH_CHEF_ID, dish.getChef().getId());
        values.put(FIELD_DISH_MENU_ID, dish.getMenu().getId());

        for(Ingredient i : dish.getIngredients()) {
            //if there isn't this ingredient in this digh
            if(!db.rawQuery("SELECT  * FROM " + TABLE_INGREDIENT_IN_DISH
                    + " WHERE " + FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID + " = " + (int)i.getId()
                    + " AND " + FIELD_INGREDIENT_IN_DISH_DISH_ID + " = " + (int)dish.getId(), null).moveToFirst()){
                if(!db.rawQuery("SELECT  * FROM " + TABLE_INGREDIENTS
                        + " WHERE " + FIELD_INGREDIENT_ID_KEY + " = " + (int)i.getId(), null).moveToFirst())
                    createIngredient(i);
                if(!db.rawQuery("SELECT  * FROM " + TABLE_INGREDIENT_IN_DISH
                        + " WHERE " + FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID + " = " + (int)i.getId()
                        + " AND " + FIELD_INGREDIENT_IN_DISH_DISH_ID + " = " + (int)dish.getId(), null).moveToFirst()){
                    ContentValues v = new ContentValues();
                    v.put(FIELD_INGREDIENT_IN_DISH_DISH_ID, dish.getId());
                    v.put(FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID, i.getId());

                    db.insert(TABLE_INGREDIENT_IN_DISH, null, v);
                }
            }
        }

        return db.update(TABLE_DISHES, values, FIELD_DISH_ID_KEY + " = ?",
                new String[] { String.valueOf(dish.getId()) });
    }

    public long updateIngredient(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_INGREDIENT_NAME, ingredient.getName());
        values.put(FIELD_INGREDIENT_NUMBER, ingredient.getNumber());

        return db.update(TABLE_INGREDIENTS, values, FIELD_INGREDIENT_ID_KEY + " = ?",
                new String[] { String.valueOf(ingredient.getId()) });
    }

    public long updateMenu(MenuType menuType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_MENU_NAME, menuType.getName());

        return db.update(TABLE_MENU, values, FIELD_MENU_ID_KEY + " = ?",
                new String[] { String.valueOf(menuType.getId()) });
    }

    public long updateOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ORDER_TABLE_NUMBER, order.getTableNumber());
        values.put(FIELD_ORDER_LEAD_TIME, order.getLeadTime());
        values.put(FIELD_ORDER_TIME, order.getOrderTime().toString());
        values.put(FIELD_ORDER_PRICE, order.getPrice());
        values.put(FIELD_ORDER_REFERRED, (order.isReferred() ? 1 : 0));

        List<Dish> dishes = order.getDishes();
        List<Integer> numbers = order.getNumbers();
        List<Boolean> done = order.getDone();
        for(int i=0; i<dishes.size(); i++) {
            ContentValues v = new ContentValues();
            v.put(FIELD_DISH_IN_ORDER_ORDER_ID, order.getId());
            v.put(FIELD_DISH_IN_ORDER_DISH_ID, dishes.get(i).getId());
            v.put(FIELD_DISH_IN_ORDER_DISH_NUMB, numbers.get(i));
            v.put(FIELD_DISH_IN_ORDER_DISH_DONE, done.get(i));

            if(!db.rawQuery("SELECT  * FROM " + TABLE_DISH_IN_ORDER
                    + " WHERE " + FIELD_DISH_IN_ORDER_ORDER_ID + " = " + (int)order.getId()
                    + " AND " + FIELD_DISH_IN_ORDER_DISH_ID + " = " + (int)dishes.get(i).getId(), null).moveToFirst()){
                db.insert(TABLE_DISH_IN_ORDER, null, v);
            }
            else{
                db.update(TABLE_DISH_IN_ORDER, v, FIELD_DISH_IN_ORDER_ORDER_ID+"="+(int)order.getId()
                +" AND "+FIELD_DISH_IN_ORDER_DISH_ID+"="+(int)dishes.get(i).getId(),null);
            }
        }

        return db.update(TABLE_ORDERS, values, FIELD_ORDER_ID_KEY + " = ?",
                new String[] { String.valueOf(order.getId()) });
    }

    //DELETE
    public void deleteChef(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHEFS, FIELD_CHEF_ID_KEY + " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteWaiter(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WAITERS, FIELD_WAITER_ID_KEY + " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteNutritionist(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NUTRITIONISTS,FIELD_NUTRITIONIST_ID_KEY + " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteDishType(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISH_TYPE, FIELD_DISH_TYPE_ID_KEY+ " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteDish(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISHES,FIELD_DISH_ID_KEY + " = ?", new String[] { String.valueOf(id) });
        db.delete(TABLE_INGREDIENT_IN_DISH,FIELD_INGREDIENT_IN_DISH_DISH_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteIngredient(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENTS,FIELD_INGREDIENT_ID_KEY + " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU,FIELD_MENU_ID_KEY + " = ?", new String[] { String.valueOf(id) });
    }

    public void deleteOrder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, FIELD_ORDER_ID_KEY + " = ?", new String[] { String.valueOf(id) });
    }


    //GETERS
    public List<Chef> getAllChefs(){
        List<Chef> chefs = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHEFS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_CHEF_ID_KEY));
                String name = c.getString(c.getColumnIndex(FIELD_CHEF_NAME));
                String surname = c.getString(c.getColumnIndex(FIELD_CHEF_SURNAME));
                String patronymic = c.getString(c.getColumnIndex(FIELD_CHEF_PATRONYMIC));

                Chef chef = new Chef(name, surname, patronymic);
                chef.setId(id);

                chefs.add(chef);
            } while (c.moveToNext());
        }
        c.close();
        return chefs;
    }

    public Chef getChef(int id){
        Chef chef = null;
        String selectQuery = "SELECT  * FROM " + TABLE_CHEFS + " WHERE " + FIELD_CHEF_ID_KEY + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            long idChef = c.getInt(c.getColumnIndex(FIELD_CHEF_ID_KEY));
            String name = c.getString(c.getColumnIndex(FIELD_CHEF_NAME));
            String surname = c.getString(c.getColumnIndex(FIELD_CHEF_SURNAME));
            String patronymic = c.getString(c.getColumnIndex(FIELD_CHEF_PATRONYMIC));

            chef = new Chef(name, surname, patronymic);
            chef.setId(idChef);
        }
        c.close();
        return chef;
    }

    public List<Nutritionist> getAllNutritionists() {
        List<Nutritionist> nutritionists = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NUTRITIONISTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_NUTRITIONIST_ID_KEY));
                String name = c.getString(c.getColumnIndex(FIELD_NUTRITIONIST_NAME));
                String surname = c.getString(c.getColumnIndex(FIELD_NUTRITIONIST_SURNAME));
                String patronymic = c.getString(c.getColumnIndex(FIELD_NUTRITIONIST_PATRONYMIC));

                Nutritionist nutritionist = new Nutritionist(name, surname, patronymic);
                nutritionist.setId(id);

                nutritionists.add(nutritionist);
            } while (c.moveToNext());
        }
        c.close();
        return nutritionists;
    }

    public Nutritionist getNutritionist(int id) {
        Nutritionist nutritionist = null;
        String selectQuery = "SELECT  * FROM " + TABLE_NUTRITIONISTS + " WHERE " + FIELD_NUTRITIONIST_ID_KEY + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            long idN = c.getInt(c.getColumnIndex(FIELD_NUTRITIONIST_ID_KEY));
            String name = c.getString(c.getColumnIndex(FIELD_NUTRITIONIST_NAME));
            String surname = c.getString(c.getColumnIndex(FIELD_NUTRITIONIST_SURNAME));
            String patronymic = c.getString(c.getColumnIndex(FIELD_NUTRITIONIST_PATRONYMIC));

            nutritionist = new Nutritionist(name, surname, patronymic);
            nutritionist.setId(idN);
        }
        c.close();
        return nutritionist;
    }

    public List<Waiter> getAllWaiters() {
        List<Waiter> waiters = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_WAITERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_WAITER_ID_KEY));
                String name = c.getString(c.getColumnIndex(FIELD_WAITER_NAME));
                String surname = c.getString(c.getColumnIndex(FIELD_WAITER_SURNAME));
                String patronymic = c.getString(c.getColumnIndex(FIELD_WAITER_PATRONYMIC));

                Waiter waiter = new Waiter(name, surname, patronymic);
                waiter.setId(id);

                waiters.add(waiter);
            } while (c.moveToNext());
        }
        c.close();
        return waiters;
    }

    public Waiter getWaiter(int id) {
        Waiter waiter = null;
        String selectQuery = "SELECT  * FROM " + TABLE_WAITERS + " WHERE " + FIELD_WAITER_ID_KEY + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(FIELD_WAITER_NAME));
            String surname = c.getString(c.getColumnIndex(FIELD_WAITER_SURNAME));
            String patronymic = c.getString(c.getColumnIndex(FIELD_WAITER_PATRONYMIC));

            waiter = new Waiter(name, surname, patronymic);
            waiter.setId(id);
        }
        c.close();
        return waiter;
    }

    public List<DishType> getAllDishTypes(){
        List<DishType> dishTypes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISH_TYPE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_DISH_TYPE_ID_KEY));
                String name = c.getString(c.getColumnIndex(FIELD_DISH_TYPE_NAME));

                DishType dishType = new DishType(name);
                dishType.setId(id);

                dishTypes.add(dishType);
            } while (c.moveToNext());
        }
        c.close();
        return dishTypes;
    }

    public DishType getDishType(int id){
        DishType dishType = null;
        String selectQuery = "SELECT  * FROM " + TABLE_DISH_TYPE + " WHERE " + FIELD_DISH_TYPE_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            long idType = c.getInt(c.getColumnIndex(FIELD_DISH_TYPE_ID_KEY));
            String name = c.getString(c.getColumnIndex(FIELD_DISH_TYPE_NAME));

            dishType = new DishType(name);
            dishType.setId(idType);
        }
        c.close();
        return dishType;
    }

    public List<Dish> getAllDishes(){
        List<Dish> dishes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISHES + " ORDER BY " + FIELD_DISH_TYPE_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_DISH_ID_KEY));
                String name = c.getString(c.getColumnIndex(FIELD_DISH_NAME));
                int price = c.getInt(c.getColumnIndex(FIELD_DISH_PRICE));
                int weight = c.getInt(c.getColumnIndex(FIELD_DISH_WEIGHT));
                int proteins = c.getInt(c.getColumnIndex(FIELD_DISH_PROTEINS));
                int fats = c.getInt(c.getColumnIndex(FIELD_DISH_FATS));
                int carbohydrates = c.getInt(c.getColumnIndex(FIELD_DISH_CARBOHYDRATES));
                int calories = c.getInt(c.getColumnIndex(FIELD_DISH_CALORIES));
                int typeId = c.getInt(c.getColumnIndex(FIELD_DISH_TYPE_ID));
                int chefId = c.getInt(c.getColumnIndex(FIELD_DISH_CHEF_ID));
                int nutritionistId = c.getInt(c.getColumnIndex(FIELD_DISH_NUTRITIONIST_ID));
                int menuId = c.getInt(c.getColumnIndex(FIELD_DISH_MENU_ID));

                Chef chef = getChef(chefId);
                DishType dishType = getDishType(typeId);
                MenuType menuType = getMenuType(menuId);

                Nutritionist nutritionist = getNutritionist(nutritionistId);

                Dish dish = new Dish(name, price, weight, proteins, fats, carbohydrates, calories, chef, nutritionist, dishType, menuType );
                dish.setId(id);

                dishes.add(dish);
            } while (c.moveToNext());
        }
        c.close();
        return dishes;
    }

    public Dish getDish(int id){
        Dish dish = null;
        String selectQuery = "SELECT  * FROM " + TABLE_DISHES + " WHERE " + FIELD_DISH_ID_KEY + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            long idDish = c.getInt(c.getColumnIndex(FIELD_DISH_ID_KEY));
            String name = c.getString(c.getColumnIndex(FIELD_DISH_NAME));
            int price = c.getInt(c.getColumnIndex(FIELD_DISH_PRICE));
            int weight = c.getInt(c.getColumnIndex(FIELD_DISH_WEIGHT));
            int proteins = c.getInt(c.getColumnIndex(FIELD_DISH_PROTEINS));
            int fats = c.getInt(c.getColumnIndex(FIELD_DISH_FATS));
            int carbohydrates = c.getInt(c.getColumnIndex(FIELD_DISH_CARBOHYDRATES));
            int calories = c.getInt(c.getColumnIndex(FIELD_DISH_CALORIES));
            long typeId = c.getLong(c.getColumnIndex(FIELD_DISH_TYPE_ID));
            long chefId = c.getLong(c.getColumnIndex(FIELD_DISH_CHEF_ID));
            long nutritionistId = c.getLong(c.getColumnIndex(FIELD_DISH_CHEF_ID));
            long menuId = c.getLong(c.getColumnIndex(FIELD_DISH_MENU_ID));

            Chef chef = getChef((int)chefId);
            DishType dishType = getDishType((int)typeId);
            MenuType menuType = getMenuType((int)menuId);

            Nutritionist nutritionist = getNutritionist((int)nutritionistId);

            dish = new Dish(name, price, weight, proteins, fats, carbohydrates, calories, chef, nutritionist, dishType, menuType);
            dish.setId(idDish);

            String ingredientQuery = "SELECT  * FROM " + TABLE_INGREDIENT_IN_DISH + " WHERE " + FIELD_INGREDIENT_IN_DISH_DISH_ID + " = " + id;

            Cursor c2 = db.rawQuery(ingredientQuery, null);
            if (c2.moveToFirst()) {
                do {
                    int idIngredient = c2.getInt(c2.getColumnIndex(FIELD_INGREDIENT_IN_DISH_INGREDIENT_ID));
                    Ingredient ingredient = getIngredient(idIngredient);
                    dish.getIngredients().add(ingredient);
                } while (c2.moveToNext());
            }
            c2.close();
        }
        c.close();
        return dish;
    }

    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_INGREDIENT_ID_KEY));
                String name = c.getString(c.getColumnIndex(FIELD_INGREDIENT_NAME));
                int numb = c.getInt(c.getColumnIndex(FIELD_INGREDIENT_NUMBER));

                Ingredient ingredient = new Ingredient(name, numb);
                ingredient.setId(id);

                ingredients.add(ingredient);
            } while (c.moveToNext());
        }
        c.close();
        return ingredients;
    }

    public Ingredient getIngredient(int id){
        Ingredient ingredient = null;
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENTS + " WHERE " + FIELD_INGREDIENT_ID_KEY + " = "+ id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            long idIngr = c.getInt(c.getColumnIndex(FIELD_INGREDIENT_ID_KEY));
            String name = c.getString(c.getColumnIndex(FIELD_INGREDIENT_NAME));
            int numb = c.getInt(c.getColumnIndex(FIELD_INGREDIENT_NUMBER));

            ingredient = new Ingredient(name, numb);
            ingredient.setId(idIngr);
        }
        c.close();
        return ingredient;
    }

    public List<MenuType> getAllMenuTypes(){
        List<MenuType> menuTypes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENU;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_MENU_ID_KEY));
                String name = c.getString(c.getColumnIndex(FIELD_MENU_NAME));
                
                MenuType menuType = new MenuType(name);
                menuType.setId(id);

                menuTypes.add(menuType);
            } while (c.moveToNext());
        }
        c.close();
        return menuTypes;
    }

    public MenuType getMenuType(int id){
        MenuType menuType = null;
        String selectQuery = "SELECT  * FROM " + TABLE_MENU + " WHERE " + FIELD_MENU_ID_KEY + " = "+ id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            long idMenu = c.getInt(c.getColumnIndex(FIELD_MENU_ID_KEY));
            String name = c.getString(c.getColumnIndex(FIELD_MENU_NAME));

            menuType = new MenuType(name);
            menuType.setId(idMenu);
        }
        c.close();
        return menuType;
    }

    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(c.getColumnIndex(FIELD_ORDER_ID_KEY));
                orders.add(getOrder((int)id));
            } while (c.moveToNext());
        }
        c.close();
        return orders;
    }

    public Order getOrder(int id){
        Order order = null;
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE " + FIELD_ORDER_ID_KEY + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            int tableNumb = c.getInt(c.getColumnIndex(FIELD_ORDER_TABLE_NUMBER));
            String leadTime = c.getString(c.getColumnIndex(FIELD_ORDER_LEAD_TIME));
            String time = c.getString(c.getColumnIndex(FIELD_ORDER_TIME));
            int price = c.getInt(c.getColumnIndex(FIELD_ORDER_PRICE));
            int waiterId = c.getInt(c.getColumnIndex(FIELD_ORDER_WAITER_ID));
            boolean isReferred = c.getInt(c.getColumnIndex(FIELD_ORDER_REFERRED)) == 1;

            //Add waiter to order
            Waiter waiter = getWaiter(waiterId);

            //Create order
            order = new Order(tableNumb, leadTime,new Date( time), price, waiter, isReferred);
            order.setId(id);

            //Add dishes to order
            String ingredientQuery = "SELECT  * FROM " + TABLE_DISH_IN_ORDER + " WHERE " + FIELD_DISH_IN_ORDER_ORDER_ID + " = " + id;
            Cursor c2 = db.rawQuery(ingredientQuery, null);
            if (c2.moveToFirst()) {
                do {
                    int idDish = c2.getInt(c2.getColumnIndex(FIELD_DISH_IN_ORDER_DISH_ID));
                    int numb = c2.getInt(c2.getColumnIndex(FIELD_DISH_IN_ORDER_DISH_NUMB));
                    boolean done = c2.getInt(c2.getColumnIndex(FIELD_DISH_IN_ORDER_DISH_DONE)) == 1;
                    Dish dish = getDish(idDish);
                    order.getDishes().add(dish);
                    order.getNumbers().add(numb);
                    order.getDone().add(done);
                } while (c2.moveToNext());
            }
            c2.close();
        }
        c.close();
        return order;
    }

    public List<Order> getOrdersByChef(int idChef) {
        List<Order> orders = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISH_IN_ORDER
                + " INNER JOIN " + TABLE_DISHES + " ON " + TABLE_DISH_IN_ORDER + "." + FIELD_DISH_IN_ORDER_DISH_ID + " = " + TABLE_DISHES + "." + FIELD_DISH_ID_KEY
                + " INNER JOIN " + TABLE_ORDERS + " ON " + TABLE_DISH_IN_ORDER + "." + FIELD_DISH_IN_ORDER_ORDER_ID + " = " + TABLE_ORDERS + "." + FIELD_ORDER_ID_KEY
                + " WHERE " + TABLE_DISH_IN_ORDER + "." + FIELD_DISH_IN_ORDER_DISH_DONE + " = 0"
                + " AND " + TABLE_ORDERS + "." + FIELD_ORDER_REFERRED + " = 0"
                + " AND " + TABLE_DISHES + "." + FIELD_DISH_CHEF_ID + " = " + idChef
                + " ORDER BY " + TABLE_DISH_IN_ORDER + "." + FIELD_DISH_IN_ORDER_ORDER_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                int idOrder = c.getInt(c.getColumnIndex(FIELD_ORDER_ID_KEY));
                int tableNumb = c.getInt(c.getColumnIndex(FIELD_ORDER_TABLE_NUMBER));
                String leadTime = c.getString(c.getColumnIndex(FIELD_ORDER_LEAD_TIME));
                String time = c.getString(c.getColumnIndex(FIELD_ORDER_TIME));
                int price = c.getInt(c.getColumnIndex(FIELD_ORDER_PRICE));
                int waiterId = c.getInt(c.getColumnIndex(FIELD_ORDER_WAITER_ID));
                boolean isReferred = c.getInt(c.getColumnIndex(FIELD_ORDER_REFERRED)) == 1;
                Waiter waiter = getWaiter(waiterId);

                Order order = new Order(tableNumb, leadTime,new Date( time), price, waiter, isReferred);
                order.setId(idOrder);

                int idDish = c.getInt(c.getColumnIndex(FIELD_DISH_IN_ORDER_DISH_ID));
                int numb = c.getInt(c.getColumnIndex(FIELD_DISH_IN_ORDER_DISH_NUMB));
                boolean done = c.getInt(c.getColumnIndex(FIELD_DISH_IN_ORDER_DISH_DONE)) == 1;
                Dish dish = getDish(idDish);
                order.getDishes().add(dish);
                order.getNumbers().add(numb);
                order.getDone().add(done);

                orders.add(order);
            } while (c.moveToNext());
        }
        c.close();
        return orders;
    }

    public List<Order> getOrdersByWaiter(int idWaiter) {
        List<Order> orders = new ArrayList<>();
        String selectQuery = "SELECT " + FIELD_ORDER_ID_KEY + " FROM " + TABLE_ORDERS
                + " WHERE " + FIELD_ORDER_REFERRED + " = 0"
                + " AND " + FIELD_ORDER_WAITER_ID + " = " + idWaiter;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                int idOrder = c.getInt(c.getColumnIndex(FIELD_ORDER_ID_KEY));
                orders.add(getOrder(idOrder));
            } while (c.moveToNext());
        }
        c.close();
        return orders;
    }
}

















