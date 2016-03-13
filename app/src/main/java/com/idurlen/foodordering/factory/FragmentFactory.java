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
	public static Fragment getInstance(String itemName){
		itemName = itemName.substring(itemName.lastIndexOf("/") + 1);
		Fragment fragment = null;

		try {
			Class fragmentClass = getComponentClass(itemName, "option", "view.fragment", Fragment.class);
			fragment = (Fragment) fragmentClass.newInstance();
		} catch (Exception e) {
			Log.e("REFLECTION", "Fragment class not found for " + itemName);
			e.printStackTrace();
		}

		return  fragment;
	}

}
