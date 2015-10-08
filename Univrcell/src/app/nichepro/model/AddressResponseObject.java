/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AddressResponseObject extends BaseResponseObject {

	@SerializedName("directions")
	public String directions;
	@SerializedName("postalCode")
	public String postalCode;
	@SerializedName("countryGeoId")
	public String countryGeoId;
	@SerializedName("toName")
	public String toName;
	@SerializedName("attnName")
	public String attnName;
	@SerializedName("stateProvinceGeoId")
	public String stateProvinceGeoId;
	@SerializedName("address1")
	public String address1;
	@SerializedName("address2")
	public String address2;
	@SerializedName("city")
	public String city;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public AddressResponseObject(Parcel in) {
		super(in);
		
		this.directions = in.readString();
		this.postalCode = in.readString();
		this.countryGeoId = in.readString();
		this.toName = in.readString();
		this.attnName = in.readString();
		this.stateProvinceGeoId = in.readString();
		this.address1 = in.readString();
		this.address2 = in.readString();
		this.city = in.readString();

	}
	
	public AddressResponseObject(){
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(directions);
		dest.writeString(postalCode);
		dest.writeString(countryGeoId);
		dest.writeString(toName);
		dest.writeString(attnName);
		dest.writeString(stateProvinceGeoId);
		dest.writeString(address1);
		dest.writeString(address2);
		dest.writeString(city);

	}
	
	public static final Parcelable.Creator<AddressResponseObject> CREATOR = new Parcelable.Creator<AddressResponseObject>() {
		public AddressResponseObject createFromParcel(Parcel in) {
			return new AddressResponseObject(in);
		}

		public AddressResponseObject[] newArray(int size) {
			return new AddressResponseObject[size];
		}
	};

}
