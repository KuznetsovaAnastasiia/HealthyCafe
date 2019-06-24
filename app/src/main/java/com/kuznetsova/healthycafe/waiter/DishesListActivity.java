package com.kuznetsova.healthycafe.waiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kuznetsova.healthycafe.database.DatabaseHandler;
import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.nutritionist.MenuTypeRecyclerViewAdapter;
import com.kuznetsova.healthycafe.entity.Dish;
import com.kuznetsova.healthycafe.entity.DishType;

import java.util.ArrayList;
import java.util.List;

public class DishesListActivity extends AppCompatActivity implements DishesListAdapter.ListItemClickListener {

    //database
    private DatabaseHandler database;

    //views
    private RecyclerView mRecyclerView;

    //adapters for recyclerView
    private DishesListAdapter mAdapter;
    private MenuTypeRecyclerViewAdapter.Section[] dummy;

    //dish info
    private List<DishType> dishesTypes;
    private List<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_list);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Settings for recyclerView
        mRecyclerView = findViewById(R.id.rv_dishes_list_for_order);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        //Connect to database
        database = new DatabaseHandler(getApplicationContext());

        showDishes();
    }

    @Override
    protected void onPause() {
        database.close();
        super.onPause();
    }

    public void showDishes(){
        //Get dishes from database
        dishesTypes = database.getAllDishTypes();
        dishes = database.getAllDishes();

        //Create an adapter for dishes
        mAdapter = new DishesListAdapter(this, dishes, this);

        //Provide a sectioned list for dishes types
        List<MenuTypeRecyclerViewAdapter.Section> sections = new ArrayList<MenuTypeRecyclerViewAdapter.Section>();
        //Add sections
        int position = 0;
        for(int i = 0; i< dishesTypes.size(); i++) {
            sections.add(new MenuTypeRecyclerViewAdapter.Section(position, dishesTypes.get(i).getName()));
            position += database.getCountDishesWithType(dishesTypes.get(i));
        }

        //Add an adapter to the sectionAdapter
        dummy = new MenuTypeRecyclerViewAdapter.Section[sections.size()];
        MenuTypeRecyclerViewAdapter mSectionedAdapter = new
                MenuTypeRecyclerViewAdapter(this,R.layout.title_section,R.id.section_text,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent();
        intent.putExtra("dish_id", (int)dishes.get(clickedItemIndex).getId());
//        Toast.makeText(this, clickedItemIndex+" ", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, intent);
        finish();
    }
}
