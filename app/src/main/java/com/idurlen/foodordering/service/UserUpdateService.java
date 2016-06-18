package com.idurlen.foodordering.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.idurlen.foodordering.database.model.User;
import com.idurlen.foodordering.net.UsersRequest;
import com.idurlen.foodordering.utils.Messenger;




public class UserUpdateService extends Service {

	User changedUserData;
	UsersRequest usersRequest;

	Thread workerThread = null;


	@Override
	public void onCreate() {
		super.onCreate();

		changedUserData = (User) Messenger.getObject(Messenger.KEY_USER_DATA);
		Messenger.clearAll();

		if(workerThread == null){
			workerThread = spawnNewThread();
		}
		if(usersRequest == null){
			usersRequest = new UsersRequest();
		}

	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(workerThread != null && ! workerThread.isAlive()) {
			workerThread.start();
		}
		else if(workerThread != null){
			workerThread.interrupt();

			changedUserData = (User) Messenger.getObject(Messenger.KEY_USER_DATA);
			Messenger.clearAll();

			workerThread = spawnNewThread();
			workerThread.start();
		}

		return START_NOT_STICKY;
	}


	private Thread spawnNewThread(){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int updateResult = usersRequest.update(changedUserData);
					Log.d("UPDATE", updateResult > 0 ? "success" : "fail");
				}
				catch(Exception e){
					//TODO:
					e.printStackTrace();
				}
				UserUpdateService.this.stopSelf();
			}
		});
		return thread;
	}

}
