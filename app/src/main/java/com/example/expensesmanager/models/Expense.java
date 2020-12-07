package com.example.expensesmanager.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Expense {
    private Date date;
    private String category;
    private double amount;
    private List<ExpenseDetails> subExpenses = new ArrayList<ExpenseDetails>();

    public Expense(Date date, String category, double amount) {
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

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}