/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class PhoneContactObject extends BaseResponseObject {

	public String displayName;
	public ArrayList<String> phoneNumberList;
	public ArrayList<String> emailIdList;
	public String Address;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public PhoneContactObject(Parcel in) {
		super(in);

		// this.directions = in.readString();
		// this.postalCode = in.readString();
		// this.countryGeoId = in.readString();
		// this.toName = in.readString();
		// this.attnName = in.readString();
		// this.stateProvinceGeoId = in.readString();
		// this.address1 = in.readString();
		// this.address2 = in.readString();
		// this.city = in.readString();

	}

	public PhoneContactObject() {

	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		// dest.writeString(directions);
		// dest.writeString(postalCode);
		// dest.writeString(countryGeoId);
		// dest.writeString(toName);
		// dest.writeString(attnName);
		// dest.writeString(stateProvinceGeoId);
		// dest.writeString(address1);
		// dest.writeString(address2);
		// dest.writeString(city);

	}

	public static final Parcelable.Creator<PhoneContactObject> CREATOR = new Parcelable.Creator<PhoneContactObject>() {
		public PhoneContactObject createFromParcel(Parcel in) {
			return new PhoneContactObject(in);
		}

		public PhoneContactObject[] newArray(int size) {
			return new PhoneContactObject[size];
		}
	};

}
