package com.example.expensesmanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.expensesmanager.R;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseDetails;
import com.example.expensesmanager.viewModels.ExpenseAdapter;
import com.example.expensesmanager.viewModels.ExpenseDetailsAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Expense> data;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = addData();

        recyclerView = findViewById(R.id.expense_card);

        recyclerView.setAdapter(new ExpenseAdapter(this, data));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Expense> addData() {
        List<Expense> result = new ArrayList<Expense>();

        Expense parent1 = new Expense(new Date(), "eating", 200);
        parent1.addExpense(new ExpenseDetails( "fart1", 30));
        parent1.addExpense(new ExpenseDetails( "smoking2", 20));
        parent1.addExpense(new ExpenseDetails( "smoking22", 201));

        Expense parent2 = new Expense(new Date(), "eating2", 300);
        parent2.addExpense(new ExpenseDetails("fart2", 301));
        parent2.addExpense(new ExpenseDetails("smoking2", 202));
        parent2.addExpense(new ExpenseDetails("smoking23", 11));

        Expense parent3 = new Expense(new Date(), "eating3", 300);
        parent3.addExpense(new ExpenseDetails("fart3", 301));


        result.add(parent1);
        result.add(parent2);
        result.add(parent3);

        return result;
    }
}