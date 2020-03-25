package com.ayush.myexpendituretracker.Database.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush.myexpendituretracker.Database.MyExpenditureModel;
import com.ayush.myexpendituretracker.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyExpenditureModel> list;

    public DashboardAdapter(Context context, ArrayList<MyExpenditureModel> model){
        this.context = context;
        this.list = model;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.my_expenditure_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.date.setText(list.get(position).getDate());
        holder.current_exp.setText(list.get(position).getTitle());
        holder.last_exp.setText(list.get(position).getExpenditure());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, current_exp, last_exp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            current_exp = itemView.findViewById(R.id.current_exp);
            last_exp = itemView.findViewById(R.id.pre_exp);
        }
    }
}
