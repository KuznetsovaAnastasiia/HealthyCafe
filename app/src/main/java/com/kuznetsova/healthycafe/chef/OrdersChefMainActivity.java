package com.kuznetsova.healthycafe.chef;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kuznetsova.healthycafe.database.DatabaseHandler;
import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.nutritionist.MenuTypeRecyclerViewAdapter;
import com.kuznetsova.healthycafe.entity.Chef;
import com.kuznetsova.healthycafe.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersChefMainActivity extends AppCompatActivity implements OrderAdapter.ListItemClickListener {

    //database
    private DatabaseHandler database;

    //views
    private RecyclerView rvOrders;

    //adapter for recyclerView
    private OrderAdapter orderAdapter;
    //list of orders
    private List<Order> orders;

    //current context
    private Context context;

    //authorized chef
    private Chef chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_chef_main);

        //Get reference to view
        rvOrders = findViewById(R.id.rv_dishes_in_orders);

        //Connect to database
        database = DatabaseHandler.getInstance(getApplicationContext());

        //Init context
        context = this;

        //Settings for recyclerView
        rvOrders.setHasFixedSize(true);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        //Get authorized chef
        Intent intent = getIntent();
        chef = database.getChef(intent.getIntExtra("chef_id", 1));

        orders = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showOrders();
    }

    @Override
    protected void onPause() {
        database.close();
        super.onPause();
    }

    public void showOrders(){
        //Get orders from database
        orders = database.getOrdersByChef((int)chef.getId());

        //Create an adapter for orders
        orderAdapter = new OrderAdapter(this, chef, orders, this);

        //Add an adapter to the sectionAdapter
        rvOrders.setAdapter(new MenuTypeRecyclerViewAdapter(this,R.layout.title_section,R.id.section_text,orderAdapter));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Order referred
        Order order = orders.get(clickedItemIndex);
        for(int i=0; i<order.getDishes().size(); i++)
            order.getDone().set(i, true);
        order.checkReadiness();
        database.updateOrder(order);
    }
}
