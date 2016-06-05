package com.idurlen.foodordering.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.idurlen.foodordering.R;
import com.idurlen.foodordering.database.model.User;
import com.idurlen.foodordering.service.UserUpdateService;
import com.idurlen.foodordering.utils.AppSettings;
import com.idurlen.foodordering.utils.Messenger;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.view.SettingsActivity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;




/**
 * MVP Presenter component for SettingsActivity.
 */
public class SettingsPresenter extends Presenter{

	private final String ERROR_EMPTY = "Ispunite ovo polje";
	private final String ERROR_EMAIL = "Nevažeća adresa pošte";

	private final String MESSAGE_DATA_ERROR = "Podaci nisu ispravni, popravite greške";

	boolean isDataValid = true;

	private AppSettings settings;
	private SessionManager session;


	public SettingsPresenter(AppCompatActivity activity){
		super(activity);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		final SettingsActivity activity = (SettingsActivity) getActivity();

		TextWatcher watcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }

			@Override
			public void afterTextChanged(Editable s) {
				List<EditText> lEditTexts = new LinkedList<>();
				lEditTexts.add(activity.getEtFirstName());
				lEditTexts.add(activity.getEtLastName());
				lEditTexts.add(activity.getEtAddress());

				for(EditText et : lEditTexts){
					if(et.getText().hashCode() == s.hashCode()){
						isDataValid = setError((TextInputLayout) et.getParent(),
								(et.getText().toString().isEmpty() ? ERROR_EMPTY : null) );
					}
				}
			}
		};

		activity.getEtFirstName().addTextChangedListener(watcher);
		activity.getEtLastName().addTextChangedListener(watcher);
		activity.getEtAddress().addTextChangedListener(watcher);
	}


	@Override
	public void onPause() { }


	@Override
	public void onActivityCreated(Bundle savedInstanceState) { }


	@Override
	public void onStart() {
		super.onStart();

		settings = AppSettings.getInstance(getApplicationContext());
		session = SessionManager.getInstance(getApplicationContext());

		isDataValid = true;
		loadSettings();
	}


	@Override
	public void onStop() {
		saveSettings();

		settings = null;
		session = null;
	}


	private void loadSettings(){
		SettingsActivity activity = (SettingsActivity) getActivity();

		// ----- App settings -----
		activity.getSwAutoSync().setChecked(settings.isAutoSync());
		// ----- Data settings -----
		activity.getEtFirstName().setText(session.getFirstName());
		activity.getEtLastName().setText(session.getLastName());
		activity.getEtAddress().setText(session.getAddress());

		List<String> lCities = Arrays.asList(getActivity().getResources().getStringArray(R.array.cities));
		activity.getSpCity().setSelection(lCities.indexOf(session.getCity()));
	}


	private boolean setError(TextInputLayout layout, String message){
		layout.setError(message);
		return (message == null);
	}


	private void saveSettings(){
		SettingsActivity activity = (SettingsActivity) getActivity();

		// ---------- App settings ----------
		if(activity.getSwAutoSync().isChecked() != settings.isAutoSync()){
			settings.setAutoSync(activity.getSwAutoSync().isChecked());
		}

		// ---------- User settings ----------
		boolean isDataChanged = false;
		User user = new User();

		if(activity.getLayoutFirstName().getError() == null &&
				session.getFirstName().compareTo(activity.getEtFirstName().getText().toString()) != 0){
			isDataChanged = true;
			user.setFirstName(activity.getEtFirstName().getText().toString());
			session.setFirstName(user.getFirstName());
		}
		if(activity.getLayoutLastName().getError() == null &&
				session.getLastName().compareTo(activity.getEtLastName().getText().toString()) != 0){
			isDataChanged = true;
			user.setLastName(activity.getEtLastName().getText().toString());
			session.setLastName(user.getLastName());
		}
		if(activity.getLayoutAddress().getError() == null &&
				session.getAddress().compareTo(activity.getEtAddress().getText().toString()) != 0){
			isDataChanged = true;
			user.setAddress(activity.getEtAddress().getText().toString());
			session.setAddress(user.getAddress());
		}

		String sCity = (String) activity.getSpCity().getSelectedItem();
		Log.d("NEW CITY", sCity);
		if(session.getCity().compareTo(sCity) != 0){
			isDataChanged = true;
			user.setCity(sCity);
			session.setCity(user.getCity());
		}

		// ---------- Call service ----------
		if(isDataChanged) {
			user.setId(session.getUserId());

			Messenger.clearAll();
			Messenger.putObject(Messenger.KEY_USER_DATA, user);

			Intent intent = new Intent(getActivity(), UserUpdateService.class);
			getActivity().startService(intent);
		}
	}


}
