package com.example.expensesmanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensesmanager.R;
import com.example.expensesmanager.db.ExpenseManagerDBHelper;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.adapters.ExpenseIncomeAdapter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class
MainActivity extends AppCompatActivity {
    private List<Expense> data;
    private ExpenseManagerDBHelper expenseManagerDBHelper;
    public RecyclerView recyclerView;

    private String url = "https://api.exchangeratesapi.io/latest?base=BGN";
    private JSONObject requestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseManagerDBHelper = new ExpenseManagerDBHelper(this);

        Switch isExpenseSwitch = findViewById(R.id.isExpense);
        Boolean isExpense = !isExpenseSwitch.getShowText();

        isExpenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    data = new ArrayList<>(expenseManagerDBHelper.getAllExpensesOrIncomes(false).values());
                } else {
                    data = new ArrayList<>(expenseManagerDBHelper.getAllExpensesOrIncomes(true).values());
                }
            }
        });

        if (expenseManagerDBHelper.getAllCategories(isExpense).size() <= 0) {
            expenseManagerDBHelper.addInitialCategories();
        }

        data = new ArrayList<>(expenseManagerDBHelper.getAllExpensesOrIncomes(!isExpense).values());

        recyclerView = findViewById(R.id.expense_card);

        recyclerView.setAdapter(new ExpenseIncomeAdapter(this, data));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        isExpenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data = new ArrayList<>(expenseManagerDBHelper.getAllExpensesOrIncomes(!isChecked).values());

                recyclerView = findViewById(R.id.expense_card);

                recyclerView.setAdapter(new ExpenseIncomeAdapter(MainActivity.this, data));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                //code to do the HTTP request
                getCurrencyRates();
            }
        });
        thread.start();


    }

    private void getCurrencyRates() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestResponse = response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(objectRequest);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addExpense(View view) {
        Intent intent = new Intent(this, ExpenseIncomeActivity.class);
        intent.putExtra("expense", true);
        startActivity(intent);
    }

    public void addIncome(View view) {
        Intent intent = new Intent(this, ExpenseIncomeActivity.class);
        intent.putExtra("expense", false);
        startActivity(intent);
    }

    public void openCurrencyInfo(View view) {
        Gson gson = new Gson();
        Intent intent = new Intent(this, CurrencyInfoActivity.class);
        intent.putExtra("currency_data", gson.toJson(requestResponse));
        startActivity(intent);
    }
}