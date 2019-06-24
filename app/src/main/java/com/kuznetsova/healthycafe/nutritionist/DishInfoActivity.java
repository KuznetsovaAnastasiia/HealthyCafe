package com.kuznetsova.healthycafe.nutritionist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kuznetsova.healthycafe.DatabaseHandler;
import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.SimpleSectionedRecyclerViewAdapter;
import com.kuznetsova.healthycafe.entity.Chef;
import com.kuznetsova.healthycafe.entity.Dish;

public class DishInfoActivity extends AppCompatActivity {

    private int REQUEST_CODE_EDIT = 0;

    private DatabaseHandler database;

    private TextView tvName;
    private TextView tvPrice;
    private TextView tvWeight;
    private TextView tvType;
    private TextView tvMenu;
    private TextView tvChef;
    private TextView tvCalories;
    private TextView tvProteins;
    private TextView tvFats;
    private TextView tvCarbohydrates;
    private RecyclerView rvIngredients;

    private IngredientsEditAdapter ingredientsEditAdapter;

    private Dish dish;
    private int dishID;

    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_info);

        database = DatabaseHandler.getInstance(getApplicationContext());

        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvWeight = findViewById(R.id.tv_weight);
        tvType = findViewById(R.id.tv_type);
        tvMenu = findViewById(R.id.tv_menu);
        tvChef = findViewById(R.id.tv_chef);
        tvCalories = findViewById(R.id.tv_caries);
        tvProteins = findViewById(R.id.tv_proteins);
        tvFats = findViewById(R.id.tv_fats);
        tvCarbohydrates = findViewById(R.id.tv_carbohydrates);
        rvIngredients = findViewById(R.id.rv_dishes_list_info);

        startIntent = getIntent();
        dishID = startIntent.getIntExtra("dish_id", -1);
        dish = database.getDish(dishID);


        setData();
    }

    @Override
    protected void onPause() {
        database.close();
        super.onPause();
    }

    public void setData(){
        tvName.setText(dish.getName());
        tvWeight.setText("Вес: " + Integer.toString(dish.getWeight()) + " г.");
        tvPrice.setText("Цена: " + Integer.toString(dish.getPrice()) + " грн.");
        tvType.setText(dish.getType().getName());
        tvMenu.setText(dish.getMenu().getName());

        Chef chef = dish.getChef();
        if(chef.getName().equals(""))
            tvChef.setText("-");
        else
            tvChef.setText("Повар: " + chef.getSurname() + " " + chef.getName().toCharArray()[0] + ". " + chef.getPatronymic().toCharArray()[0] + ".");

        tvCalories.setText(Integer.toString(dish.getCalories()));
        tvProteins.setText(Integer.toString(dish.getProteins()));
        tvFats.setText(Integer.toString(dish.getFats()));
        tvCarbohydrates.setText(Integer.toString(dish.getCarbohydrates()));

        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        rvIngredients.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        ingredientsEditAdapter = new IngredientsEditAdapter(this, dish.getIngredients(), null,false,dishID);
        rvIngredients.setAdapter(new SimpleSectionedRecyclerViewAdapter(this,R.layout.title_section,R.id.section_text,ingredientsEditAdapter));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_dish_info, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_edit:
                Intent intent = new Intent(this, DishEditActivity.class);
                intent.putExtra("dish_id", dishID);
                intent.putExtra("nutritionist_id", startIntent.getIntExtra("nutritionist_id", -1));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case R.id.menu_item_delete:
                database.deleteDish(dishID);
                Toast.makeText(this, "Блюдо удалено", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //todo refresh dish
        dish = database.getDish(dishID);
        setData();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
