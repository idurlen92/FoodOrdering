package com.idurlen.foodordering.view.fragment;


import android.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;




/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends com.google.android.gms.maps.MapFragment implements OnMapReadyCallback{


	public MapFragment() {
		getMapAsync(this);
	}




	@Override
	public void onMapReady(GoogleMap googleMap) {
		googleMap.setMyLocationEnabled(true);
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.
	}

}
