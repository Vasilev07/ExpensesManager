package com.example.expensesmanager.viewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesmanager.R;
import com.example.expensesmanager.models.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    Context context;
    List<Expense> expenses;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public ExpenseAdapter(Context context, List<Expense> data) {
        this.context = context;
        this.expenses = data;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.expense_card, parent, false);

        return new ExpenseAdapter.ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        LinearLayoutManager childLayoutManager =
                new LinearLayoutManager(holder.child.getContext(), RecyclerView.VERTICAL, false);
        childLayoutManager.setInitialPrefetchItemCount(4);

        holder.child.setLayoutManager(childLayoutManager);
        holder.child.setAdapter(new ExpenseDetailsAdapter(context, expenses.get(position).getSubExpenses()));
        holder.child.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        RecyclerView child;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            child = itemView.findViewById(R.id.expense_card_details2);
        }
    }
}