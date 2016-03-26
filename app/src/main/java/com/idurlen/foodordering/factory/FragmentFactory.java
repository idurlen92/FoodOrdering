package com.idurlen.foodordering.factory;

import android.app.Fragment;




/**
 * @author Ivan Durlen
 */
public class FragmentFactory extends FactoryMethod{


	/**
	 * Creates Fragment for specified Option. Caches Fragments in Map.
	 * @param itemName name of Option in Navigation Drawer Menu.
	 * @return {@link Fragment}
	 */
	public static Fragment newInstance(String itemName){
		if(itemName.contains("/")) {
			itemName = itemName.substring(itemName.lastIndexOf("/") + 1);
		}

		Fragment fragment = null;

		try {
			Class fragmentClass = getComponentClass(itemName, "view.fragment", "Fragment");
			fragment = (Fragment) fragmentClass.newInstance();
		}
		catch (ClassNotFoundException e){
			logError(e, "Class not found");
		}
		catch(IllegalAccessException e) {
			logError(e, "No Constructor");
		}
		catch(Exception e){
			logError(e, "");
		}

		return  fragment;
	}


}
