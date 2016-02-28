package com.idurlen.foodordering.controller;

import android.util.Log;

import com.idurlen.foodordering.AppController;
import com.idurlen.foodordering.utils.StringUtils;




public class ControllerFactory {


	public static Controller getInstance(){
		AppController.AppContext appContext = AppController.getCurrentContext();
		String strClassPath = "com.idurlen.foodordering.controller." +
				StringUtils.upperToName(appContext.toString()) + "Controller";

		Controller controller = null;
		try{
			Class controllerClass = Class.forName(strClassPath);
			controller = (Controller) controllerClass.newInstance();
			Log.d("REFLECTION", controller.getClass().toString());
		}
		catch(Exception e){
			Log.e("REFLECTION", "Class " + strClassPath + " not found");
			e.printStackTrace();
		}

		return  controller;
	}

}
