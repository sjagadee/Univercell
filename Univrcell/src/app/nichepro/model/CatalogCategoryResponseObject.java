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

public class CatalogCategoryResponseObject extends BaseResponseObject {
	public CatalogCategoryResponseObject(Parcel in) {
		super(in);
		this.catalogId = in.readString();
		this.catalogName = in.readString();
		categoryList = new ArrayList<CategorysResponseObject>();
		in.readTypedList(categoryList, CategorysResponseObject.CREATOR);

		// TODO Auto-generated constructor stub
	}

	public CatalogCategoryResponseObject() {
		// TODO Auto-generated constructor stub

	}

	@SerializedName("catalogName")
	public String catalogName;

	@SerializedName("catalogId")
	public String catalogId;

	public List<CategorysResponseObject> categoryList;
	private boolean isExpandable;

	public boolean isExpandable() {
		return isExpandable;
	}

	public void setExpandable(boolean isExandate) {
		this.isExpandable = isExandate;
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
		dest.writeString(catalogId);
		dest.writeString(catalogName);
		dest.writeTypedList(categoryList);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<CatalogCategoryResponseObject> CREATOR = new Parcelable.Creator<CatalogCategoryResponseObject>() {
		public CatalogCategoryResponseObject createFromParcel(Parcel in) {
			return new CatalogCategoryResponseObject(in);
		}

		public CatalogCategoryResponseObject[] newArray(int size) {
			return new CatalogCategoryResponseObject[size];
		}
	};

}
