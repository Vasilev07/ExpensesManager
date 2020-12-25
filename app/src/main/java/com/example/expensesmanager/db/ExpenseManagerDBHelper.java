package com.example.expensesmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseIncomeDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManagerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense_manager.db";
    private static final int DATABASE_VERSION = 4;
    public static final String EXPENSES_TABLE_NAME = "expenses";
    public static final String INCOME_TABLE_NAME = "income";
    public static final String UID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AMOUNT = "amount";

    public ExpenseManagerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " +
                EXPENSES_TABLE_NAME + "("+
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT NOT NULL," +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_AMOUNT + " REAL NOT NULL);";

        String SQL_CREATE_INCOME_TABLE = "CREATE TABLE " +
                INCOME_TABLE_NAME + "("+
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT NOT NULL," +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_AMOUNT + " REAL NOT NULL);";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
        db.execSQL(SQL_CREATE_INCOME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSES_TABLE_NAME );

        onCreate(db);
    }

    public void addExpenseOrIncomes(ExpenseIncomeDetails expenseIncomeDetails, Boolean isExpense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ExpenseManagerDBHelper.COLUMN_CATEGORY, expenseIncomeDetails.getCategory());
        values.put(ExpenseManagerDBHelper.COLUMN_DATE, expenseIncomeDetails.getDate());
        values.put(ExpenseManagerDBHelper.COLUMN_AMOUNT, expenseIncomeDetails.getAmount());

        if (isExpense) {
            db.insert(ExpenseManagerDBHelper.EXPENSES_TABLE_NAME, null, values);
        } else {
            db.insert(ExpenseManagerDBHelper.INCOME_TABLE_NAME, null, values);
        }
    }

    public Map<String, Expense> getAllExpensesOrIncomes(Boolean isExpense) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Expense> expenses = new HashMap<String, Expense>();

        String query;
        if (isExpense) {
            query = "SELECT" +
                    " * FROM " +
                    ExpenseManagerDBHelper.EXPENSES_TABLE_NAME +
                    " ORDER BY" +
                    " datetime(" +
                    ExpenseManagerDBHelper.COLUMN_DATE + ") DESC";
        } else {
            query = "SELECT" +
                    " * FROM " +
                    ExpenseManagerDBHelper.INCOME_TABLE_NAME +
                    " ORDER BY" +
                    " datetime(" +
                    ExpenseManagerDBHelper.COLUMN_DATE + ") DESC";
        }


        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseManagerDBHelper.UID));
            String category = cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_DATE));
            // potential bug with big amount
            String amount = cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_AMOUNT));

            String onlyDate = date.split(" ")[0];

            ExpenseIncomeDetails expenseIncomeDetails = new ExpenseIncomeDetails(category, Integer.parseInt(amount), date);

            if (expenses.get(onlyDate) != null && expenses.get(onlyDate).getSubExpenses().size() >= 0) {
                Expense currentExpense = expenses.get(onlyDate);
                currentExpense.increaseAmount(expenseIncomeDetails.getAmount());
                currentExpense.getSubExpenses().add(expenseIncomeDetails);
            } else {
                Expense expense = new Expense(date, Integer.parseInt(amount));
                expense.addExpense(expenseIncomeDetails);
                expenses.put(onlyDate, expense);
            }

        }

        return expenses;
    }
}
