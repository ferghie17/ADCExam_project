package com.adc.takehomeexam.adcexam;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adc.takehomeexam.adcexam.model.Employee;
import com.adc.takehomeexam.adcexam.presenter.EmployeePresenter;
import com.adc.takehomeexam.adcexam.view.AddEmployeeView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

import butterknife.BindDrawable;

public class AddEmployeeActivity extends BaseViewActivity<EmployeePresenter> implements AddEmployeeView {
    @BindView(R.id.etFirstName) EditText etFirstname;
    @BindView(R.id.etLastName) EditText etLastName;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.btnSubmit) Button btnSubmit;

    @BindDrawable(android.R.drawable.presence_busy)
    Drawable mInvalidField;

    @BindDrawable(android.R.drawable.presence_online)
    Drawable mValidField;
    private ProgressDialog loading;

    @NonNull
    @Override
    protected EmployeePresenter createPresenter(@NonNull Context context) {
        return new EmployeePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        ButterKnife.bind(this);

        Observable<CharSequence> firstNameObservable = RxTextView.textChanges(etFirstname);
        firstNameObservable
                .map(this::isValidInput)
                .subscribe(isValid -> etFirstname.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, (isValid ? mValidField : mInvalidField), null));

        Observable<CharSequence> lastNameObservable = RxTextView.textChanges(etLastName);
        lastNameObservable
                .map(this::isValidInput)
                .subscribe(isValid -> etLastName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, (isValid ? mValidField : mInvalidField), null));

        Observable<CharSequence> emailObservable = RxTextView.textChanges(etEmail);
        emailObservable
                .map(this::isValidEmail)
                .subscribe(isValid -> etEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, (isValid ? mValidField : mInvalidField), null));
        Observable<Boolean> combinedObservables = Observable.combineLatest(firstNameObservable, lastNameObservable, emailObservable, (o1, o2, o3) -> isValidInput(o1) && isValidInput(o2) && isValidEmail(o3));
        combinedObservables.subscribe(isVisible -> btnSubmit.setVisibility(isVisible ? View.VISIBLE : View.GONE));
    }

    private boolean isValidInput(CharSequence value) {
        return !mPresenter.isNullOrEmpty(value.toString());
    }

    private boolean isValidEmail(CharSequence email) {
        return mPresenter.isEmailValid(email.toString());
    }


    @OnClick(R.id.btnSubmit)
    public void onSubmit(Button button) {
        mPresenter.addEmployee(setValues());
        loading = Helper.buildSpinnerDialog(this);
        loading.show();
    }

    private Employee setValues() {
        Employee employee = new Employee();
        employee.setFirstName(etFirstname.getText().toString());
        employee.setLastName(etLastName.getText().toString());
        employee.setEmail(etEmail.getText().toString());
        return employee;
    }

    @Override
    public void duplicateEntry(String email) {
        etEmail.setText("");
        showToast(email + " is already used. Please use another.");
        loading.dismiss();
    }

    @Override
    public void inputSuccess() {
        clearFields();
        showToast("Employee information has been saved.");
        loading.dismiss();
    }

    @Override
    public void inputError() {
        showToast("Error occured. Please try again.");
        loading.dismiss();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        loading.dismiss();
    }

    private void clearFields() {
        etLastName.setText("");
        etEmail.setText("");
        etFirstname.setText("");
    }
}
