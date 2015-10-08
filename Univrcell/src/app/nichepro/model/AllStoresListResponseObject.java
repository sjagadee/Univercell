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

public class AllStoresListResponseObject extends BaseResponseObject {

	public List<AllStoresResponseObject> productStoreList;

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

		dest.writeTypedList(productStoreList);
		//dest.writeString(jsessionid);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<AllStoresListResponseObject> CREATOR = new Parcelable.Creator<AllStoresListResponseObject>() {
		public AllStoresListResponseObject createFromParcel(Parcel in) {
			return new AllStoresListResponseObject(in);
		}

		public AllStoresListResponseObject[] newArray(int size) {
			return new AllStoresListResponseObject[size];
		}
	};

	public AllStoresListResponseObject() {
		initalize();

	}

	private void initalize() {
		this.productStoreList = new ArrayList<AllStoresResponseObject>();
	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private AllStoresListResponseObject(Parcel in) {
		// this();
		super(in);
		initalize();
		in.readTypedList(productStoreList, AllStoresResponseObject.CREATOR);
		//this.jsessionid = in.readString();
	}

}
