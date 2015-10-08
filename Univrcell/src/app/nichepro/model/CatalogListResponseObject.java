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

public class CatalogListResponseObject extends BaseResponseObject {

	public List<CatalogResponseObject> response;

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
		dest.writeTypedList(response);
	}
	
	/** The Constant CREATOR. */
	public static final Parcelable.Creator<CatalogListResponseObject> CREATOR = new Parcelable.Creator<CatalogListResponseObject>() {
		public CatalogListResponseObject createFromParcel(Parcel in) {
			return new CatalogListResponseObject(in);
		}

		public CatalogListResponseObject[] newArray(int size) {
			return new CatalogListResponseObject[size];
		}
	};
	
	public CatalogListResponseObject() {
		initalize();
		
	}

	private void initalize() {
		this.response = new ArrayList<CatalogResponseObject>();
		
	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private CatalogListResponseObject(Parcel in) {
		//this();
		super(in);
		initalize();
		in.readTypedList(response, CatalogResponseObject.CREATOR);
	}

}
