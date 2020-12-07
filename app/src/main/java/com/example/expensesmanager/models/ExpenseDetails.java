package com.example.expensesmanager.models;

public class ExpenseDetails {
    private String category;
    private double amount;

    public ExpenseDetails(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

