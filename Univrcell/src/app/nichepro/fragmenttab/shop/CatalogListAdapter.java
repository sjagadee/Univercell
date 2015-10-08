/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.model.CatalogResponseObject;

public class CatalogListAdapter extends ArrayAdapter<CatalogResponseObject> {
	LayoutInflater minflater;

	static class ViewHolder {

		TextView name;

		public void reset() {

		}
	}

	public CatalogListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = LayoutInflater.from(getContext());

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.custom_list_cell, null);
			holder = new ViewHolder();

			holder.name = ((TextView) convertView.findViewById(R.id.name));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CatalogResponseObject cro = getItem(position);

		holder.name.setText(cro.catalogName);

		return convertView;
	}
}
