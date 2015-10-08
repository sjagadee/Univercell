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

public class AllStoresResponseObject extends BaseResponseObject {

	@SerializedName("productStoreId")
	public String productStoreId;
	@SerializedName("storeName")
	public String storeName;

	public AllStoresResponseObject(Parcel in) {
		super(in);
		this.productStoreId = in.readString();
		this.storeName = in.readString();
		// TODO Auto-generated constructor stub
	}

	public AllStoresResponseObject() {
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

		dest.writeString(productStoreId);
		dest.writeString(storeName);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<AllStoresResponseObject> CREATOR = new Parcelable.Creator<AllStoresResponseObject>() {
		public AllStoresResponseObject createFromParcel(Parcel in) {
			return new AllStoresResponseObject(in);
		}

		public AllStoresResponseObject[] newArray(int size) {
			return new AllStoresResponseObject[size];
		}
	};
}
