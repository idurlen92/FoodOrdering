package com.idurlen.foodordering.utils.async;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;




/**
 * Enables doing background operation in new thread, where no params should be transmitted, and where
 * {@link ProgressBar} is properly showed/hidden.
 * @author Ivan Durlen
 */
public class BackgroundTask extends AsyncTask{

	private BackgroundOperation operation;
	private ProgressBar progressBar;
	private View mainView;


	public BackgroundTask(ProgressBar progressBar, View mainView, BackgroundOperation operation){
		this.progressBar = progressBar;
		this.mainView = mainView;
		this.operation = operation;
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressBar.setVisibility(View.VISIBLE);
		mainView.setVisibility(View.GONE);
	}


	@Override
	protected Object doInBackground(Object[] params) {
		return operation.execInBackground();
	}


	@Override
	protected void onPostExecute(Object o) {
		super.onPostExecute(o);
		progressBar.setVisibility(View.GONE);
		mainView.setVisibility(View.VISIBLE);
		operation.execAfter(o);
	}

}
