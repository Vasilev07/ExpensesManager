package com.example.expensesmanager.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanager.R;
import com.example.expensesmanager.db.ExpenseManagerDBHelper;
import com.example.expensesmanager.models.ExpenseIncomeDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpenseIncomeActivity extends AppCompatActivity {
    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private Button saveExpense;
    private ExpenseManagerDBHelper expenseManagerDBHelper;
    private EditText expenseDate;
    private EditText expenseAmount;
    private EditText expenseCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);

        expenseManagerDBHelper = new ExpenseManagerDBHelper(this);

        Bundle data = getIntent().getExtras();
        final Boolean isExpense = data.getBoolean("expense");

        saveExpense = findViewById(R.id.save_expense);
        expenseDate = findViewById(R.id.dateEditText);
        expenseAmount = findViewById(R.id.amount_expense_text);
        expenseCategory = findViewById(R.id.expense_category_text);

        expenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField() ;
            }
        });

        saveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseIncomeDetails expenseIncomeDetails = new ExpenseIncomeDetails(
                        expenseCategory.getText().toString(),
                        Integer.parseInt(expenseAmount.getText().toString()),
                        expenseDate.getText().toString()
                        );
                expenseManagerDBHelper.addExpenseOrIncomes(expenseIncomeDetails, isExpense);
            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = dateSelected;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                expenseDate.setText(dateFormatter.format(dateSelected.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        expenseDate.setText(dateFormatter.format(dateSelected.getTime()));
    }
}
