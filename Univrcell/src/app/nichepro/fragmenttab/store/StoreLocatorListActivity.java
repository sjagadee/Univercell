/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.store;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.StoreLoactorListResponseObject;
import app.nichepro.model.StoreLocatorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class StoreLocatorListActivity extends BaseDefaultActivity implements
		ResponseParserListener, OnItemSelectedListener {

	StoreLoactorListResponseObject storeListObject;
	WebRequestTask storeRequestTask;
	private ResponseMesssagType msgType;
	ArrayAdapter<StoreLocatorResponseObject> listAdapter;
	LayoutInflater mInflater;
	ListView list;
	Spinner getCity;
	String[] array_spinner;
	MCommerceApp myApp;
	boolean isAutoSelected;
	String cityName;

	TextView heading1;

	class MyCustomAdapter extends ArrayAdapter<String> {

		Context mContext;

		public MyCustomAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			this.mContext = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			// return super.getView(position, convertView, parent);

			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			TextView label = (TextView) row.findViewById(R.id.weekofday);
			label.setText(array_spinner[position]);

			return row;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isAutoSelected = false;
		myApp = (MCommerceApp) this.getApplication();
		setContentView(R.layout.store_locator_screen);
		heading1 = (TextView) findViewById(R.id.heading1);
		Intent intent = getIntent();
		storeListObject = intent.getParcelableExtra("storelist");
		cityName = intent.getStringExtra(Constants.PARAM_CITY);
		getCity = (Spinner) findViewById(R.id.sGetCity);
		list = (ListView) findViewById(R.id.storelist);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setHomeScreenOnTop();
			}
		});

		// Creating the list adapter and populating the list
		populateView();
		if (cityName != null && cityName.compareTo("All") == 0) {
			cityList();
		} else if (cityName != null) {
			heading1.setText(cityName + " Store List");
			getCity.setVisibility(View.GONE);
		}

		getCity.setOnItemSelectedListener(this);

	}

	@Override
	public void onItemSelected(AdapterView<?> parentView, View arg1,
			int position, long arg3) {
		if (position > 0 && isAutoSelected == false) {
			myApp.setiSelectedCity(position);
			myApp.setSelectedCity(parentView.getSelectedItem().toString());
			requestForStore();
		} else {
			isAutoSelected = false;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void populateView() {
		listAdapter = new StoreLocatorListAdapter(this,
				LayoutInflater.from(this), R.layout.store_list_cell);
		for (StoreLocatorResponseObject element : storeListObject.showRoomsList) {
			listAdapter.add(element);

		}
		list.setAdapter(listAdapter);
	}

	public void cityList() {

		int size = storeListObject.cityList.size();
		array_spinner = new String[size + 1];
		array_spinner[0] = "Select City";
		for (int i = 1; i < size + 1; i++) {
			array_spinner[i] = storeListObject.cityList.get(i - 1);
		}
		getCity.setAdapter(new MyCustomAdapter(this, R.layout.row,
				array_spinner));

		if (myApp.getiSelectedCity() != -1) {
			getCity.setSelection(myApp.getiSelectedCity());
			isAutoSelected = true;
		}

	}

	public void requestForStore() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_SHOW_ROOMS), this, this);

		if (myApp.getiSelectedCity() != -1) {
			storeRequestTask.AddParam(Constants.PARAM_CITY,
					array_spinner[myApp.getiSelectedCity()]);
		} else {
			storeRequestTask.AddParam(Constants.PARAM_CITY, "");
		}

		storeRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.StoreLocatorListMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				storeListObject = (StoreLoactorListResponseObject) items.get(0);
				populateView();

			}
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(this, ero.getErrorText());
			} else
				UIUtilities.showServerError(this);

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(this);
		} else {
			UIUtilities.showServerError(this);
		}
	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(this, ex.getMessage());

	}

	public ResponseMesssagType getMsgType() {
		return msgType;
	}

	public void setMsgType(ResponseMesssagType msgType) {
		this.msgType = msgType;
	}

}
