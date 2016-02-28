package com.idurlen.foodordering;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.idurlen.foodordering.utils.StringUtils;




public class AppController{

	/**
	 * Describes current context of Application.
	 */
	public enum AppContext {LOGIN, REGISTER, MAIN};

	private static AppContext currentContext = AppContext.LOGIN;

	// ---------------  Methods  ---------------
	public static AppContext getCurrentContext(){
		return currentContext;
	}


	public  static void changeActivity(Context context, AppContext appContext){
		changeActivity(context, appContext, null, false);
	}


	public  static void changeActivity(Context context, AppContext appContext, Bundle bundle){
		changeActivity(context, appContext, bundle, false);
	}


	public static void changeActivity(Context context, AppContext appContext, Bundle bundle, boolean clean){
		currentContext = appContext;
		String strClassPath = "com.idurlen.foodordering.view." +
				StringUtils.upperToName(appContext.toString()) + "Activity";
		try {
			Intent intent = new Intent(context, Class.forName(strClassPath));

			if(clean) {
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			}
			if(null != bundle) {
				intent.putExtra("data", bundle);
			}


			context.startActivity(intent);
		}
		catch(Exception e){
			Log.e("REFLECTION", "Class " + strClassPath + " not found");
			e.printStackTrace();
		}
	}


}