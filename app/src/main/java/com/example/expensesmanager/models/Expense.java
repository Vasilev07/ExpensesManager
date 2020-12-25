package com.example.expensesmanager.models;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String date;
    private int amount;
    private List<ExpenseIncomeDetails> subExpenses = new ArrayList<ExpenseIncomeDetails>();

    public Expense(String date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public void addExpense(ExpenseIncomeDetails expenseIncomeDetails) {
        this.subExpenses.add(expenseIncomeDetails);
    }

    public List<ExpenseIncomeDetails> getSubExpenses() {
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