/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductDetailListResponseObject extends BaseResponseObject {

	public List<ProductAttirbutesResponseObject> productAttributes;
	public List<ProductResponseObject> productDetail;
	@SerializedName("productKeyFeatures")
	public List<String> productKeyFeatures;
	@SerializedName("emi")
	public HashMap<String, HashMap<String, String>> emi;
	@SerializedName("cardType")
	public HashMap<String, String> cardType;
	@SerializedName("emiDetails")
	public HashMap<String, HashMap<String, String>> emiDetails;

	public ProductDetailListResponseObject(Parcel in) {
		super(in);
		this.productAttributes = new ArrayList<ProductAttirbutesResponseObject>();
		in.readTypedList(productAttributes,
				ProductAttirbutesResponseObject.CREATOR);
		this.productDetail = new ArrayList<ProductResponseObject>();
		in.readTypedList(productDetail, ProductResponseObject.CREATOR);
		this.productKeyFeatures = new ArrayList<String>();
		in.readStringList(productKeyFeatures);
		Bundle bundle = in.readBundle();
		cardType = (HashMap<String, String>) bundle.getSerializable("cardType");
		emi = (HashMap<String, HashMap<String, String>>) bundle
				.getSerializable("emi");
		emiDetails = (HashMap<String, HashMap<String, String>>) bundle
				.getSerializable("emiDetails");

		// TODO Auto-generated constructor stub
	}

	public ProductDetailListResponseObject() {
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
		dest.writeTypedList(productAttributes);
		dest.writeTypedList(productDetail);
		dest.writeStringList(productKeyFeatures);
		Bundle bundle = new Bundle();
		bundle.putSerializable("cardType", cardType);
		bundle.putSerializable("emi", emi);
		bundle.putSerializable("emiDetails", emiDetails);
		dest.writeBundle(bundle);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ProductDetailListResponseObject> CREATOR = new Parcelable.Creator<ProductDetailListResponseObject>() {
		public ProductDetailListResponseObject createFromParcel(Parcel in) {
			return new ProductDetailListResponseObject(in);
		}

		public ProductDetailListResponseObject[] newArray(int size) {
			return new ProductDetailListResponseObject[size];
		}
	};
}
