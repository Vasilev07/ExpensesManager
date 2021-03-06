package com.example.expensesmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesmanager.R;
import com.example.expensesmanager.models.Expense;
import com.example.expensesmanager.views.ExpenseIncomeActivity;

import java.util.ArrayList;
import java.util.List;

public class ExpenseIncomeAdapter extends RecyclerView.Adapter<ExpenseIncomeAdapter.ExpenseViewHolder> {
    Context context;
    List<Expense> expenses = new ArrayList<>();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public ExpenseIncomeAdapter(Context context, List<Expense> data) {
        this.context = context;
        this.expenses = data;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.expense_card, parent, false);

        return new ExpenseIncomeAdapter.ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        LinearLayoutManager childLayoutManager =
                new LinearLayoutManager(holder.child.getContext(), RecyclerView.VERTICAL, false);
        childLayoutManager.setInitialPrefetchItemCount(4);
        setDate(holder.expenseDate, expenses.get(position).getDate());
        holder.totalAmount.setText(expenses.get(position).getAmount() + "");
        holder.child.setLayoutManager(childLayoutManager);
        holder.child.setAdapter(new ExpenseIncomeDetailsAdapter(context, expenses.get(position).getSubExpenses()));
        holder.child.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        RecyclerView child;
        TextView expenseDate;
        TextView totalAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            child = itemView.findViewById(R.id.expense_card_details2);
            expenseDate = itemView.findViewById(R.id.expense_date);
            totalAmount = itemView.findViewById(R.id.total_amount);
        }
    }

    public void setDate (TextView view, String date){
        view.setText(date);
    }
}
