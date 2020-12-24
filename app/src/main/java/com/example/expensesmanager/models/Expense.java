package com.example.expensesmanager.models;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String date;
    private int amount;
    private List<ExpenseDetails> subExpenses = new ArrayList<ExpenseDetails>();

    public Expense(String date, int amount) {
        this.date = date;
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

    public int getAmount() {
        return amount;
    }

    public void increaseAmount(int amount) {
        this.amount += amount;
    }
}