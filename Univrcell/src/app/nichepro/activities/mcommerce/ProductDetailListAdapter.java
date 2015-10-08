/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ProductDetailListAdapter extends BaseExpandableListAdapter {

	Context mContext;
	ArrayList<KeyValue> eMessageList;

	static class ViewHolder {

		/** The note view. */
		TextView name;

		TextView value;
		public void reset() {

		}
	}

	public ProductDetailListAdapter(Context context, ArrayList<KeyValue> message) {
		eMessageList = message;
		mContext = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		KeyValue child = eMessageList.get(groupPosition);
		return child.getChildren().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		String emsg = (String) getChild(groupPosition, childPosition);
		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.child_new_cell, null);
			holder = new ViewHolder();

			holder.name = ((TextView) convertView.findViewById(R.id.name));
			holder.value = ((TextView) convertView.findViewById(R.id.fame));
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.name.setText(emsg);
		holder.value.setText("some thing");
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		KeyValue em = eMessageList.get(groupPosition);
		int count = em.getChildren().size();
		return count;

	}

	@Override
	public Object getGroup(int groupPosition) {
		return eMessageList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (eMessageList != null) {
			return eMessageList.size();
		} else
			return 0;

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {

		KeyValue emsg = (KeyValue) eMessageList.get(groupPosition);

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.child_cell, null);
			holder = new ViewHolder();

			holder.name = ((TextView) convertView.findViewById(R.id.name));
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.name.setText(emsg.getParentName());

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}