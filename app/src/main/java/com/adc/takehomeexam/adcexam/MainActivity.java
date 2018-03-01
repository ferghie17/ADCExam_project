package com.adc.takehomeexam.adcexam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.adc.takehomeexam.adcexam.model.Employee;
import com.adc.takehomeexam.adcexam.presenter.EmployeePresenter;
import com.adc.takehomeexam.adcexam.view.EmployeeListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseViewActivity<EmployeePresenter> implements EmployeeListView {
    private final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.btnShowAddEmployeePage) Button btnShowEmployeePage;
    @BindView(R.id.rvEmployees) RecyclerView rvEmployees;

    private EmployeeListAdapter adapter;
    private ProgressDialog loading;

    @NonNull
    @Override
    protected EmployeePresenter createPresenter(@NonNull Context context) {
        return new EmployeePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fetchData();
    }

    private void fetchData() {
        loading = Helper.buildSpinnerDialog(this);
        loading.show();
        mPresenter.fetchAllEmployees();
    }

    @OnClick(R.id.btnShowAddEmployeePage)
    public void showAddEmployeePage(Button button) {
        Intent intent = new Intent(this,AddEmployeeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!loading.isShowing()) {
            fetchData();
        }
    }

    @Override
    public void displayEmployeeList(List<Employee> list) {
        adapter = new EmployeeListAdapter(list);
        rvEmployees.setVisibility(View.VISIBLE);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvEmployees.setLayoutManager(linearLayoutManager);
        rvEmployees.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loading.dismiss();
    }
}
