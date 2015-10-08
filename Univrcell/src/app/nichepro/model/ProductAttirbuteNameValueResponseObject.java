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

public class ProductAttirbuteNameValueResponseObject extends BaseResponseObject {

	
	@SerializedName("attrName")
	public String attrName;
	@SerializedName("attrValue")
	public String attrValue;

	public ProductAttirbuteNameValueResponseObject(Parcel in) {
		super(in);
		this.attrName = in.readString();
		this.attrValue = in.readString();

		// TODO Auto-generated constructor stub
	}

	public ProductAttirbuteNameValueResponseObject() {
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

		dest.writeString(attrName);
		dest.writeString(attrValue);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ProductAttirbuteNameValueResponseObject> CREATOR = new Parcelable.Creator<ProductAttirbuteNameValueResponseObject>() {
		public ProductAttirbuteNameValueResponseObject createFromParcel(Parcel in) {
			return new ProductAttirbuteNameValueResponseObject(in);
		}

		public ProductAttirbuteNameValueResponseObject[] newArray(int size) {
			return new ProductAttirbuteNameValueResponseObject[size];
		}
	};
}
