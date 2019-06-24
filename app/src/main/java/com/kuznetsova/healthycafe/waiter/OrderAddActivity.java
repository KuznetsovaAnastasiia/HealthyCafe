package com.kuznetsova.healthycafe.waiter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kuznetsova.healthycafe.DatabaseHandler;
import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.SimpleSectionedRecyclerViewAdapter;
import com.kuznetsova.healthycafe.entity.Dish;
import com.kuznetsova.healthycafe.entity.Order;
import com.kuznetsova.healthycafe.entity.Waiter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderAddActivity extends AppCompatActivity implements View.OnClickListener, DishesInOrderAdapter.ListItemClickListener {

    //request code
    private final int REQUEST_CODE_ADD_DISH = 0;

    //database
    private DatabaseHandler database;

    //views
    private EditText etTableNumber;
    private EditText etLeadTime;
    private TextView tvWaiter;
    private TextView tvPrice;
    private Button btnAddDish;
    private RecyclerView rvDishes;

    //main order
    private Order order;

    //adapter for recyclerView
    private DishesInOrderAdapter dishesInOrderAdapter;

    //authorized waiter
    private Waiter waiter;

    //context
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_add);

        //Initialize views
        etTableNumber = findViewById(R.id.et_order_table_number);
        etLeadTime = findViewById(R.id.et_order_lead_time);
        tvWaiter = findViewById(R.id.tv_order_waiter);
        tvPrice = findViewById(R.id.tv_order_price);
        rvDishes = findViewById(R.id.rv_order_dishes);
        btnAddDish = findViewById(R.id.btn_order_add_dish);

        //Get reference to context
        context = this;

        //Set clock handler for button
        btnAddDish.setOnClickListener(this);

        //Settings for recyclerView
        rvDishes.setHasFixedSize(true);
        rvDishes.setLayoutManager(new LinearLayoutManager(this));
        rvDishes.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        //Connect to database
        database = DatabaseHandler.getInstance(getApplicationContext());

        //Get authorized waiter
        Intent intent = getIntent();
        int waiterId = intent.getIntExtra("waiter_id", -1);
        waiter = database.getWaiter(waiterId);

        //Init order
        order = new Order(waiter);

        //Set waiter
        tvWaiter.setText(waiter.getSurname() + " " + waiter.getName().toCharArray()[0] + ". " + waiter.getPatronymic().toCharArray()[0] + ".");
    }

    @Override
    protected void onStart() {
        super.onStart();
        showInfo();
    }

    @Override
    protected void onPause() {
        database.close();
        super.onPause();
    }

    public void showInfo(){
        //Create an adapter for dishes
        dishesInOrderAdapter = new DishesInOrderAdapter(this, order.getDishes(), order.getNumbers(), this);

        //Provide a sectioned list for dishes types
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Add an adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this,R.layout.title_section,R.id.section_text,dishesInOrderAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        rvDishes.setAdapter(mSectionedAdapter);

        //Set price
        onListItemClick(0);
    }

    @Override
    public void onClick(View v) {
        //Open Activity with dishes
        Intent intent = new Intent(this, DishesListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_DISH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            //Get added dish
            int id = data.getIntExtra("dish_id", -1);
            Dish dish = database.getDish(id);
            dishesInOrderAdapter.add(dish, -1);
//            Toast.makeText(this, dish.getName() + " ", Toast.LENGTH_SHORT).show();
        }
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
            if(!checkFields())
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show();
            else{
                order.setTableNumber(Integer.parseInt(etTableNumber.getText().toString()));
                order.setLeadTime(etLeadTime.getText().toString());
                order.setOrderTime(Calendar.getInstance().getTime());
                order.setReadiness(false);
                order.checkDone();
                for(int i=0; i<order.getDishes().size(); i++)
                    if(order.getDishes().get(i).getChef().getName().equals(""))
                        order.getDone().set(i, true);
                database.createOrderWithDishes(order);

                setResult(RESULT_OK);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkFields() {
        String field = etTableNumber.getText().toString();
        if(field.equals("") || field == null)
            return false;

        field = etLeadTime.getText().toString();
        if(field.equals("") || field == null)
            return false;

        if(dishesInOrderAdapter.getItemCount() == 0)
            return false;

        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        order.setPrice(dishesInOrderAdapter.calcPrice());
        tvPrice.setText(Integer.toString(order.getPrice()));
    }
}
