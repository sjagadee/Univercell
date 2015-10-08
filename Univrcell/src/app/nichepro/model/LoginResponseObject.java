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

public class LoginResponseObject extends BaseResponseObject {

	public LoginDetailResponseObject userLogin;
	public PersonResponseObject person;
	public List<PhoneResponseObject> TELECOM_NUMBER;
	public List<AddressResponseObject> POSTAL_ADDRESS;
	public List<EmailResponseObject> EMAIL_ADDRESS;
	@SerializedName("_LOGIN_PASSED_")
	public String _LOGIN_PASSED_;

	public LoginResponseObject(Parcel in) {
		super(in);
		this.TELECOM_NUMBER = new ArrayList<PhoneResponseObject>();
		in.readTypedList(TELECOM_NUMBER, PhoneResponseObject.CREATOR);
		this.POSTAL_ADDRESS = new ArrayList<AddressResponseObject>();
		in.readTypedList(POSTAL_ADDRESS, AddressResponseObject.CREATOR);
	
		// TODO Auto-generated constructor stub
	}

	public LoginResponseObject(){
		
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
		dest.writeTypedList(TELECOM_NUMBER);
		dest.writeTypedList(POSTAL_ADDRESS);
	}
	
	public static final Parcelable.Creator<LoginResponseObject> CREATOR = new Parcelable.Creator<LoginResponseObject>() {
		public LoginResponseObject createFromParcel(Parcel in) {
			return new LoginResponseObject(in);
		}

		public LoginResponseObject[] newArray(int size) {
			return new LoginResponseObject[size];
		}
	};

}
