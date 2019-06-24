package com.kuznetsova.healthycafe.waiter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuznetsova.healthycafe.database.DatabaseHandler;
import com.kuznetsova.healthycafe.entity.Order;
import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.SimpleSectionedRecyclerViewAdapter;
import com.kuznetsova.healthycafe.entity.Waiter;

import java.util.List;

public class OrdersMainActivity extends AppCompatActivity implements OrderAdapter.ListItemClickListener {

    //database
    private DatabaseHandler database;

    //views
    private RecyclerView rvOrders;
    private FloatingActionButton fabAddOrder;

    //adapter for recyclerView
    private OrderAdapter orderAdapter;
    //list of orders
    private List<Order> orders;

    //current context
    private Context context;

    //authorized waiter
    private Waiter waiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_main);

        //Get reference to views
        rvOrders = findViewById(R.id.rv_all_orders_view);
        fabAddOrder = findViewById(R.id.fab_add_order);

        //Connect to database
        database = new DatabaseHandler(getApplicationContext());

        //Init context
        context = this;

        //Settings for recyclerView
        rvOrders.setHasFixedSize(true);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        //Get authorized waiter
        waiter = database.getWaiter(getIntent().getIntExtra("waiter_id", 1));

        //Add new order
        fabAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderAddActivity.class);
                intent.putExtra("waiter_id", (int)waiter.getId());
                startActivity(intent);
            }
        });
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
        orders = database.getOrdersByWaiter((int)waiter.getId());
        for(Order order : orders)
            order.checkReadiness();

        //Create an adapter for orders
        orderAdapter = new OrderAdapter(this, orders, this);

        //Add an adapter to the sectionAdapter
        rvOrders.setAdapter(new SimpleSectionedRecyclerViewAdapter(this,R.layout.title_section,R.id.section_text,orderAdapter));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Order is referred
        Order order = orders.get(clickedItemIndex);
        order.setReferred(true);
        database.updateOrder(order);
    }
}
