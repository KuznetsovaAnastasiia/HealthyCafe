package com.kuznetsova.healthycafe;

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

    //views
    private EditText etLogin;
    private EditText etPassword;
    private Button button;
    private TextView tvMessage;

    //buttons for debug
    private Button btnWaiter1;
    private Button btnWaiter2;
    private Button btnWaiter3;
    private Button btnChef1;
    private Button btnChef2;
    private Button btnNutritionist;

    //lists of logins and passwords
    private List<String> loginList;
    private List<String> passwordList;
    private List<String> encPasswordList;

    //context for new intent
    private Context context = this;

    //constants for encryption
    private final static int M = 7;
    private final static int N = 6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connect to database
        database = DatabaseHandler.getInstance(getApplicationContext());

        //Get reference to views
        etLogin = findViewById(R.id.login);
        etPassword = findViewById(R.id.password);
        button = findViewById(R.id.sign_in_button);
        tvMessage = findViewById(R.id.tv_login_message);

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

        //Fill database
        if(database.emptyTables()) {
            fillDatabase();
        }

        //Create lists of logins and passwords
        loginList = Arrays.asList("chef1", "chef2", "nutritionist", "waiter1", "waiter2", "waiter3");
//        passwordList = Arrays.asList("ivanov", "petrov", "orehov", "zhukov", "karpov", "nikonov");
        encPasswordList = Arrays.asList("jueqpu", "n2uspu", "iublnq", "xkqmpo", "jiavpo", "krhqhv2r");

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get entered login and password
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                //Create alphabet and matrix for encryption
                char alphabet[] = "abcdefghijklmnopqrstuvwxyz0123456789._*!, ".toCharArray();
                char [][]matrix = createMatrix(login.toCharArray(), alphabet);

                //If login and password are true, start new Activity
                if (loginList.contains(login) && encPasswordList.get(loginList.indexOf(login)).equals(encryptPassword(matrix, password.toCharArray()))) {
                    Intent intent;
                    Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show();
                    switch (login) {
                        case "nutritionist":
                            intent = new Intent(context, DishesMainActivity.class);
                            intent.putExtra("nutritionist_id", 1);
                            startActivity(intent);
                            break;
                        case "waiter1":
                            intent = new Intent(context, OrdersMainActivity.class);
                            intent.putExtra("waiter_id", 1);
                            startActivity(intent);
                            break;
                        case "waiter2":
                            intent = new Intent(context, OrdersMainActivity.class);
                            intent.putExtra("waiter_id", 2);
                            startActivity(intent);
                            break;
                        case "waiter3":
                            intent = new Intent(context, OrdersMainActivity.class);
                            intent.putExtra("waiter_id", 3);
                            startActivity(intent);
                            break;
                        case "chef1":
                            intent = new Intent(context, OrdersChefMainActivity.class);
                            intent.putExtra("chef_id", 1);
                            startActivity(intent);
                            break;
                        case "chef2":
                            intent = new Intent(context, OrdersChefMainActivity.class);
                            intent.putExtra("chef_id", 2);
                            startActivity(intent);
                            break;
                    }
                } else {
                    tvMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //create matrix for encrypted password
    private char[][] createMatrix(char [] key, char [] alphabet) {
        int k = 1;
        int len = key.length;
        char [][]matrix = new char[M][N];

        //add a key to the matrix
        k = 0;
        int i = 0, j = 0;
        while (k < len) {
            matrix[i][j] = key[k];
            j++;
            k++;
            if (j == N) {
                i++;
                j = 0;
            }
        }

        //add the alphabet
        k = 0;
        int n = j;
        if (n == N) {
            n = 0;
            i++;
        }

        //fill matrix
        for (int m = i; m < M; m++) {
            while (n < N) {
                while (matrixContains(matrix, alphabet[k]))
                    k++;
                matrix[m][n] = alphabet[k++];
                n++;
            }
            n = 0;
        }
        return matrix;
    }

    //search for symbol in matrix
    private boolean matrixContains(char [][]matrix, char symbol) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (matrix[i][j] == symbol)
                    return true;
        return false;
    }

    //find line by symbol in matrix
    private int findLineBySymbol(char [][]matrix, char symbol) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (matrix[i][j] == symbol)
                    return i;
        return -1;
    }

    //find column by symbol in matrix
    private int findColumnBySymbol(char [][]matrix, char symbol) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (matrix[i][j] == symbol)
                    return j;
        return -1;
    }

    //encrypt password by matrix
    private String encryptPassword(char [][]matrix, char[] password) {
        int len = password.length;
        char [] result;
        char [] pass;

        if (password.length % 2 != 0) {
            pass = new char[len+1];
            result = new char[len+1];
            pass[len] = ' ';
        }
        else {
            pass = new char[len];
            result = new char[len];
        }

        for(int i = 0; i < len; i++)
            pass[i] = password[i];

        for (int i = 0; i < len; i+=2) {
            int col1 = findColumnBySymbol(matrix, pass[i]),
                    col2 = findColumnBySymbol(matrix, pass[i+1]),
                    line1 = findLineBySymbol(matrix, pass[i]),
                    line2 = findLineBySymbol(matrix, pass[i+1]);

            if(col1 == -1 || col2 == -1 || line1 == -1 || line2 == -1)
                return "";
            if (line1 == line2) {
                result[i] = matrix[line1][(col1 + 1) % N];
                result[i + 1] = matrix[line2][(col2 + 1 ) % N];
            }
            else if (col1 == col2) {
                result[i] = matrix[(line1 + 1) % M][col1];
                result[i + 1] = matrix[(line2 + 1) % M][col2];
            }
            else {
                result[i] = matrix[line1][col2];
                result[i+1] = matrix[line2][col1];
            }
        }

        return String.valueOf(result);
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

    public void fillDatabase(){
        //fill with start data
        String [] chefs = getResources().getStringArray(R.array.Chefs);
        for(String chef : chefs) {
            String [] fio = chef.split(" ");
            database.createChef(new Chef(fio[1], fio[0], fio[2]));
        }

        String [] waiters = getResources().getStringArray(R.array.Waiters);
        for(String waiter : waiters) {
            String [] fio = waiter.split(" ");
            database.createWaiter(new Waiter(fio[1], fio[0], fio[2]));
        }

        database.createChef(new Chef("","",""));

        String [] nutritionists = getResources().getStringArray(R.array.Nutritionists);
        for(String nutritionist : nutritionists) {
            String [] fio = nutritionist.split(" ");
            database.createNutritionist(new Nutritionist(fio[1], fio[0], fio[2]));
        }

        String [] dt = getResources().getStringArray(R.array.DishesTypes);
        for(String type : dt)
            database.createDishType(new DishType(type));

        String [] mt = getResources().getStringArray(R.array.MenuTypes);
        for(String type : mt)
            database.createMenu(new MenuType(type));

        database.createChef(new Chef("","",""));
    }
}