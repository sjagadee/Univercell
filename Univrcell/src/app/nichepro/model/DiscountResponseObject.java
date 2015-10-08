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

public class DiscountResponseObject extends BaseResponseObject {

	@SerializedName("amount")
	public String amount;
	@SerializedName("adjustmentType")
	public String adjustmentType;

	public DiscountResponseObject(Parcel in) {
		super(in);

		this.amount = in.readString();
		this.adjustmentType = in.readString();

		// TODO Auto-generated constructor stub
	}
	
	public Bitmap prodImg;
	public boolean isDeleted;

	public DiscountResponseObject() {
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

		dest.writeString(amount);
		dest.writeString(adjustmentType);

	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<DiscountResponseObject> CREATOR = new Parcelable.Creator<DiscountResponseObject>() {
		public DiscountResponseObject createFromParcel(Parcel in) {
			return new DiscountResponseObject(in);
		}

		public DiscountResponseObject[] newArray(int size) {
			return new DiscountResponseObject[size];
		}
	};
}
