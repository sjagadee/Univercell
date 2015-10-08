/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.R;

/**
 * MenuDialog - displays the menu in a more traditional way
 */
public class MenuDialog extends Dialog {
	static final int MARGIN_X = 10, MARGIN_Y = 0, dummy_int = 0;

	static final float LINE_TEXT_SIZE = 20.0F, dummy_float = 0;

	ListView mListView;
	TextView mTitleView;
	String mTitle;

	/**
	 * Constructs and shows menu
	 * 
	 * @param context
	 *            - calling context
	 * @param menuSelectListener
	 *            - callback interface
	 * @param menuList
	 *            - list of menu items
	 * @param logoRes
	 *            - image resource id or 0
	 */
	public MenuDialog(final Context context,
			final MenuSelectListener menuSelectListener,
			final MenuItem[] menuList, String title) {
		super(context);

		if (menuSelectListener == null || menuList == null) {
			return; // exception?
		}

		final Resources resources = context.getResources();
		mTitle = title;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(createMenuLayout(context));

		BaseAdapter adapter = new BaseAdapter() {
			@Override
			public int getCount() {
				return menuList.length;
			}

			@Override
			/* * */
			public Object getItem(int position) {
				return position;
			}

			/* * */
			@Override
			public long getItemId(int position) {
				int id = menuList[position].mTextResourceId;
				if (id == 0)
					id = menuList[position].mIconResourceId;
				return id;
			}

			/* * */
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView name;
				ImageView icon;
				if (convertView == null) {
					convertView = LayoutInflater.from(context).inflate(
							R.layout.alert_list_cell, null);
					name = (TextView) convertView.findViewById(R.id.name);
					icon = (ImageView) convertView.findViewById(R.id.icon);
				} else {
					name = (TextView) convertView.findViewById(R.id.name);
					icon = (ImageView) convertView.findViewById(R.id.icon);
				}

				if (position < menuList.length) {
					if (menuList[position].mTextResourceId == 0) {
						name.setText(null);
					} else {
						name.setText(resources
								.getString(menuList[position].mTextResourceId));
					}
					// textView.setTextSize(LINE_TEXT_SIZE);
					// textView.setTextColor(Color.DKGRAY);

					if (menuList[position].mIconResourceId == 0) {

					} else {
						icon.setBackgroundResource(menuList[position].mIconResourceId);

					}
				}
				return convertView;
			}
		};

		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int clicked, long id) {
				menuSelectListener.onMenuSelect(clicked, id);
				MenuDialog.this.dismiss();
			}
		});

		super.show();
	}

	View createMenuLayout(Context context) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.menu_dialog_alert, null);
		mListView = (ListView) view.findViewById(R.id.lvalertlist);
		mTitleView = (TextView) view.findViewById(R.id.heading1);
		mTitleView.setText(mTitle);
		return view;
	}

	/**
	 * Menu item - used in MenuDialog constructor contains mIconResourceId or 0
	 * mTextResourceId or 0 expected that at least one of these will be not 0
	 * the program will crash if non-zero resource id is invalid
	 */
	public static class MenuItem {
		int mIconResourceId;
		int mTextResourceId;

		/**
		 * MenuDialog.MenuItem constructor.
		 * 
		 * @param iconResourceId
		 *            - valid icon resource id or 0
		 * @param textResourceId
		 *            - valid text resource id or 0 at least one of these must
		 *            be non-zero
		 */
		public MenuItem(int iconResourceId, int textResourceId) {
			this.mIconResourceId = iconResourceId;
			this.mTextResourceId = textResourceId;
		}
	}

	/**
	 * Menu selection listener - used in MenuDialog constructor
	 */
	public static interface MenuSelectListener {
		/**
		 * Gets invoked when a user selects a menu item
		 * 
		 * @param index
		 *            - selected index in MenuItem[]
		 * @param id
		 *            - selected text resource id or icon resource id (if
		 *            textResourceId == 0) in MenuItem[]
		 */
		public void onMenuSelect(int index, long id);
	}
}
