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

public class ServiceCenterListResponseObject extends BaseResponseObject {

	public List<ServiceCenterResponseObject> serviceCenters;
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
		
		dest.writeTypedList(serviceCenters);
		dest.writeStringList(cityList);
		dest.writeString(city);
	}
	
	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ServiceCenterListResponseObject> CREATOR = new Parcelable.Creator<ServiceCenterListResponseObject>() {
		public ServiceCenterListResponseObject createFromParcel(Parcel in) {
			return new ServiceCenterListResponseObject(in);
		}

		public ServiceCenterListResponseObject[] newArray(int size) {
			return new ServiceCenterListResponseObject[size];
		}
	};
	
	public ServiceCenterListResponseObject() {
		initalize();
		
	}

	private void initalize() {
		this.serviceCenters = new ArrayList<ServiceCenterResponseObject>(); 
		this.cityList = new ArrayList<String>();
	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private ServiceCenterListResponseObject(Parcel in) {
		//this();
		super(in);
		initalize();
		in.readTypedList(serviceCenters, ServiceCenterResponseObject.CREATOR);
		in.readStringList(cityList);
		this.city = in.readString();

	}

}
