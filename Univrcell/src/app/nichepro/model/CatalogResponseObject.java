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

public class CatalogResponseObject extends BaseResponseObject {

	public CatalogResponseObject(Parcel in) {
		super(in);
		this.prodCatalogId = in.readString();
		this.catalogName = in.readString();
		
		// TODO Auto-generated constructor stub
	}
	public CatalogResponseObject() {
		// TODO Auto-generated constructor stub
		
	}

	@SerializedName("prodCatalogId")
	public String prodCatalogId;
	@SerializedName("catalogName")
	public String catalogName;

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
		dest.writeString(prodCatalogId);
		dest.writeString(catalogName);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<CatalogResponseObject> CREATOR = new Parcelable.Creator<CatalogResponseObject>() {
		public CatalogResponseObject createFromParcel(Parcel in) {
			return new CatalogResponseObject(in);
		}

		public CatalogResponseObject[] newArray(int size) {
			return new CatalogResponseObject[size];
		}
	};
	

}
