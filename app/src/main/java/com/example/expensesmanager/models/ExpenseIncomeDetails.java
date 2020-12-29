package com.example.expensesmanager.models;

import java.io.Serializable;

public class ExpenseIncomeDetails implements Serializable {
    private String category;
    private int amount;
    private String date;

    public ExpenseIncomeDetails(String category, int amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String  getDate() {
        return date;
    }
}

