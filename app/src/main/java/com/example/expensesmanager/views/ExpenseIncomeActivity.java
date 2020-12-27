package com.example.expensesmanager.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanager.R;
import com.example.expensesmanager.db.ExpenseManagerDBHelper;
import com.example.expensesmanager.models.Category;
import com.example.expensesmanager.models.ExpenseIncomeDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExpenseIncomeActivity extends AppCompatActivity {
    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private Button saveExpense;
    private ExpenseManagerDBHelper expenseManagerDBHelper;
    private EditText expenseDate;
    private EditText expenseAmount;
    private EditText expenseCategory;
    private Switch isExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);

        expenseManagerDBHelper = new ExpenseManagerDBHelper(this);

        Bundle data = getIntent().getExtras();
        final Boolean isExpense = data.getBoolean("expense");

        this.isExpense = findViewById(R.id.isExpense);
        this.isExpense.setChecked(!isExpense);

        saveExpense = findViewById(R.id.save_expense);
        expenseDate = findViewById(R.id.dateEditText);
        expenseAmount = findViewById(R.id.amount_expense_text);
//        expenseCategory = findViewById(R.id.expense_category_text);
        final Spinner categoriesSpinner = (Spinner) findViewById(R.id.categories_spinner);

        List<Category> categories = expenseManagerDBHelper.getAllCategories();
        List<String> categoryNames = new ArrayList<>();
        // sorry for that , too old Java -v
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryNames);
        itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(itemsAdapter);

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
                        categoriesSpinner.getSelectedItem().toString(),
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
