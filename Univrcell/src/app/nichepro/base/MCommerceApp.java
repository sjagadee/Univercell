/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.base;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import com.facebook.widget.LoginButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.model.OfferListResponseObject;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.Log;
import app.nichepro.util.WebRequestTask;

/**
 * The Class UnivercellApp. App object for maintaining the global application
 * state.
 */
@ReportsCrashes(formKey = "dFV6U09zWGJBSHlYdHY5SVZTMGNRR3c6MQ")
public class MCommerceApp extends BaseApp {

	/** The TAG. */
	private static String TAG = MCommerceApp.class.getSimpleName();

	private LoginResponseObject loggedInUser;

	private int iSelectedCity;
	public int iSelectedSearch;

	private boolean isContinueShopping;
	private String selectedCity;

	private String deviceUUID;
	private String deviceMobileNo;
	/** The location manager. */
	private LocationManager locationManager = null;
	private LoginButton authButton;
	private OfferListResponseObject mOfferList;

	/** The Dialog. Hold the reference for the progress dialog */
	public WeakReference<ProgressDialog> Dialog = null;

	/**
	 * The weekly dialog. Hold the reference for the progress dialog as there
	 * are two separate request for weekly ad fetching
	 * */
	public WeakReference<ProgressDialog> weeklyDialog = null;

	/** The waiting. */
	public Boolean waiting = false;

	/** The waiting on pause. */
	public Boolean waitingOnPause = false;

	private String storeId;
	private String sessionId;
	
	HashMap<ResponseMesssagType, String> htmlLinkMap = new HashMap<ResponseMesssagType, String>();

	public HashMap<ResponseMesssagType, String> getHtmlLinkMap() {
		return htmlLinkMap;
	}

	public void setHtmlLinkMap(HashMap<ResponseMesssagType, String> htmlLinkMap) {
		this.htmlLinkMap = htmlLinkMap;
	}

	public String getHtmlLinkMap(ResponseMesssagType responsetype) {
		return htmlLinkMap.get(responsetype);
	}

	/**
	 * Gets the location manager.
	 * 
	 * @return the location manager
	 */
	public LocationManager getLocationManager() {
		if (locationManager == null)
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		return locationManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		Log.d(TAG, "In OnCreate");
		ACRA.init(this);

		iSelectedCity = -1;
		selectedCity = null;
		super.onCreate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onTerminate() clean up the timer on app end
	 */
	@Override
	public void onTerminate() {

		super.onTerminate();
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getDeviceMobileNo() {
		return deviceMobileNo;
	}

	public void setDeviceMobileNo(String deviceMobileNo) {
		this.deviceMobileNo = deviceMobileNo;
	}

	HashMap<Integer, WeakReference<WebRequestTask>> currentRunningTasksMap = null;

	public void addWebRequestTask(int dialogId, WebRequestTask wrt) {
		if (currentRunningTasksMap == null)
			currentRunningTasksMap = new HashMap<Integer, WeakReference<WebRequestTask>>();

		currentRunningTasksMap.put(Integer.valueOf(dialogId),
				new WeakReference<WebRequestTask>(wrt));
	}

	public void cleanRunningWebRequestTask(int dialogId) {
		if (currentRunningTasksMap != null && !currentRunningTasksMap.isEmpty()) {
			WeakReference<WebRequestTask> wwrt = currentRunningTasksMap
					.get(Integer.valueOf(dialogId));
			if (wwrt != null) {
				WebRequestTask wrt = wwrt.get();
				if (wrt != null) {
					wrt.cancelTask();
					currentRunningTasksMap.remove(Integer.valueOf(dialogId));
				}
			}
		}
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public LoginResponseObject getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(LoginResponseObject loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(String selectedCity) {
		this.selectedCity = selectedCity;
	}

	public int getiSelectedCity() {
		return iSelectedCity;
	}

	public void setiSelectedCity(int iSelectedCity) {
		this.iSelectedCity = iSelectedCity;
	}

	public boolean isContinueShopping() {
		return isContinueShopping;
	}

	public void setContinueShopping(boolean isContinueShopping) {
		this.isContinueShopping = isContinueShopping;
	}

	public OfferListResponseObject getmOfferList() {
		return mOfferList;
	}

	public void setmOfferList(OfferListResponseObject mOfferList) {
		this.mOfferList = mOfferList;
	}

	public LoginButton getAuthButton() {
		return authButton;
	}

	public void setAuthButton(LoginButton authButton) {
		this.authButton = authButton;
	}

}
