package com.example.expensesmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseManagerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense_manager.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "expenses";
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
                TABLE_NAME + "("+
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT NOT NULL," +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_AMOUNT + " REAL NOT NULL);";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );

        onCreate(db);
    }

    public void addExpense(ExpenseDetails expenseDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ExpenseManagerDBHelper.COLUMN_CATEGORY, expenseDetails.getCategory());
        values.put(ExpenseManagerDBHelper.COLUMN_DATE, expenseDetails.getDate());
        values.put(ExpenseManagerDBHelper.COLUMN_AMOUNT, expenseDetails.getAmount());

        db.insert(ExpenseManagerDBHelper.TABLE_NAME, null, values);
    }

    public List<Expense> getAllExpenses() {
        List<Expense> result = new ArrayList<Expense>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT" +
                " * FROM " +
                ExpenseManagerDBHelper.TABLE_NAME +
                " ORDER BY" +
                " datetime(" +
                ExpenseManagerDBHelper.COLUMN_DATE + ") DESC";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseManagerDBHelper.UID));
            String category = cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_DATE));
            String amount = cursor.getString(cursor.getColumnIndex(ExpenseManagerDBHelper.COLUMN_AMOUNT));

            ExpenseDetails expenseDetails = new ExpenseDetails(category, Integer.parseInt(amount), date);
            Expense expense = new Expense(date, category, Integer.parseInt(amount));

            result.add(expense);
        }

        return result;
    }
}