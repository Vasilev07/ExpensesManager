package com.example.expensesmanager.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanager.R;
import com.example.expensesmanager.db.ExpenseManagerDBHelper;
import com.example.expensesmanager.models.Category;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseIncomeDetails;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExpenseIncomeActivity extends AppCompatActivity {
    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private Button saveExpense;
    private Button deleteExpense;
    private ExpenseManagerDBHelper expenseManagerDBHelper;
    private EditText expenseDate;
    private EditText expenseAmount;
    private Spinner categoriesSpinner;
    private Switch isExpense;
    private List<Category> categories;
    private List<String> categoryNames;
    private boolean isEdit = false;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);

        expenseManagerDBHelper = new ExpenseManagerDBHelper(this);

        Bundle data = getIntent().getExtras();
        final Boolean isExpense = data.getBoolean("expense");
        final ExpenseIncomeDetails oldExpenseDetails = (ExpenseIncomeDetails) getIntent().getSerializableExtra("expenseDetails");

        this.isExpense = findViewById(R.id.isExpense);
        this.isExpense.setChecked(!isExpense);

        saveExpense = findViewById(R.id.save_expense);
        deleteExpense = findViewById(R.id.delete_expense);
        expenseDate = findViewById(R.id.dateEditText);
        expenseAmount = findViewById(R.id.amount_expense_text);
        categoriesSpinner = (Spinner) findViewById(R.id.categories_spinner);

        this.categories = expenseManagerDBHelper.getAllCategories(isExpense);

        this.isExpense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    categories =  expenseManagerDBHelper.getAllCategories(false);
                }
                else
                {
                    categories =  expenseManagerDBHelper.getAllCategories(true);
                }
                categoryNames = getCategoryNames();
                setDropdownOptions(categoryNames);
            }
        });

        this.categoryNames = getCategoryNames();

        this.setDropdownOptions(categoryNames);

        if (oldExpenseDetails.getDate() != null && oldExpenseDetails.getAmount() != 0 && oldExpenseDetails.getCategory() != null) {
            this.expenseDate.setText(oldExpenseDetails.getDate());
            this.expenseAmount.setText(oldExpenseDetails.getAmount() + "");
            int spinnerPosition = itemsAdapter.getPosition(oldExpenseDetails.getCategory());
            categoriesSpinner.setSelection(spinnerPosition);
            this.isEdit = true;
        }

        saveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseIncomeDetails newExpenseIncomeDetails = new ExpenseIncomeDetails(
                        categoriesSpinner.getSelectedItem().toString(),
                        Integer.parseInt(expenseAmount.getText().toString()),
                        expenseDate.getText().toString()
                        );
                if (isEdit) {
                    expenseManagerDBHelper.updateExpenseOrIncome(oldExpenseDetails, newExpenseIncomeDetails, isExpense);
                    isEdit = false;
                } else {
                    expenseManagerDBHelper.addExpenseOrIncomes(newExpenseIncomeDetails, isExpense);
                }

                finish();
            }
        });

        deleteExpense.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ConfirmationDialog dialog  = new ConfirmationDialog();

                dialog.show(getSupportFragmentManager(), "ConfirmationDialog");
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

    private void setDropdownOptions(List<String> categoryNames) {
        itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryNames);
        itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(itemsAdapter);

        expenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField() ;
            }
        });
    }

    private List<String> getCategoryNames() {
        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        return categoryNames;
    }
}
