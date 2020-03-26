package com.ayush.myexpendituretracker.Database.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush.myexpendituretracker.DAO.LastMonthExpenditure;
import com.ayush.myexpendituretracker.Database.MyExpenditureModel;
import com.ayush.myexpendituretracker.R;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    Context context;
    List<MyExpenditureModel> list;
    List<LastMonthExpenditure> lastMonthExpenditureList;

    public DashboardAdapter(Context context, List<MyExpenditureModel> model, List<LastMonthExpenditure> lastMonthExpenditures) {
        this.context = context;
        this.list = model;
        this.lastMonthExpenditureList = lastMonthExpenditures;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.my_expenditure_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.current_date.setText(list.get(position).getDate());
        holder.current_title.setText(list.get(position).getTitle());
        holder.current_amount.setText("Rs. "+list.get(position).getExpenditure());

        holder.last_date.setText(lastMonthExpenditureList.get(position).getDate());
        holder.last_title.setText(lastMonthExpenditureList.get(position).getTitle());
        holder.last_amount.setText("Rs. "+lastMonthExpenditureList.get(position).getExpenditure());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView current_date, last_date, current_title, last_title, current_amount, last_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            current_date = itemView.findViewById(R.id.current_date);
            current_title = itemView.findViewById(R.id.current_title);
            current_amount = itemView.findViewById(R.id.current_amount);
            last_date = itemView.findViewById(R.id.last_date);
            last_title = itemView.findViewById(R.id.last_title);
            last_amount = itemView.findViewById(R.id.last_amount);
        }
    }
}
