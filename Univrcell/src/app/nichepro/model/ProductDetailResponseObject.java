/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class ProductDetailResponseObject extends BaseResponseObject {

	@SerializedName("isSale")
	public String isSale;
	@SerializedName("promoPrice")
	public String promoPrice;
	@SerializedName("salesDiscontinuationDate")
	public String salesDiscontinuationDate;
	@SerializedName("validPriceFound")
	public String validPriceFound;
	@SerializedName("image")
	public String image;
	@SerializedName("brandName")
	public String brandName;
	@SerializedName("currencyUsed")
	public String currencyUsed;
	@SerializedName("productId")
	public String productId;
	@SerializedName("introductionDate")
	public String introductionDate;
	@SerializedName("price")
	public String price;
	@SerializedName("listPrice")
	public String listPrice;
	@SerializedName("defaultPrice")
	public String defaultPrice;
	@SerializedName("productName")
	public String productName;
	@SerializedName("specialPromoPrice")
	public String specialPromoPrice;
	@SerializedName("basePrice")
	public String basePrice;

	
	public Bitmap prodImg;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

}
