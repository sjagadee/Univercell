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

public class OfferProductListResponseObject extends BaseResponseObject {

	public List<ProductResponseObject> productsList;
	@SerializedName("prodCatalogCateType")
	public String prodCatalogCateType;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
		dest.writeTypedList(productsList);
		dest.writeString(prodCatalogCateType);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<OfferProductListResponseObject> CREATOR = new Parcelable.Creator<OfferProductListResponseObject>() {
		public OfferProductListResponseObject createFromParcel(Parcel in) {
			return new OfferProductListResponseObject(in);
		}

		public OfferProductListResponseObject[] newArray(int size) {
			return new OfferProductListResponseObject[size];
		}
	};

	public OfferProductListResponseObject() {

	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private OfferProductListResponseObject(Parcel in) {
		// this();
		super(in);
		productsList = new ArrayList<ProductResponseObject>();
		in.readTypedList(productsList, ProductResponseObject.CREATOR);
		this.prodCatalogCateType = in.readString();
	}

}
