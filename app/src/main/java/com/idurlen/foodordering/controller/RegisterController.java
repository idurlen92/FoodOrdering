package com.idurlen.foodordering.controller;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.utils.StringUtils;
import com.idurlen.foodordering.view.RegisterActivity;
import com.idurlen.foodordering.view.ui.DatePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * MVC Controller component for RegisterActivity
 */
public class RegisterController implements Controller{

	private final String ERROR_EMPTY = "Ispunite ovo polje";
	private final String ERROR_EMAIL = "Nevažeća adresa pošte";
	private final String ERROR_PASSWORD = "Loznika mora biti barem 6 znakova";
	private final String ERROR_MATCH = "Lozinke se ne podudaraju";
	private final String MESSAGE_ERROR = "Podaci nisu ispravni, popravite greške";

	boolean isEmailValid = false;
	boolean isPassValid = false;
	boolean isPassRepValid = false;

	DatePicker datePicker;

	Button bRegister;

	EditText etFirstName;
	EditText etLastName;
	EditText etEmail;
	EditText etPassword;
	EditText etPasswordRepeat;
	EditText etAddress;
	EditText etBirthDate;

	Spinner sCity;


	RegisterActivity activity;


	public RegisterController(Activity activity){
		this.activity = (RegisterActivity) activity;
	}


	@Override
	public void activate() {
		this.activity = (RegisterActivity) activity;
		Log.d("ATTACHED", "RegisterController");

		setWidgets();
		setListeners();
		datePicker = new DatePicker(activity, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				etBirthDate.setText(StringUtils.getDateString(year, monthOfYear, dayOfMonth, true, false));
			}
		});
	}


	private void setWidgets(){
		bRegister = activity.getBRegister();
		etFirstName = activity.getEtFirstName();
		etLastName = activity.getEtLastName();
		etEmail = activity.getEtEmail();
		etPassword = activity.getEtPassword();
		etPasswordRepeat = activity.getEtPasswordRepeat();
		etAddress = activity.getEtAddress();
		etBirthDate = activity.getEtBirthDate();
		sCity = activity.getSCity();
	}


	private void clearError(EditText et, boolean isValid){
		TextInputLayout parentLayout = (TextInputLayout) et.getParent();
		if(isValid && parentLayout.getError() != null){
			parentLayout.setError(null);
		}
	}


	@Override
	public void setListeners() {
		TextWatcher watcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }

			@Override
			public void afterTextChanged(Editable s) {
				if (etPassword.getText().hashCode() == s.hashCode()){
					isPassValid = s.toString().length() > 5;
					clearError(etPassword, isPassValid);
					Log.d("PASS(Rep)", isPassValid ? "Valid" : "Invalid");
				}
				else if (etPasswordRepeat.getText().hashCode() == s.hashCode()){
					isPassRepValid = (s.toString().compareTo(etPassword.getText().toString()) == 0);
					clearError(etPasswordRepeat, isPassRepValid);
					Log.d("PASS(Rep)", isPassRepValid ? "Valid" : "Invalid");
				}
				else if (etEmail.getText().hashCode() == s.hashCode()){
					isEmailValid = isEmailValid(s.toString());
					clearError(etEmail, isEmailValid);
					Log.d("EMAIL", isEmailValid ? "Valid" : "Invalid");
				}
			}// afterTextChanged(...)
		};

		etEmail.addTextChangedListener(watcher);
		etPassword.addTextChangedListener(watcher);
		etPasswordRepeat.addTextChangedListener(watcher);
		etBirthDate.setOnTouchListener(this);
		bRegister.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		if(v instanceof Button) {
			handleRegister(v);
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v instanceof EditText){
			EditText et = (EditText) v;
			if(et.getId() == R.id.etRegisterBirthDate) {
				datePicker.showPicker();
				return true;
			}
		}
 		return false;
	}


	private boolean isEmailValid(String str){
		String strPattern = "\\A[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*@" +
							"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z";
		Pattern pattern = Pattern.compile(strPattern);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}



	private boolean isAllDataFilled(){
		RelativeLayout rootLayout = (RelativeLayout) etFirstName.getParent().getParent();

		boolean isAllFilledOut = true;
		for(int i = 0; i < rootLayout.getChildCount(); i++){
			if(rootLayout.getChildAt(i) instanceof TextInputLayout){
				TextInputLayout tiLayout = (TextInputLayout) rootLayout.getChildAt(i);
				EditText etCurrent = (EditText) tiLayout.getChildAt(0);
				tiLayout.setError(etCurrent.getText().length() > 0 ? null : ERROR_EMPTY);
			}
		}//for

		return isAllFilledOut;
	}


	private void handleRegister(View view){
		boolean isDataValid = isAllDataFilled() && isEmailValid && isPassValid && isPassRepValid;
		if(etEmail.getText().length() > 0){
			((TextInputLayout) etEmail.getParent()).setError(isEmailValid ? null : ERROR_EMAIL);
		}
		if(etPassword.getText().length() > 0){
			((TextInputLayout) etPassword.getParent()).setError(isPassValid ? null : ERROR_PASSWORD);
		}
		if(etPasswordRepeat.getText().length() > 0){
			((TextInputLayout) etPasswordRepeat.getParent()).setError(isPassRepValid ? null : ERROR_MATCH);
		}

		if(isDataValid){
			Snackbar.make(view, "Ispravni podaci!", Snackbar.LENGTH_SHORT).show();
		}
		else {
			Snackbar.make(view, MESSAGE_ERROR, Snackbar.LENGTH_SHORT).show();
		}
	}

}