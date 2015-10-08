/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PersonResponseObject extends BaseResponseObject {

	@SerializedName("firstName")
	public String firstName;
	@SerializedName("lastName")
	public String lastName;
	@SerializedName("gender")
	public String gender;
	@SerializedName("birthDate")
	public String birthDate;
	@SerializedName("occupation")
	public String occupation;
	@SerializedName("qualification")
	public String qualification;
	@SerializedName("avgIncome")
	public String avgIncome;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	}

}
