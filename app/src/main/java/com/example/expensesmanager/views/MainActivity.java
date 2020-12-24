package com.example.expensesmanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.expensesmanager.R;
import com.example.expensesmanager.db.ExpenseManagerDBHelper;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseDetails;
import com.example.expensesmanager.viewModels.ExpenseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Expense> data;
    private ExpenseManagerDBHelper expenseManagerDBHelper;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseManagerDBHelper = new ExpenseManagerDBHelper(this);

        data = expenseManagerDBHelper.getAllExpenses();

        recyclerView = findViewById(R.id.expense_card);

        recyclerView.setAdapter(new ExpenseAdapter(this, data));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        database.close();
//        databaseHelper.close();
    }

    public void addExpense(View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void addIncome(View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

//    private List<Expense> addData() {
//        List<Expense> result = new ArrayList<Expense>();
//
//        Expense parent1 = new Expense(new Date(), "eating", 200);
//        parent1.addExpense(new ExpenseDetails( "fart1", 30, new Date()));
//        parent1.addExpense(new ExpenseDetails( "smoking12", 20, new Date()));
//        parent1.addExpense(new ExpenseDetails( "smoking12", 201, new Date()));
//        parent1.addExpense(new ExpenseDetails( "smoking14", 201, new Date()));
//        parent1.addExpense(new ExpenseDetails( "smoking15", 201, new Date()));
//
//        Expense parent2 = new Expense(new Date(System.currentTimeMillis()-24*60*60*1000), "eating2", 300);
//        parent2.addExpense(new ExpenseDetails("fart21", 301, new Date(System.currentTimeMillis()-24*60*60*1000)));
//        parent2.addExpense(new ExpenseDetails("smoking22", 202, new Date(System.currentTimeMillis()-24*60*60*1000)));
//        parent2.addExpense(new ExpenseDetails("smoking23", 11, new Date(System.currentTimeMillis()-24*60*60*1000)));
//
//        Expense parent3 = new Expense(new Date(System.currentTimeMillis()-((24*60*60*1000)*2)), "eating3", 300);
//        parent3.addExpense(new ExpenseDetails("fart31", 301, new Date(System.currentTimeMillis()-24*60*60*1000)));
//        parent3.addExpense(new ExpenseDetails("smoking31", 202, new Date(System.currentTimeMillis()-24*60*60*1000)));
//        parent3.addExpense(new ExpenseDetails("smoking32", 11, new Date(System.currentTimeMillis()-24*60*60*1000)));
//
//        result.add(parent1);
//        result.add(parent2);
//        result.add(parent3);
//
//        return result;
//    }
}