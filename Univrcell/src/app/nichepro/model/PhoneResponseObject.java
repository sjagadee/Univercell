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

public class PhoneResponseObject extends BaseResponseObject {

	@SerializedName("contactNumber")
	public String contactNumber;
	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public PhoneResponseObject(Parcel in) {
		super(in);
		
		this.contactNumber = in.readString();
		

	}
	
	public PhoneResponseObject(){
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(contactNumber);
		

	}
	
	public static final Parcelable.Creator<PhoneResponseObject> CREATOR = new Parcelable.Creator<PhoneResponseObject>() {
		public PhoneResponseObject createFromParcel(Parcel in) {
			return new PhoneResponseObject(in);
		}

		public PhoneResponseObject[] newArray(int size) {
			return new PhoneResponseObject[size];
		}
	};


}
