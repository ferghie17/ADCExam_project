package com.adc.takehomeexam.adcexam;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adc.takehomeexam.adcexam.model.Employee;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mark Ferdie Catabona on 27/02/2018.
 */

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {


    private List<Employee> list;

    public EmployeeListAdapter(List<Employee> list) {
        this.list = list;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee, parent, false);
        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        Employee employee = list.get(position);
        holder.tvFirstName.setText(employee.getFirstName());
        holder.tvLastName.setText(employee.getLastName());
        holder.tvEmail.setText(employee.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFirstname) TextView tvFirstName;
        @BindView(R.id.tvLastName) TextView tvLastName;
        @BindView(R.id.tvEmail) TextView tvEmail;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
