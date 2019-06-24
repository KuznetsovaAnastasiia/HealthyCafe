package com.kuznetsova.healthycafe.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kuznetsova.healthycafe.R;
import com.kuznetsova.healthycafe.chef.OrdersChefMainActivity;
import com.kuznetsova.healthycafe.database.DatabaseHandler;
import com.kuznetsova.healthycafe.entity.Chef;
import com.kuznetsova.healthycafe.entity.DishType;
import com.kuznetsova.healthycafe.entity.MenuType;
import com.kuznetsova.healthycafe.entity.Nutritionist;
import com.kuznetsova.healthycafe.entity.Waiter;
import com.kuznetsova.healthycafe.nutritionist.DishesMainActivity;
import com.kuznetsova.healthycafe.waiter.OrdersMainActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    //database
    private DatabaseHandler database;

    //buttons for debug
    private Button btnWaiter1;
    private Button btnWaiter2;
    private Button btnWaiter3;
    private Button btnChef1;
    private Button btnChef2;
    private Button btnNutritionist;

    //context for new intent
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connect to database
        database = DatabaseHandler.getInstance(getApplicationContext());

        //References for test buttons
        btnChef1 = findViewById(R.id.btn_chef1);
        btnChef2 = findViewById(R.id.btn_chef2);
        btnWaiter1 = findViewById(R.id.btn_waiter1);
        btnWaiter2 = findViewById(R.id.btn_waiter2);
        btnWaiter3 = findViewById(R.id.btn_waiter3);
        btnNutritionist = findViewById(R.id.btn_nutritionist);
        btnChef1.setOnClickListener(this);
        btnChef2.setOnClickListener(this);
        btnWaiter1.setOnClickListener(this);
        btnWaiter2.setOnClickListener(this);
        btnWaiter3.setOnClickListener(this);
        btnNutritionist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.btn_chef1:
                intent = new Intent(context, OrdersChefMainActivity.class);
                intent.putExtra("chef_id", 1);
                startActivity(intent);
                break;
            case R.id.btn_chef2:
                intent = new Intent(context, OrdersChefMainActivity.class);
                intent.putExtra("chef_id", 2);
                startActivity(intent);
                break;
            case R.id.btn_waiter1:
                intent = new Intent(context, OrdersMainActivity.class);
                intent.putExtra("waiter_id", 1);
                startActivity(intent);
                break;
            case R.id.btn_waiter2:
                intent = new Intent(context, OrdersMainActivity.class);
                intent.putExtra("waiter_id", 2);
                startActivity(intent);
                break;
            case R.id.btn_waiter3:
                intent = new Intent(context, OrdersMainActivity.class);
                intent.putExtra("waiter_id", 3);
                startActivity(intent);
                break;
            case R.id.btn_nutritionist:
                intent = new Intent(context, DishesMainActivity.class);
                intent.putExtra("nutritionist_id", 1);
                startActivity(intent);
                break;
        }
    }

}