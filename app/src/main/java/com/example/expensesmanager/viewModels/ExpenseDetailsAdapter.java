package com.example.expensesmanager.viewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesmanager.R;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.models.ExpenseDetails;

import java.util.List;

public class ExpenseDetailsAdapter extends RecyclerView.Adapter<ExpenseDetailsAdapter.ExpenseViewHolder> {
    List<ExpenseDetails> expenses;
    Context context;

    public ExpenseDetailsAdapter(Context ctx, List<ExpenseDetails> expenses) {
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
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        holder.singleExpenseCategory.setText(expenses.get(position).getCategory());
//        holder.singleExpenseAmount.setText((int) expenses.get(position).getAmount());
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
