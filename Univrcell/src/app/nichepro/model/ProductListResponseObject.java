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

public class ProductListResponseObject extends BaseResponseObject {

	public List<ProductResponseObject> productsList;
	@SerializedName("viewIndex")
	public String viewIndex;
	@SerializedName("listSize")
	public String listSize;
	@SerializedName("previousViewSize")
	public String previousViewSize;
	@SerializedName("lowIndex")
	public String lowIndex;
	@SerializedName("highIndex")
	public String highIndex;
	@SerializedName("viewSize")
	public String viewSize;
	@SerializedName("paging")
	public String paging;
	@SerializedName("SEARCH_STRING")
	public String SEARCH_STRING;

	public List<ProductResponseObject> exactSearchResults;
	public int isKeyWordSearch;
	public String mTitle;

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
		dest.writeTypedList(productsList);
		dest.writeInt(isKeyWordSearch);
		dest.writeString(mTitle);

		if (isKeyWordSearch == 1) {
			dest.writeString(viewIndex);
			dest.writeString(listSize);
			dest.writeString(previousViewSize);
			dest.writeString(lowIndex);
			dest.writeString(highIndex);
			dest.writeString(viewSize);
			dest.writeString(paging);
			dest.writeTypedList(exactSearchResults);
			dest.writeString(SEARCH_STRING);

		}
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ProductListResponseObject> CREATOR = new Parcelable.Creator<ProductListResponseObject>() {
		public ProductListResponseObject createFromParcel(Parcel in) {
			return new ProductListResponseObject(in);
		}

		public ProductListResponseObject[] newArray(int size) {
			return new ProductListResponseObject[size];
		}
	};

	public ProductListResponseObject() {

	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private ProductListResponseObject(Parcel in) {
		// this();
		super(in);
		productsList = new ArrayList<ProductResponseObject>();
		in.readTypedList(productsList, ProductResponseObject.CREATOR);
		this.isKeyWordSearch = in.readInt();
		this.mTitle = in.readString();

		if (isKeyWordSearch == 1) {

			this.viewIndex = in.readString();
			this.listSize = in.readString();
			this.previousViewSize = in.readString();
			this.lowIndex = in.readString();
			this.highIndex = in.readString();
			this.viewSize = in.readString();
			this.paging = in.readString();
			exactSearchResults = new ArrayList<ProductResponseObject>();
			in.readTypedList(exactSearchResults, ProductResponseObject.CREATOR);
			this.SEARCH_STRING = in.readString();

		}
	}

}
