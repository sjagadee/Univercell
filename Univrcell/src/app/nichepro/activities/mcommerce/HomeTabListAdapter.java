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
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.nichepro.model.CatalogCategoryListResponseObject;
import app.nichepro.model.CatalogCategoryResponseObject;
import app.nichepro.model.CategorysResponseObject;

public class HomeTabListAdapter extends BaseExpandableListAdapter {

	Context mContext;
	CatalogCategoryListResponseObject mCCListData;

	static class ViewHolder {

		/** The note view. */
		TextView name;

		ImageView collapsable;
		ImageView expandable;
		RelativeLayout rLayout;

		public void reset() {

		}
	}

	public HomeTabListAdapter(Context context,
			CatalogCategoryListResponseObject listData) {
		mCCListData = listData;
		mContext = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		CatalogCategoryResponseObject child = (CatalogCategoryResponseObject) mCCListData.catalogCategoryList
				.get(groupPosition);
		CategorysResponseObject data = child.categoryList.get(childPosition);
		return data.categoryName;
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
			convertView = inflater
					.inflate(R.layout.home_custom_list_cell, null);
			holder = new ViewHolder();

			holder.name = ((TextView) convertView.findViewById(R.id.name));

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.name.setText(emsg);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		CatalogCategoryResponseObject emsg = (CatalogCategoryResponseObject) mCCListData.catalogCategoryList
				.get(groupPosition);
		int count = emsg.categoryList.size();
		return count;

	}

	@Override
	public Object getGroup(int groupPosition) {
		return mCCListData.catalogCategoryList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (mCCListData != null) {
			return mCCListData.catalogCategoryList.size();
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

		CatalogCategoryResponseObject emsg = (CatalogCategoryResponseObject) mCCListData.catalogCategoryList
				.get(groupPosition);

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.child_cell, null);
			holder = new ViewHolder();

			holder.name = ((TextView) convertView.findViewById(R.id.name));
			holder.collapsable = (ImageView) convertView
					.findViewById(R.id.collapsable);
			holder.expandable = (ImageView) convertView
					.findViewById(R.id.expandable);
			holder.rLayout = (RelativeLayout) convertView
					.findViewById(R.id.row_layout);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		if (emsg.isExpandable()) {
			holder.collapsable.setVisibility(View.VISIBLE);
			holder.expandable.setVisibility(View.GONE);
			holder.rLayout
					.setBackgroundResource(R.drawable.cyan_border_pressed);
		} else {
			holder.collapsable.setVisibility(View.GONE);
			holder.expandable.setVisibility(View.VISIBLE);
			if (groupPosition == 0) {
				holder.rLayout
				.setBackgroundResource(R.drawable.green_border_pressed);

			}else if (groupPosition == 1) {
				holder.rLayout
				.setBackgroundResource(R.drawable.grey_border_pressed);

			}else if (groupPosition == 2) {
				holder.rLayout
				.setBackgroundResource(R.drawable.green_border_pressed);

			}else{
				holder.rLayout
				.setBackgroundResource(R.drawable.grey_border_pressed);

			}
			
		}

		holder.name.setText(emsg.catalogName);

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