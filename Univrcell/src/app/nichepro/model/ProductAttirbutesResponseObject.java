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

public class ProductAttirbutesResponseObject extends BaseResponseObject {

	@SerializedName("attributeType")
	public String attributeType;
	public List<ProductAttirbuteNameValueResponseObject> attrNameAndValue;

	public ProductAttirbutesResponseObject(Parcel in) {
		super(in);
		this.attributeType = in.readString();
		attrNameAndValue = new ArrayList<ProductAttirbuteNameValueResponseObject>();
		in.readTypedList(attrNameAndValue, ProductAttirbuteNameValueResponseObject.CREATOR);

		// TODO Auto-generated constructor stub
	}

	public ProductAttirbutesResponseObject() {
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

		dest.writeString(attributeType);
		dest.writeTypedList(attrNameAndValue);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ProductAttirbutesResponseObject> CREATOR = new Parcelable.Creator<ProductAttirbutesResponseObject>() {
		public ProductAttirbutesResponseObject createFromParcel(Parcel in) {
			return new ProductAttirbutesResponseObject(in);
		}

		public ProductAttirbutesResponseObject[] newArray(int size) {
			return new ProductAttirbutesResponseObject[size];
		}
	};
}
