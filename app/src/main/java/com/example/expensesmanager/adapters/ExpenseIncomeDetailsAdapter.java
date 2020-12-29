package com.example.expensesmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesmanager.R;
import com.example.expensesmanager.models.ExpenseIncomeDetails;
import com.example.expensesmanager.views.ExpenseIncomeActivity;

import java.util.List;

public class ExpenseIncomeDetailsAdapter extends RecyclerView.Adapter<ExpenseIncomeDetailsAdapter.ExpenseViewHolder> {
    List<ExpenseIncomeDetails> expenses;
    Context context;

    public ExpenseIncomeDetailsAdapter(Context ctx, List<ExpenseIncomeDetails> expenses) {
        this.context = ctx;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_details, parent, false);

        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, final int position) {
        holder.singleExpenseCategory.setText(expenses.get(position).getCategory());
        holder.singleExpenseAmount.setText(expenses.get(position).getAmount() + "");
        holder.singleExpenseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExpenseIncomeActivity.class);
                intent.putExtra("expense", false);
                intent.putExtra("expenseDetails", new ExpenseIncomeDetails(
                        expenses.get(position).getCategory(),
                        expenses.get(position).getAmount(),
                        expenses.get(position).getDate()
                ));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView singleExpenseCategory;
        TextView singleExpenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {

            super(itemView);

            singleExpenseCategory = itemView.findViewById(R.id.single_expense_category);
            singleExpenseAmount = itemView.findViewById(R.id.single_expense_amount);
        }
    }
}
