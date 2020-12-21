package com.example.expensesmanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.expensesmanager.R;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseDetails;
import com.example.expensesmanager.viewModels.ExpenseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Expense> data;
    public RecyclerView recyclerView;
    Button incomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = addData();

        recyclerView = findViewById(R.id.expense_card);

        recyclerView.setAdapter(new ExpenseAdapter(this, data));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        incomeBtn = (Button) findViewById(R.id.income);
        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private List<Expense> addData() {
        List<Expense> result = new ArrayList<Expense>();

        Expense parent1 = new Expense(new Date(), "eating", 200);
        parent1.addExpense(new ExpenseDetails( "fart1", 30));
        parent1.addExpense(new ExpenseDetails( "smoking2", 20));
        parent1.addExpense(new ExpenseDetails( "smoking22", 201));

        Expense parent2 = new Expense(new Date(System.currentTimeMillis()-24*60*60*1000), "eating2", 300);
        parent2.addExpense(new ExpenseDetails("fart2", 301));
        parent2.addExpense(new ExpenseDetails("smoking2", 202));
        parent2.addExpense(new ExpenseDetails("smoking23", 11));

        Expense parent3 = new Expense(new Date(System.currentTimeMillis()-((24*60*60*1000)*2)), "eating2", 300);
        parent3.addExpense(new ExpenseDetails("fart2", 301));
        parent3.addExpense(new ExpenseDetails("smoking2", 202));
        parent3.addExpense(new ExpenseDetails("smoking23", 11));

        result.add(parent1);
        result.add(parent2);
        result.add(parent3);

        return result;
    }
}