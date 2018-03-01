package com.adc.takehomeexam.adcexam.dataaccessobject;

import android.util.Log;

import com.adc.takehomeexam.adcexam.model.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mark Ferdie Catabona on 27/02/2018.
 */

public class EmployeeDAO {

    public interface EmployeeDaoCallback {
        void getEmployees(List<Employee> list);
    }

    public interface AddEmployeeCallback {
        void duplicateEmail(String email);
        void insertSuccess();
        void insertError();

    }

    private DatabaseReference dbRef;
    private EmployeeDaoCallback employeeDaoCallback;
    private AddEmployeeCallback addEmployeeCallback;

    private final String TAG = EmployeeDAO.class.getSimpleName();

    public EmployeeDAO () {
        initFirebaseDb();
    }

    public void setEmployeeDaoCallback(EmployeeDaoCallback employeeDaoCallback) {
        this.employeeDaoCallback = employeeDaoCallback;
    }

    public void setAddEmployeeCallback(AddEmployeeCallback addEmployeeCallback) {
        this.addEmployeeCallback = addEmployeeCallback;
    }


    private void initFirebaseDb() {
        FirebaseDatabase dbInstance = FirebaseDatabase.getInstance();
        dbRef = dbInstance.getReference();
    }

    public void fetchAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        dbRef.child("employee")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Employee employee = snapshot.getValue(Employee.class);
                            employeeList.add(employee);
                        }
                        Collections.reverse(employeeList);
                        employeeDaoCallback.getEmployees(employeeList);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i(TAG,databaseError.getMessage());
                    }
                });
    }

    public void addEmployee(Employee employee) {
        // Validate if duplicate email address.

        dbRef.child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean hasDuplicate = false;
                String duplicateEmail = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee emp = snapshot.getValue(Employee.class);
                    if(employee.getEmail().equals(emp.getEmail())) {
                        hasDuplicate = true;
                        duplicateEmail = emp.getEmail();
                        break;
                    }
                }

                if(hasDuplicate) {
                    addEmployeeCallback.duplicateEmail(duplicateEmail);
                } else {
                    dbRef.child("employee").push().setValue(employee, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError == null) {
                                addEmployeeCallback.insertSuccess();
                            } else {
                                addEmployeeCallback.insertError();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeEmployee(String uid) {}

    public void updateEmployee(Employee employee) {}
}
