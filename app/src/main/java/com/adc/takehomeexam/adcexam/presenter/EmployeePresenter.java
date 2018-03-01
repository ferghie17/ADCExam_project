package com.adc.takehomeexam.adcexam.presenter;

import android.text.TextUtils;

import com.adc.takehomeexam.adcexam.view.AddEmployeeView;
import com.adc.takehomeexam.adcexam.BasePresenter;
import com.adc.takehomeexam.adcexam.businessobject.EmployeeBO;
import com.adc.takehomeexam.adcexam.dataaccessobject.EmployeeDAO;
import com.adc.takehomeexam.adcexam.view.EmployeeListView;
import com.adc.takehomeexam.adcexam.model.Employee;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mark Ferdie Catabona on 27/02/2018.
 */

public class EmployeePresenter extends BasePresenter
        implements
        EmployeeDAO.EmployeeDaoCallback,
        EmployeeDAO.AddEmployeeCallback {

    private EmployeeDAO dao;
    private EmployeeBO bo;
    private AddEmployeeView employeeView;
    private EmployeeListView employeeListView;

    public EmployeePresenter(AddEmployeeView employeeView) {
        this.employeeView = employeeView;
        init();
    }

    public EmployeePresenter(EmployeeListView employeeListView) {
        this.employeeListView = employeeListView;
        init();
    }

    private void init() {
        dao = new EmployeeDAO();
        bo = new EmployeeBO(dao);
    }

    public void fetchAllEmployees() {
        dao.setEmployeeDaoCallback(this);
        bo.fetchAllEmployees();
    }

    public void addEmployee(Employee employee) {
        dao.setAddEmployeeCallback(this);
        bo.addEmployee(employee);
    }


    public boolean isNullOrEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void getEmployees(List<Employee> list) {
        employeeListView.displayEmployeeList(list);
    }

    @Override
    public void duplicateEmail(String email) {
        employeeView.duplicateEntry(email);
    }

    @Override
    public void insertSuccess() {
        employeeView.inputSuccess();
    }

    @Override
    public void insertError() {
        employeeView.inputError();
    }
}
