package com.idurlen.foodordering.utils.async;

import android.content.Context;
import android.util.Log;

import com.idurlen.foodordering.controller.MainController;
import com.idurlen.foodordering.net.RestClient;

import java.util.List;




/**
 * @author Ivan Durlen
 */
public class DownloadThread extends Thread{

	List<Object> list;

	RestClient client;
	Runnable runnable;

	Context context;
	MainController controller;


	/**
	 * Thread that uses {@link RestClient} to download data.
	 * @param context
	 * @param client
	 * @param list
	 */
	public DownloadThread(Context context, MainController controller, RestClient client, List<Object> list){
		this.context = context;
		this.controller = controller;
		this.client = client;
		this.list = list;
		Log.d("DownloadThread", "Created");
	}


	@Override
	public void run() {
		try {
			Log.d("DownloadThread", "Downloading");
			for(Object obj : client.getAll()){
				list.add(obj);
			}
			Log.d("DownloadThread", "Download finished!");
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
			controller.stopAllThreads(this);
		}
	}

}
