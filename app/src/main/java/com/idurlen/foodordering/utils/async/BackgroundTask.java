package com.idurlen.foodordering.utils.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;




/**
 * Enables doing background operation in new thread, where no params should be transmitted, and where
 * {@link ProgressBar} is properly showed/hidden.
 * @author Ivan Durlen
 */
public class BackgroundTask extends AsyncTask{

	private boolean isShowProgressDialog = false;
	private boolean isShowSpinner = true;

	private ProgressDialog progressDialog;
	private ProgressBar progressBar;
	private View mainView;

	private BackgroundOperation operation;
	private Context context;


	public BackgroundTask(ProgressBar progressBar, View mainView, BackgroundOperation operation){
		this.progressBar = progressBar;
		this.mainView = mainView;
		this.operation = operation;
		this.isShowSpinner = true;
	}


	/**
	 *
	 * @param operation
	 */
	public BackgroundTask(Context context, String dialogTitle, BackgroundOperation operation){
		this.context = context;
		this.operation = operation;
		this.isShowProgressDialog = true;
		this.isShowSpinner = false;

		progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle(dialogTitle);
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(isShowProgressDialog){
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}
		else if(isShowSpinner) {
			progressBar.setVisibility(View.VISIBLE);
			mainView.setVisibility(View.GONE);
		}
		operation.execBefore();
	}


	@Override
	protected Object doInBackground(Object[] params) {
		return operation.execInBackground();
	}


	@Override
	protected void onPostExecute(Object o) {
		super.onPostExecute(o);
		if(isShowProgressDialog){
			progressDialog.dismiss();
		}
		else if(isShowSpinner) {
			progressBar.setVisibility(View.GONE);
			mainView.setVisibility(View.VISIBLE);
		}
		operation.execAfter(o);
	}

}
