/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.store;

import android.os.Bundle;
import app.nichepro.activities.mcommerce.R;

import com.google.android.maps.MapActivity;

public class StoreMapActivity extends MapActivity {

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_map_screen);

	}
}
