/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StoreLoactorListResponseObject extends BaseResponseObject {

	public List<StoreLocatorResponseObject> showRoomsList;
	public List<StoreLocatorResponseObject> liveShowRoomsList;
	@SerializedName("cityList")
	public List<String> cityList;
	@SerializedName("city")
	public String city;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		super.describeContents();

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
		
		dest.writeTypedList(liveShowRoomsList);
		dest.writeTypedList(showRoomsList);
		dest.writeStringList(cityList);
		dest.writeString(city);
	}
	
	/** The Constant CREATOR. */
	public static final Parcelable.Creator<StoreLoactorListResponseObject> CREATOR = new Parcelable.Creator<StoreLoactorListResponseObject>() {
		public StoreLoactorListResponseObject createFromParcel(Parcel in) {
			return new StoreLoactorListResponseObject(in);
		}

		public StoreLoactorListResponseObject[] newArray(int size) {
			return new StoreLoactorListResponseObject[size];
		}
	};
	
	public StoreLoactorListResponseObject() {
		initalize();
		
	}

	private void initalize() {
		
		this.liveShowRoomsList = new ArrayList<StoreLocatorResponseObject>();
		this.showRoomsList = new ArrayList<StoreLocatorResponseObject>(); 
		this.cityList = new ArrayList<String>();
		
	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private StoreLoactorListResponseObject(Parcel in) {
		//this();
		super(in);
		initalize();
		in.readTypedList(liveShowRoomsList, StoreLocatorResponseObject.CREATOR);
		in.readTypedList(showRoomsList, StoreLocatorResponseObject.CREATOR);
		in.readStringList(cityList);
		this.city = in.readString();
	}

}
