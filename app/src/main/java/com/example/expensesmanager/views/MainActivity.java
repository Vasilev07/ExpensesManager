package com.example.expensesmanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.expensesmanager.R;
import com.example.expensesmanager.db.ExpenseManagerDBHelper;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.viewModels.ExpenseIncomeAdapter;

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

        data = new ArrayList<>(expenseManagerDBHelper.getAllExpensesOrIncomes().values());

        recyclerView = findViewById(R.id.expense_card);

        recyclerView.setAdapter(new ExpenseIncomeAdapter(this, data));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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