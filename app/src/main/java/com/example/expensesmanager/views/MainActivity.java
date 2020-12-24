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

        data = new ArrayList<>(expenseManagerDBHelper.getAllExpenses().values());

        recyclerView = findViewById(R.id.expense_card);

        recyclerView.setAdapter(new ExpenseAdapter(this, data));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addExpense(View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void addIncome(View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }
}