package com.example.expensesmanager.models;

import java.util.Date;

public class ExpenseDetails {
    private String category;
    private int amount;
    private String date;

    public ExpenseDetails(String category, int amount, String date) {
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

