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

public class EmailResponseObject extends BaseResponseObject {

	@SerializedName("email")
	public String email;
	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public EmailResponseObject(Parcel in) {
		super(in);
		
		this.email = in.readString();
		

	}
	
	public EmailResponseObject(){
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(email);
		

	}
	
	public static final Parcelable.Creator<EmailResponseObject> CREATOR = new Parcelable.Creator<EmailResponseObject>() {
		public EmailResponseObject createFromParcel(Parcel in) {
			return new EmailResponseObject(in);
		}

		public EmailResponseObject[] newArray(int size) {
			return new EmailResponseObject[size];
		}
	};


}
