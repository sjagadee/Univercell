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

public class CatalogCategoryListResponseObject extends BaseResponseObject {

	public List<CatalogCategoryResponseObject> catalogCategoryList;

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
		dest.writeTypedList(catalogCategoryList);
	}
	
	/** The Constant CREATOR. */
	public static final Parcelable.Creator<CatalogCategoryListResponseObject> CREATOR = new Parcelable.Creator<CatalogCategoryListResponseObject>() {
		public CatalogCategoryListResponseObject createFromParcel(Parcel in) {
			return new CatalogCategoryListResponseObject(in);
		}

		public CatalogCategoryListResponseObject[] newArray(int size) {
			return new CatalogCategoryListResponseObject[size];
		}
	};
	
	public CatalogCategoryListResponseObject() {
		initalize();
		
	}

	private void initalize() {
		this.catalogCategoryList = new ArrayList<CatalogCategoryResponseObject>();
		
	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private CatalogCategoryListResponseObject(Parcel in) {
		//this();
		super(in);
		initalize();
		in.readTypedList(catalogCategoryList, CatalogCategoryResponseObject.CREATOR);
	}

}
