/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StoreLocatorResponseObject extends BaseResponseObject {

	@SerializedName("city")
	public String city;
	@SerializedName("state")
	public String state;
	@SerializedName("location")
	public String location;
	@SerializedName("address")
	public String address;
	@SerializedName("phoneNumber")
	public String phoneNumber;
	@SerializedName("mobileNumber")
	public String mobileNumber;
	@SerializedName("email")
	public String email;

	public Bitmap prodImg;

	public StoreLocatorResponseObject(Parcel in) {
		super(in);
		this.city = in.readString();
		this.state = in.readString();
		this.location = in.readString();
		this.address = in.readString();
		this.phoneNumber = in.readString();
		this.mobileNumber = in.readString();
		this.email = in.readString();
		// TODO Auto-generated constructor stub
	}

	public StoreLocatorResponseObject() {
		// TODO Auto-generated constructor stub

	}

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

		dest.writeString(city);
		dest.writeString(state);
		dest.writeString(location);
		dest.writeString(address);
		dest.writeString(phoneNumber);
		dest.writeString(mobileNumber);
		dest.writeString(email);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<StoreLocatorResponseObject> CREATOR = new Parcelable.Creator<StoreLocatorResponseObject>() {
		public StoreLocatorResponseObject createFromParcel(Parcel in) {
			return new StoreLocatorResponseObject(in);
		}

		public StoreLocatorResponseObject[] newArray(int size) {
			return new StoreLocatorResponseObject[size];
		}
	};
}
