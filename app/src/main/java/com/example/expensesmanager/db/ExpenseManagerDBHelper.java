package com.example.expensesmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensesmanager.models.Category;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseIncomeDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManagerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense_manager.db";
    private static final int DATABASE_VERSION = 5;

    public static final String EXPENSES_TABLE_NAME = "expenses";
    public static final String INCOME_TABLE_NAME = "income";
    public static final String CATEGORIES_TABLE_NAME = "categories";

    public static final String UID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AMOUNT = "amount";

    public static final String COLUMN_CATEGORY_NAME = "name";
    public static final String COLUMN_IS_EXPENSE = "isExpense";

    public ExpenseManagerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CATEGORIES_TABLE_NAME + "("+
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT NOT NULL," +
                COLUMN_IS_EXPENSE + " TEXT NOT NULL);";

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

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
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

    public void updateExpenseOrIncome(ExpenseIncomeDetails oldExpenseDetails, ExpenseIncomeDetails expenseIncomeDetails, Boolean isExpense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ExpenseManagerDBHelper.COLUMN_CATEGORY, expenseIncomeDetails.getCategory());
        values.put(ExpenseManagerDBHelper.COLUMN_DATE, expenseIncomeDetails.getDate());
        values.put(ExpenseManagerDBHelper.COLUMN_AMOUNT, expenseIncomeDetails.getAmount());

        String whereClause =
                ExpenseManagerDBHelper.COLUMN_DATE + " = ? AND " +
                ExpenseManagerDBHelper.COLUMN_AMOUNT + " = ? AND  " +
                ExpenseManagerDBHelper.COLUMN_CATEGORY + " = ? ";
        String[] whereArgs = new String[] {
                oldExpenseDetails.getDate(),
                String.valueOf(oldExpenseDetails.getAmount()),
                oldExpenseDetails.getCategory()
        };

        if (isExpense) {
            db.update(ExpenseManagerDBHelper.EXPENSES_TABLE_NAME, values, whereClause, whereArgs);
        } else {
            db.update(ExpenseManagerDBHelper.INCOME_TABLE_NAME, values, whereClause, whereArgs);
        }
    }

    public void addInitialCategories() {
        Category food = new Category("food", true);
        Category drinks = new Category("drinks", true);
        Category donation = new Category("donation", true);
        Category salary = new Category("salary", false);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        List<Category> initialCategories = new ArrayList<>();

        initialCategories.add(food);
        initialCategories.add(drinks);
        initialCategories.add(donation);
        initialCategories.add(salary);

        for (Category category : initialCategories) {
            values.put(ExpenseManagerDBHelper.COLUMN_CATEGORY_NAME, category.getName());
            values.put(ExpenseManagerDBHelper.COLUMN_IS_EXPENSE, category.getExpense().toString());

            db.insert(ExpenseManagerDBHelper.CATEGORIES_TABLE_NAME, null, values);
        }
    }

    public List<Category> getAllCategories(Boolean isExpenseFilter) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Category> categories = new ArrayList<>();

        Cursor cursor = db.query(
                ExpenseManagerDBHelper.CATEGORIES_TABLE_NAME,
                null,
                ExpenseManagerDBHelper.COLUMN_IS_EXPENSE + "=?",
                new String[]{isExpenseFilter.toString()},
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseManagerDBHelper.UID));
            String name = cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_CATEGORY_NAME));
            Boolean isExpense = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_IS_EXPENSE)));

            Category category = new Category(name, isExpense);
            categories.add(category);
        }

        return categories;
    }
}
