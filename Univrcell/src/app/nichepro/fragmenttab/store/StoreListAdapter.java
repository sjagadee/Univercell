/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.R;



public class StoreListAdapter extends ArrayAdapter<String> {
	LayoutInflater minflater;

	public StoreListAdapter(Context context,
			LayoutInflater inflater, int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = minflater.inflate(R.layout.custom_list_cell,
					null);
		}

		((TextView) convertView.findViewById(R.id.name)).setText(getItem(position));
		

		return convertView;
	}
}
