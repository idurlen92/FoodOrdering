package com.idurlen.foodordering.factory;

import android.app.Fragment;
import android.util.Log;




public class FragmentFactory extends FactoryMethod{


	public static Fragment getInstance(String itemName){
		Fragment fragment = null;

		try{
			Class fragmentClass = getComponentClass(itemName, "option", "fragment", "view.fragment");
			fragment = (Fragment) fragmentClass.newInstance();
			Log.d("REFLECTION", "Created: " + fragment.getClass().getSimpleName());
		}
		catch(Exception e){
			Log.e("REFLECTION", "Class not found");
			e.printStackTrace();
		}

		return  fragment;
	}

}
