package com.example.expensesmanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.expensesmanager.R;
import com.example.expensesmanager.db.ExpenseManagerDBHelper;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.adapters.ExpenseIncomeAdapter;

import java.util.ArrayList;
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



        Switch isExpenseSwitch = findViewById(R.id.isExpense);
        Boolean isExpense = !isExpenseSwitch.getShowText();

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
}