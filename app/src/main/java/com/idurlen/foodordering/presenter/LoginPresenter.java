package com.idurlen.foodordering.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.idurlen.foodordering.database.model.User;
import com.idurlen.foodordering.net.UsersRequest;
import com.idurlen.foodordering.utils.AppSettings;
import com.idurlen.foodordering.utils.SessionManager;
import com.idurlen.foodordering.utils.async.BackgroundOperation;
import com.idurlen.foodordering.utils.async.BackgroundTask;
import com.idurlen.foodordering.view.LoginActivity;
import com.idurlen.foodordering.view.MainActivity;
import com.idurlen.foodordering.view.RegisterActivity;




/**
 * MVC Presenter component for LoginActivity.
 */
public class LoginPresenter extends Presenter implements View.OnClickListener{

	private final String MSG_EMPTY = "Unesite vrijednost";
	private final String MSG_NOT_FOUND = "Krivo kor. ime ili lozinka";
	private final String MSG_NETWORK_ERROR = "Mrežna Greška";

	private EditText etUsername;
	private EditText etPassword;

	private TextInputLayout layoutUsername;
	private TextInputLayout layoutPassword;

	private AppSettings settings;
	private SessionManager sessionManager;

	private UsersRequest request;

	private User user = null;



	public LoginPresenter(AppCompatActivity activity){
		super(activity);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("ATTACHED", "LoginPresenter");
		findViews();
	}


	@Override
	public void onResume() { }

	@Override
	public void onPause() { }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) { }


	@Override
	public void onStart() {
		super.onStart();
		settings = AppSettings.getInstance(getApplicationContext());
		sessionManager = SessionManager.getInstance(getApplicationContext());
		request = new UsersRequest();
	}




	@Override
	public void onStop() {
		settings = null;
		sessionManager = null;
		request = null;
		user = null;
	}



	public void findViews() {
		LoginActivity activity = ((LoginActivity) getActivity());

		etUsername = activity.getETUsername();
		etPassword = activity.getETPassword();
		layoutUsername = activity.getLayoutUsername();
		layoutPassword = activity.getLayoutPassword();

		activity.getBLogin().setOnClickListener(this);
		activity.getTVRegister().setOnClickListener(this);
	}


	@Override
	public void onClick(View v){
		Log.d("View class: ", v.getClass().toString());
		if(v instanceof AppCompatTextView){
			Intent intent = new Intent(getActivity(), RegisterActivity.class);
			getActivity().startActivity(intent);
		}
		else{
			handleLogin();
		}
	}


	private void handleLogin(){
		final String sUsername = etUsername.getText().toString();
		final String sPassword = etPassword.getText().toString();

		layoutUsername.setError(sUsername.isEmpty() ? MSG_EMPTY : null);
		layoutPassword.setError(sPassword.isEmpty() ? MSG_EMPTY : null);

		if(!sUsername.isEmpty() && !sPassword.isEmpty()){
			BackgroundTask task = new BackgroundTask(getActivity(), "Prijava", new BackgroundOperation() {
				@Override
				public Object execInBackground() {
					boolean isError = false;
					try{
						user = request.getUser(sUsername, sPassword);
					}
					catch(Exception e){
						Log.e("REST", "error");
						e.printStackTrace();
						isError = true;
					}
					return isError;
				}

				@Override
				public void execAfter(Object object) {
					boolean isError = (boolean) object;
					if(isError) {
						Snackbar.make(layoutPassword, MSG_NETWORK_ERROR, Snackbar.LENGTH_SHORT).show();
					}
					else if(user == null){
						Snackbar.make(layoutPassword, MSG_NOT_FOUND, Snackbar.LENGTH_SHORT).show();
					}
					else {
						sessionManager.createSession(user);
						settings.setLastUserId(user.getId());
						Log.d("User ADDRESS:", user.getAddress());
						Log.d("Session ADDRESS:", sessionManager.getAddress());
						redirectToMain();
					}
				}
			});

			task.execute();
		}//if
	}


	private void redirectToMain(){
		Intent intent = new Intent(getActivity(), MainActivity.class);
		getActivity().startActivity(intent);
		getActivity().finish();
	}

}