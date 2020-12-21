package com.example.expensesmanager.models;

public class ExpenseDetails {
    private String category;
    private int amount;

    public ExpenseDetails(String category, int amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }
}

