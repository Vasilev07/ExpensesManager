package com.example.expensesmanager.models;

public class Category {
    private String name;
    private Boolean isExpense;

    public Category(String name, Boolean isExpense) {
        this.name = name;
        this.isExpense = isExpense;
    }

    public String getName() {
        return name;
    }

    public Boolean getExpense() {
        return isExpense;
    }
}
