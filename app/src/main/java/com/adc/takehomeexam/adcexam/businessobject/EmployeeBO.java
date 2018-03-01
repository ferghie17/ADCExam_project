package com.adc.takehomeexam.adcexam.businessobject;

import com.adc.takehomeexam.adcexam.model.Employee;
import com.adc.takehomeexam.adcexam.dataaccessobject.EmployeeDAO;

/**
 * Created by Mark Ferdie Catabona on 27/02/2018.
 */

public class EmployeeBO {

    private EmployeeDAO employeeDAO;

    public EmployeeBO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void fetchAllEmployees() {
        employeeDAO.fetchAllEmployees();
    }

    public void addEmployee(Employee employee) {
        employeeDAO.addEmployee(employee);
    }
    public void removeEmployee(String uid) {
        employeeDAO.removeEmployee(uid);
    }
    public void updateEmployee(Employee employees) {
        employeeDAO.updateEmployee(employees);
    }
}
