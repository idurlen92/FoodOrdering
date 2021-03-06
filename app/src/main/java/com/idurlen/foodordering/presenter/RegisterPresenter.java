package com.idurlen.foodordering.presenter;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.idurlen.foodordering.database.model.User;
import com.idurlen.foodordering.net.RestService;
import com.idurlen.foodordering.net.UsersRequest;
import com.idurlen.foodordering.utils.StringUtils;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.RegisterActivity;
import com.idurlen.foodordering.view.ui.DatePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * MVC Presenter component for RegisterActivity
 */
public class RegisterPresenter extends Presenter implements View.OnTouchListener, View.OnClickListener{

	private final String ERROR_EMPTY = "Ispunite ovo polje";
	private final String ERROR_EMAIL = "Nevažeća adresa pošte";
	private final String ERROR_PASSWORD = "Loznika mora biti barem 6 znakova";
	private final String ERROR_MATCH = "Lozinke se ne podudaraju";
	private final String MESSAGE_DATA_ERROR = "Podaci nisu ispravni, popravite greške";
	private final String MESSAGE_NETWORK_ERROR = "Mrežna greška";
	private final String MESSAGE_REGISTER_SUCCESS = "Uspješna registracija!";
	private final String MESSAGE_REGISTER_ERROR = "Greška u registraciji!";

	boolean isEmailValid = false;
	boolean isPassValid = false;
	boolean isPassRepValid = false;

	boolean isBirthDateFocused = false;

	int insertStatus = RestService.REST_NO_INSERT_UPDATE;

	String sBirthDate = "";

	DatePicker datePicker;

	Button bRegister;

	EditText etFirstName;
	EditText etLastName;
	EditText etEmail;
	EditText etPassword;
	EditText etPasswordRepeat;
	EditText etAddress;
	EditText etBirthDate;

	Spinner spCity;

	UsersRequest request;


	public RegisterPresenter(AppCompatActivity activity){
		super(activity);
		//TODO: check network state ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	}




	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("ATTACHED", "RegisterPresenter");
		setWidgets();
		setListeners();
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) { }


	@Override
	public void onStart() {
		super.onStart();

		request = new UsersRequest();
		datePicker = new DatePicker(getActivity(), new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				StringBuilder builder = new StringBuilder();
				builder.append(year + "-");
				builder.append(( (monthOfYear + 1) < 10 ? "0" : "") + (monthOfYear + 1) + "-");
				builder.append((dayOfMonth < 10 ? "0" : "") + dayOfMonth);
				sBirthDate = builder.toString();

				Log.d("Birthdate", sBirthDate);

				etBirthDate.setText(StringUtils.getDateString(year, monthOfYear, dayOfMonth, true, false));
			}
		});
	}


	@Override
	public void onPause() { }


	@Override
	public void onStop() {
		datePicker = null;
		request = null;
	}



	private void setWidgets(){
		RegisterActivity activity = (RegisterActivity) getActivity();

		bRegister = activity.getBRegister();
		etFirstName = activity.getEtFirstName();
		etLastName = activity.getEtLastName();
		etEmail = activity.getEtEmail();
		etPassword = activity.getEtPassword();
		etPasswordRepeat = activity.getEtPasswordRepeat();
		etAddress = activity.getEtAddress();
		etBirthDate = activity.getEtBirthDate();
		spCity = activity.getSCity();
	}


	private void clearError(EditText et, boolean isValid){
		TextInputLayout parentLayout = (TextInputLayout) et.getParent();
		if(isValid && parentLayout.getError() != null){
			parentLayout.setError(null);
		}
	}




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
		//etBirthDate.setOnTouchListener(this);
		bRegister.setOnClickListener(this);

		etBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!isBirthDateFocused && hasFocus){
					isBirthDateFocused = true;
					datePicker.showPicker();
				}
				else if(!hasFocus){
					isBirthDateFocused = false;
				}
			}
		});
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
				isAllFilledOut = (etCurrent.getText().length() > 0);
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
			User user = new User();
			user.setFirstName(etFirstName.getText().toString());
			user.setLastName(etLastName.getText().toString());
			user.setEmail(etEmail.getText().toString());
			user.setPassword(etPassword.getText().toString());
			user.setCity(spCity.getSelectedItem().toString());
			user.setAddress(etAddress.getText().toString());
			user.setBirthDate(sBirthDate);
			registerUser(user);
		}
		else {
			Snackbar.make(view, MESSAGE_DATA_ERROR, Snackbar.LENGTH_SHORT).show();
		}
	}


	private void registerUser(final User user){
		BackgroundTask task = new BackgroundTask(getActivity(), "Registracija", new BackgroundOperation() {
			@Override
			public Object execInBackground() {
				boolean isError = false;
				try{
					insertStatus = request.insert(user);
				}
				catch(Exception e){
					e.printStackTrace();
					isError = true;
				}
				return isError;
			}

			@Override
			public void execAfter(Object object) {
				boolean isError = (boolean) object;

				if(isError){
					Snackbar.make(bRegister, MESSAGE_NETWORK_ERROR, Snackbar.LENGTH_SHORT).show();
				}
				else if(insertStatus == RestService.REST_INSERT_UPDATE_ERROR || insertStatus == RestService.REST_NO_INSERT_UPDATE){
					Snackbar.make(bRegister, MESSAGE_REGISTER_ERROR, Snackbar.LENGTH_SHORT).show();
				}
				else{
					Snackbar.make(bRegister, MESSAGE_REGISTER_SUCCESS, Snackbar.LENGTH_SHORT).show();
					getActivity().finish();
				}
			}
		});

		task.execute();
	}


}