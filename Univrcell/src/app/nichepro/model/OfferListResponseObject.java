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

public class OfferListResponseObject extends BaseResponseObject {

	public List<OfferProductListResponseObject> offersList;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
		dest.writeTypedList(offersList);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<OfferListResponseObject> CREATOR = new Parcelable.Creator<OfferListResponseObject>() {
		public OfferListResponseObject createFromParcel(Parcel in) {
			return new OfferListResponseObject(in);
		}

		public OfferListResponseObject[] newArray(int size) {
			return new OfferListResponseObject[size];
		}
	};

	public OfferListResponseObject() {

	}

	/**
	 * Instantiates a new category list item response object_ bca.
	 * 
	 * @param in
	 *            the in
	 */
	private OfferListResponseObject(Parcel in) {
		// this();
		super(in);
		offersList = new ArrayList<OfferProductListResponseObject>();
		in.readTypedList(offersList, OfferProductListResponseObject.CREATOR);
	}

}
