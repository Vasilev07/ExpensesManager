package com.example.expensesmanager.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanager.R;

public class ExpenseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_income);
    }
}
