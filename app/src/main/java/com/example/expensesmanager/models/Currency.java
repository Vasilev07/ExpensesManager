package com.example.expensesmanager.models;

public class Currency {
    private String name;
    private double rate;
    private String flagURL;

    public Currency(String name, double rate, String flagURL) {
        this.name = name;
        this.rate = rate;
        this.flagURL = flagURL;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getFlagURL() {
        return flagURL;
    }
}
