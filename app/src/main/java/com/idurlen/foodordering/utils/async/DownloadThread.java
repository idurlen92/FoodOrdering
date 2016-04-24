package com.idurlen.foodordering.utils.async;

import android.content.Context;

import com.idurlen.foodordering.net.RestClient;

import java.util.List;




/**
 * @author Ivan Durlen
 */
public class DownloadThread extends Thread{

	List<Object> list;

	boolean isErrorOccurred;

	String errorMessage;

	RestClient client;
	Runnable runnable;

	Context context;


	/**
	 * Thread that uses {@link RestClient} to download data.
	 * @param context
	 * @param client
	 * @param list
	 */
	public DownloadThread(Context context, RestClient client, List<Object> list){
		this.context = context;
		this.client = client;
		this.list = list;
		this.isErrorOccurred = false;
	}


	public void start(){
		runnable = new Runnable() {
			@Override
			public void run() {
				try {
					for(Object obj : client.getAll()){
						list.add(obj);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		};

		runnable.run();
	}



	public boolean isErrorOccurred(){
		return isErrorOccurred;
	}


}
