/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple implementation of list adapter.
 */
public class DataListAdapter extends ArrayAdapter<String> {
	LayoutInflater minflater;

	public DataListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = minflater.inflate(R.layout.data_list_cell, null);
		}

		((TextView) convertView.findViewById(R.id.name))
				.setText(getItem(position));

		return convertView;
	}
}