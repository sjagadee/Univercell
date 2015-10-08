/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.ServiceCenterListResponseObject;
import app.nichepro.model.StoreLoactorListResponseObject;
import app.nichepro.model.StoreLocatorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class StoreActivity extends BaseDefaultActivity implements
		ResponseParserListener {

	StoreLoactorListResponseObject storeListObject;
	WebRequestTask storeRequestTask;
	private ResponseMesssagType msgType;
	private LayoutInflater mInflater;
	ArrayAdapter<StoreLocatorResponseObject> listAdapter;
	ListView list;
	ArrayList<String> storeData;
	MCommerceApp myApp;
	String cityName;
	int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_locator_first_screen);
		cityName = null;
		myApp = (MCommerceApp) this.getApplication();

		storeData = new ArrayList<String>();

		storeData.add("Chennai");
		storeData.add("Bangalore");
		storeData.add("Hyderabad");
		storeData.add("Cochin");
		storeData.add("Store List");
		// storeData.add("Service Centers");
		// storeData.add("Live Store List");

		list = (ListView) findViewById(R.id.getStoreList);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setHomeScreenOnTop();
			}
		});

		ArrayAdapter<String> listAdapter = new StoreListAdapter(this,
				LayoutInflater.from(this), R.layout.custom_list_cell);
		for (String element : storeData) {
			listAdapter.add(element);
		}
		list.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				switch (position) {
				case 0:
					cityName = "Chennai";
					requestForChennai();

					break;
				case 1:
					cityName = "Bangalore";

					requestForBangalore();

					break;
				case 2:
					cityName = "Hyderabad";

					requestForHyderabad();
					break;
				case 3:
					cityName = "Cochin";

					requestForCochin();
					break;
				case 4:
					// requestForServiceCenters();
					// requestForLiveStores();
					cityName = "All";

					requestForStore();
					break;

				}
			}
		});

		// initiateLocationRequest();

	}

	public void initiateLocationRequest() {

		MCommerceApp myApp = (MCommerceApp) this.getApplication();
		LocationManager lm = myApp.getLocationManager();
		Location location = null;

		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				// makeUseOfNewLocation(location);
				Log.i("Location", "location");
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				Log.i("Location", "location");

			}

			public void onProviderEnabled(String provider) {
				Log.i("Location", "location");

			}

			public void onProviderDisabled(String provider) {
				Log.i("Location", "location");

			}
		};
		boolean isGpsOn = true;
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
					locationListener);
			location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		} else {
			isGpsOn = false;
			gpsNotOn();
		}

		if (location != null) {
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			String strcurrentZip = null;

			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			{
				try {

					List<Address> addresses = geocoder.getFromLocation(
							latitude, longitude, 1);

					if (addresses != null) {
						Address returnedZip = addresses.get(0);
						if (returnedZip != null) {
							strcurrentZip = returnedZip.getPostalCode();

						}

					} else {

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// m_zip.setText("zip not found!");
				}
			}
		} else if (isGpsOn == true) {
			// Location not found

		}

	}

	void gpsNotOn() {
		UIUtilities
				.showConfirmationAlert(
						this,
						R.string.dialog_title_information,
						"Please turn on GPS of your device or choose a different search method from the list!",
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
	}

	public void requestForStore() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_SHOW_ROOMS), this, this);
		if (myApp.getSelectedCity() != null) {
			storeRequestTask.AddParam(Constants.PARAM_CITY,
					myApp.getSelectedCity());
		} else
			storeRequestTask.AddParam(Constants.PARAM_CITY, "");
		storeRequestTask.execute();
	}

	public void requestForChennai() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_SHOW_ROOMS), this, this);

		storeRequestTask.AddParam(Constants.PARAM_CITY, "Chennai");
		storeRequestTask.execute();

	}

	public void requestForBangalore() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_SHOW_ROOMS), this, this);

		storeRequestTask.AddParam(Constants.PARAM_CITY, "BANGALORE");

		storeRequestTask.execute();
	}

	public void requestForHyderabad() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_SHOW_ROOMS), this, this);

		storeRequestTask.AddParam(Constants.PARAM_CITY, "Hyderabad");
		storeRequestTask.execute();

	}

	public void requestForCochin() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_SHOW_ROOMS), this, this);

		storeRequestTask.AddParam(Constants.PARAM_CITY, "Cochin");

		storeRequestTask.execute();
	}

	public void requestForServiceCenters() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_SERVICE_CENTERS), this, this);

		if (myApp.getSelectedCity() != null) {
			storeRequestTask.AddParam(Constants.PARAM_CITY,
					myApp.getSelectedCity());
		} else
			storeRequestTask.AddParam(Constants.PARAM_CITY, "");
		storeRequestTask.execute();
	}

	public void requestForLiveStores() {

		storeRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_LIVE_SHOW_ROOMS), this, this);

		if (myApp.getSelectedCity() != null) {
			storeRequestTask.AddParam(Constants.PARAM_CITY,
					myApp.getSelectedCity());
		} else
			storeRequestTask.AddParam(Constants.PARAM_CITY, "");
		storeRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	public void setCity(StoreLoactorListResponseObject sllro) {
		if (sllro.city != null && sllro.cityList != null
				&& sllro.cityList.size() > 0) {
			myApp.setSelectedCity(sllro.city);
			int index = sllro.cityList.indexOf(sllro.city);
			myApp.setiSelectedCity(index + 1);
		}
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.StoreLocatorListMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				StoreLoactorListResponseObject sllro = (StoreLoactorListResponseObject) items
						.get(0);
				setCity(sllro);
				Intent storeLocatorListActivity = new Intent(
						StoreActivity.this, StoreLocatorListActivity.class);
				storeLocatorListActivity.putExtra("storelist", items.get(0));
				storeLocatorListActivity.putExtra(Constants.PARAM_CITY,
						cityName);

				startActivity(storeLocatorListActivity);
			}
		} else if (this.msgType == ResponseMesssagType.ServiceCentersListMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ServiceCenterListResponseObject sllro = (ServiceCenterListResponseObject) items
						.get(0);

				if (sllro.city != null && sllro.cityList != null
						&& sllro.cityList.size() > 0) {
					myApp.setSelectedCity(sllro.city);
					int index = sllro.cityList.indexOf(sllro.city);
					myApp.setiSelectedCity(index + 1);
				}

				Intent serviceCenterListActivity = new Intent(
						StoreActivity.this, ServiceCenterListActivity.class);
				serviceCenterListActivity.putExtra("serviceList", items.get(0));

				startActivity(serviceCenterListActivity);
			}
		} else if (this.msgType == ResponseMesssagType.LiveStoreLocatorListMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				StoreLoactorListResponseObject sllro = (StoreLoactorListResponseObject) items
						.get(0);
				setCity(sllro);

				Intent liveShowRoomsListActivity = new Intent(
						StoreActivity.this, LiveStoreListActivity.class);
				liveShowRoomsListActivity.putExtra("liveStoreList",
						items.get(0));

				startActivity(liveShowRoomsListActivity);
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
		// TODO Auto-generated method stub

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
