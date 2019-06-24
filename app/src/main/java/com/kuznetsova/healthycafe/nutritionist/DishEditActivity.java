package com.kuznetsova.healthycafe.nutritionist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kuznetsova.healthycafe.database.DatabaseHandler;
import com.kuznetsova.healthycafe.entity.Chef;
import com.kuznetsova.healthycafe.entity.Dish;
import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.entity.DishType;
import com.kuznetsova.healthycafe.entity.Ingredient;
import com.kuznetsova.healthycafe.entity.MenuType;
import com.kuznetsova.healthycafe.entity.Nutritionist;

import java.util.ArrayList;
import java.util.List;

public class DishEditActivity extends AppCompatActivity implements IngredientsEditAdapter.ListItemClickListener {

    private DatabaseHandler database;

    private EditText etName;
    private EditText etWeight;
    private EditText etCost;
    private EditText etCalories;
    private EditText etProteins;
    private EditText etFats;
    private EditText etCarbohydrates;
    private Spinner sType;
    private Spinner sMenu;
    private Spinner sChef;
    private Button btnAdd;

    private IngredientsEditAdapter ingredientsEditAdapter;

    private Toast toast;

    private Dish dish;

    private List<DishType> types;
    private List<MenuType> menu;
    private List<Chef> chefs;

    private List<String> typesNames;
    private List<String> menuNames;
    private List<String> chefsNames;

    private ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> menuAdapter;
    private ArrayAdapter<String> chefAdapter;

    private Nutritionist nutritionist;
    private int dishID;

    private Context context = this;
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);

        database = DatabaseHandler.getInstance(getApplicationContext());

        etName = findViewById(R.id.et_dish_name);
        etWeight = findViewById(R.id.et_dish_weight);
        etCost = findViewById(R.id.et_dish_cost);
        etCalories = findViewById(R.id.et_calories);
        etProteins = findViewById(R.id.et_proteins);
        etFats = findViewById(R.id.et_fats);
        etCarbohydrates = findViewById(R.id.et_carbohydrates);
        sType = findViewById(R.id.dish_type_spinner);
        sMenu = findViewById(R.id.dish_menu_spinner);
        sChef = findViewById(R.id.dish_chef_spinner);
        btnAdd = findViewById(R.id.btn_add_ingredients);
        RecyclerView rvIngredients = findViewById(R.id.rv_ingredients_list_edit);

        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        rvIngredients.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        types = database.getAllDishTypes();
        typesNames = new ArrayList<>();
        for(DishType dishType : types)
            typesNames.add(dishType.getName());

        menu = database.getAllMenuTypes();
        menuNames = new ArrayList<>();
        for(MenuType menuType : menu)
            menuNames.add(menuType.getName());

        chefs = database.getAllChefs();
        chefsNames = new ArrayList<>();
        for(Chef chef : chefs) {
            if(chef.getName().equals(""))
                chefsNames.add("-");
            else
                chefsNames.add(chef.getSurname() + " " + chef.getName().toCharArray()[0] + ". " + chef.getPatronymic().toCharArray()[0] + ".");
        }

        typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typesNames);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sType.setAdapter(typeAdapter);
        menuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menuNames);
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMenu.setAdapter(menuAdapter);
        chefAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chefsNames);
        chefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sChef.setAdapter(chefAdapter);

        startIntent = getIntent();
        nutritionist = database.getNutritionist(startIntent.getIntExtra("nutritionist_id", 1));
        dishID = startIntent.getIntExtra("dish_id",-1);
        if(dishID != -1) {
            dish = database.getDish(dishID);
            setInfo();
        }
        else {
            dish = new Dish();
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle(R.string.title_activity_create_dish);
        }

        ingredientsEditAdapter = new IngredientsEditAdapter(this, dish.getIngredients(),this, true, dishID);
        rvIngredients.setAdapter(new MenuTypeRecyclerViewAdapter(this,R.layout.title_section,R.id.section_text,ingredientsEditAdapter));


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.add_ingredient_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = mView.findViewById(R.id.et_ingredient);
                alertDialogBuilderUserInput
                    .setCancelable(true)
                    .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            if(!userInputDialogEditText.getText().toString().isEmpty()) {
                                ingredientsEditAdapter.add(new Ingredient(userInputDialogEditText.getText().toString(), 0), -1);
                            }
                        }
                    });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
    }

    @Override
    protected void onPause() {
        database.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_save){
            if(!checkFields()){
                if(toast != null)
                    toast.cancel();
                toast = Toast.makeText(this, R.string.error_fill_all_fields, Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                DishType dishType = types.get(sType.getSelectedItemPosition());
                Chef chef = chefs.get(sChef.getSelectedItemPosition());
                MenuType menuType = menu.get(sMenu.getSelectedItemPosition());

                dish.setName(etName.getText().toString());
                dish.setPrice(Integer.parseInt(etCost.getText().toString()));
                dish.setWeight(Integer.parseInt(etWeight.getText().toString()));

                int proteins;
                int fats;
                int carbohydrates;
                int calories;
                try {
                    proteins = Integer.parseInt(etProteins.getText().toString());
                    fats = Integer.parseInt(etFats.getText().toString());
                    carbohydrates = Integer.parseInt(etCarbohydrates.getText().toString());
                    calories = Integer.parseInt(etCalories.getText().toString());
                }catch (NumberFormatException e) {
                    proteins = 0;
                    fats = 0;
                    carbohydrates = 0;
                    calories = 0;
                }
                dish.setProteins(proteins);
                dish.setFats(fats);
                dish.setCarbohydrates(carbohydrates);
                dish.setCalories(calories);

                dish.setChef(chef);
                dish.setType(dishType);
                dish.setMenu(menuType);
                dish.setNutritionist(nutritionist);

                if(dishID == -1)
                    database.createDishWithIngredients(dish);
                else {
                    database.updateDishWithIngredients(dish);
                    toast = Toast.makeText(this, R.string.message_edit_dish, Toast.LENGTH_SHORT);
                    toast.show();
                }
                setResult(RESULT_OK);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkFields(){
        String field = etName.getText().toString();
        if(field.equals("") || field == null) return false;
        field = etWeight.getText().toString();
        if(field.equals("") || field == null) return false;
        field = etCost.getText().toString();
        if(field.equals("") || field == null) return false;
        return true;
    }

    private void setInfo() {
        etName.setText(dish.getName());
        etWeight.setText(Integer.toString(dish.getWeight()));
        etCost.setText(Integer.toString(dish.getPrice()));
        etCalories.setText(Integer.toString(dish.getCalories()));
        etProteins.setText(Integer.toString(dish.getProteins()));
        etFats.setText(Integer.toString(dish.getFats()));
        etCarbohydrates.setText(Integer.toString(dish.getCarbohydrates()));

        int j=-1;
        for(int i=0; i<typesNames.size(); i++)
            if(typesNames.get(i).equals(dish.getType().getName()))
                j=i;
        sType.setSelection(j);

        for(int i=0; i<menuNames.size(); i++)
            if(menuNames.get(i).equals(dish.getMenu().getName()))
                j=i;
        sMenu.setSelection(j);

        for(int i=0; i<chefsNames.size(); i++)
            if(chefsNames.get(i).equals(dish.getChef().getName()))
                j=i;
        sChef.setSelection(j);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        database.deleteIngredientFromDish(dish, dish.getIngredients().get(clickedItemIndex));
    }
}
