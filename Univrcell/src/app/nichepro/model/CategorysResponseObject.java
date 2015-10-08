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

public class CategorysResponseObject extends BaseResponseObject {

	public CategorysResponseObject(Parcel in) {
		super(in);
		this.productCategoryId = in.readString();
		this.categoryName = in.readString();

		// TODO Auto-generated constructor stub
	}

	public CategorysResponseObject() {
		// TODO Auto-generated constructor stub

	}

	@SerializedName("productCategoryId")
	public String productCategoryId;
	@SerializedName("categoryName")
	public String categoryName;

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
		dest.writeString(productCategoryId);
		dest.writeString(categoryName);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<CategorysResponseObject> CREATOR = new Parcelable.Creator<CategorysResponseObject>() {
		public CategorysResponseObject createFromParcel(Parcel in) {
			return new CategorysResponseObject(in);
		}

		public CategorysResponseObject[] newArray(int size) {
			return new CategorysResponseObject[size];
		}
	};

}
