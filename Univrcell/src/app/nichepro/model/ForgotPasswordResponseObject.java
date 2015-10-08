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

public class ForgotPasswordResponseObject extends BaseResponseObject {

	@SerializedName("message")
	public String message;



	public Bitmap prodImg;

	public ForgotPasswordResponseObject(Parcel in) {
		super(in);
		this.message = in.readString();
	
		// TODO Auto-generated constructor stub
	}

	public ForgotPasswordResponseObject() {
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

		dest.writeString(message);

	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ForgotPasswordResponseObject> CREATOR = new Parcelable.Creator<ForgotPasswordResponseObject>() {
		public ForgotPasswordResponseObject createFromParcel(Parcel in) {
			return new ForgotPasswordResponseObject(in);
		}

		public ForgotPasswordResponseObject[] newArray(int size) {
			return new ForgotPasswordResponseObject[size];
		}
	};
}