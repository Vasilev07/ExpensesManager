package com.example.expensesmanager.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Expense {
    private String date;
    private String category;
    private int amount;
    private List<ExpenseDetails> subExpenses = new ArrayList<ExpenseDetails>();

    public Expense(String date, String category, int amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public void addExpense(ExpenseDetails expenseDetails) {
        this.subExpenses.add(expenseDetails);
    }

    public List<ExpenseDetails> getSubExpenses() {
        return subExpenses;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }
}