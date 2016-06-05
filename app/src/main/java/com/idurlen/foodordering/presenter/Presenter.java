package com.idurlen.foodordering.presenter;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;




/**
 * Presenter abstract class of MVP Presenter component.
 */
public abstract class Presenter{

	private boolean isActivated = false;

	private final String title;

	private Fragment fragment;
	private AppCompatActivity activity;


	/**
	 * Used when the Presenter's View is an Activity.
	 * @param activity
	 */
	public Presenter(AppCompatActivity activity){
		this.activity = activity;
		this.title = "";
	}


	/**
	 * Used when the Presenter's View is a Fragment.
	 * @param fragment
	 * @param title
	 */
	public Presenter(Fragment fragment, final String title){
		this.fragment = fragment;
		this.title = title;
	}


	// ############### Abstract methods ###############
	/**
	 * Called in Activity/Fragment {@code onCreate}
	 * @param savedInstanceState
	 */
	public abstract void onCreate(Bundle savedInstanceState);

	/**
	 * Called in Fragment {@code onActivityCreated}
	 * @param savedInstanceState
	 */
	public abstract void onActivityCreated(Bundle savedInstanceState);

	/**
	 * Called in Activity/Fragment {@code Pause}
	 */
	public abstract void onPause();

	/**
	 * Called in Activity/Fragment {@code onStop}
	 */
	public abstract void onStop();



	// ############### Implemented methods ###############
	/**
	 * @return {@link Fragment}
	 */
	protected Fragment getFragment(){ return fragment; }


	/**
	 * @return {@link AppCompatActivity}
	 */
	protected AppCompatActivity getActivity(){ return (fragment == null ? activity : (AppCompatActivity) fragment.getActivity()); }


	/**
	 * @return {@link Context}
	 */
	protected Context getApplicationContext(){ return getActivity().getApplicationContext(); }


	/**
	 * @return {@code boolean} true if the Presenter is activated
	 */
	protected boolean isActivated(){ return isActivated; }


	/**
	 * @param isActivated
	 */
	protected void setIsActivated(boolean isActivated){ this.isActivated = isActivated; }


	/**
	 * Called in Activity/Fragment {@code onStart}
	 */
	public void onStart(){
		if(!title.isEmpty()) {
			getActivity().getSupportActionBar().setTitle(title);
		}
	}

	/**
	 * Called in Activity/Fragment {@code onResume}
	 */
	public void onResume(){ }


	/**
	 * Called in Activity/Fragment {@code onDestroy}
	 */
	public void onDestroy(){
		this.fragment = null;
		this.activity = null;
	}


}
