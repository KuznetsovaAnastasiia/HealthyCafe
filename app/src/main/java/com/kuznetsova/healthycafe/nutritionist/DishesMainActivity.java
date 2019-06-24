package com.kuznetsova.healthycafe.nutritionist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.kuznetsova.healthycafe.database.DatabaseHandler;
import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.SimpleSectionedRecyclerViewAdapter;
import com.kuznetsova.healthycafe.entity.Dish;
import com.kuznetsova.healthycafe.entity.DishType;
import com.kuznetsova.healthycafe.entity.Nutritionist;

import java.util.ArrayList;
import java.util.List;

public class DishesMainActivity extends AppCompatActivity implements View.OnClickListener {

    //database
    private DatabaseHandler database;

    //request code
    private final int REQUEST_CODE_ADD_DISH = 0;
    private final int REQUEST_CODE_EDIT_DISH = 1;

    //views
    private RecyclerView mRecyclerView;
    private FloatingActionButton fabAdd;

    //adapters for recyclerView
    private DishesAdapter mAdapter;
    private SimpleSectionedRecyclerViewAdapter.Section[] dummy;

    //dish info
    private List<DishType> dishesTypes;
    private List<Dish> dishes;

    //authorized nutritionist
    private Nutritionist nutritionist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_main);

        //Settings for recyclerView
        mRecyclerView = findViewById(R.id.rv_dishes_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        //Settings for add button
        fabAdd = findViewById(R.id.fab_add_dish);
        fabAdd.setOnClickListener(this);

        //Connect to database
        database = DatabaseHandler.getInstance(getApplicationContext());

        //Get authorized nutritionist
        int id = getIntent().getIntExtra("nutritionist_id", 1);
        nutritionist = database.getNutritionist(id);
    }

    public void showDishes(){
        //Get dishes from database
        dishesTypes = database.getAllDishTypes();
        dishes = database.getAllDishes();

        //Create an adapter for dishes
        mAdapter = new DishesAdapter(this, dishes, (int)nutritionist.getId());

        //Provide a sectioned list for dishes types
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
        //Add sections
        int position = 0;
        for(int i = 0; i< dishesTypes.size(); i++) {
            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, dishesTypes.get(i).getName()));
            position += database.getCountDishesWithType(dishesTypes.get(i));
        }

        //Add an adapter to the sectionAdapter
        dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this,R.layout.title_section,R.id.section_text,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //refresh dishes info
        dishes = database.getAllDishes();
        showDishes();
    }

    @Override
    protected void onPause() {
        database.close();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        //Click add button
        if(v.getId() == R.id.fab_add_dish){
            Intent intent = new Intent(this, DishEditActivity.class);
            intent.putExtra("nutritionist_id", nutritionist.getId());
            startActivityForResult(intent, REQUEST_CODE_ADD_DISH);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Dish added
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_DISH){
            Toast.makeText(this, "Блюдо добавлено", Toast.LENGTH_LONG).show();
        }
    }
}
