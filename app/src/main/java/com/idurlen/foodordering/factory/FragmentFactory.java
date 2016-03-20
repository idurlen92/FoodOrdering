package com.idurlen.foodordering.factory;

import android.app.Fragment;
import android.util.Log;




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
			Class fragmentClass = getComponentClass(itemName, "option", "view.fragment", Fragment.class);
			fragment = (Fragment) fragmentClass.newInstance();
		}
		catch (IllegalAccessException e) {
			Log.e("REFLECTION", "Default constructor not found for Fragment class " + itemName);
			e.printStackTrace();
		}
		catch(Exception e){
			Log.e("REFLECTION", "Default constructor not found for Fragment class " + itemName);
			e.printStackTrace();
		}

		return  fragment;
	}


}
