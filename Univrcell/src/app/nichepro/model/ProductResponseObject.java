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

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.annotations.SerializedName;

public class ProductResponseObject extends BaseResponseObject {

	@SerializedName("isSale")
	public String isSale;
	@SerializedName("promoPrice")
	public String promoPrice;
	@SerializedName("salesDiscontinuationDate")
	public String salesDiscontinuationDate;
	@SerializedName("productPromoCodeId")
	public String productPromoCodeId;
	@SerializedName("priceAfterDiscount")
	public String priceAfterDiscount;
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
	@SerializedName("productUrl")
	public String productUrl;
	@SerializedName("defaultPrice")
	public String defaultPrice;
	@SerializedName("productName")
	public String productName;
	@SerializedName("specialPromoPrice")
	public String specialPromoPrice;
	@SerializedName("basePrice")
	public String basePrice;

	// New member
	@SerializedName("preBookingLaunchMsg")
	public String preBookingLaunchMsg;
	@SerializedName("preBookingPriceMsg")
	public String preBookingPriceMsg;
	@SerializedName("allowPreBooking")
	public String allowPreBooking;
	@SerializedName("expectedPrice")
	public String expectedPrice;
	@SerializedName("offerFlatAmount")
	public String offerFlatAmount;

	public List<String> additionalImages;
	public List<Bitmap> additionalImagesData;

	public Bitmap prodImg;
	public ImageView prodImgView;
	public ProgressBar prodProgressBar;

	// public int isProductDetail;

	public ProductResponseObject(Parcel in) {
		super(in);
		this.isSale = in.readString();
		this.promoPrice = in.readString();
		this.priceAfterDiscount = in.readString();
		this.salesDiscontinuationDate = in.readString();
		this.productPromoCodeId = in.readString();
		this.validPriceFound = in.readString();
		this.image = in.readString();
		this.brandName = in.readString();
		this.currencyUsed = in.readString();
		this.productId = in.readString();
		this.introductionDate = in.readString();
		this.price = in.readString();
		this.listPrice = in.readString();
		this.productUrl = in.readString();
		this.defaultPrice = in.readString();
		this.productName = in.readString();
		this.specialPromoPrice = in.readString();
		this.basePrice = in.readString();
		this.preBookingLaunchMsg = in.readString();
		this.preBookingPriceMsg = in.readString();
		this.allowPreBooking = in.readString();
		this.expectedPrice = in.readString();
		this.offerFlatAmount = in.readString();
		this.additionalImages = new ArrayList<String>();
		in.readStringList(additionalImages);
		this.additionalImagesData = new ArrayList<Bitmap>();
	//	this.emi = new ArrayList<HashMap<String,HashMap<String,String>>>();
	//	in.readArrayList(emi);


	}

	public ProductResponseObject() {
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

		dest.writeString(isSale);
		dest.writeString(promoPrice);
		dest.writeString(priceAfterDiscount);
		dest.writeString(salesDiscontinuationDate);
		dest.writeString(productPromoCodeId);
		dest.writeString(validPriceFound);
		dest.writeString(image);
		dest.writeString(brandName);
		dest.writeString(currencyUsed);
		dest.writeString(productId);
		dest.writeString(introductionDate);
		dest.writeString(price);
		dest.writeString(listPrice);
		dest.writeString(productUrl);
		dest.writeString(defaultPrice);
		dest.writeString(productName);
		dest.writeString(specialPromoPrice);
		dest.writeString(basePrice);
		dest.writeString(preBookingLaunchMsg);
		dest.writeString(preBookingPriceMsg);
		dest.writeString(allowPreBooking);
		dest.writeString(expectedPrice);
		dest.writeString(offerFlatAmount);
		dest.writeStringList(additionalImages);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ProductResponseObject> CREATOR = new Parcelable.Creator<ProductResponseObject>() {
		public ProductResponseObject createFromParcel(Parcel in) {
			return new ProductResponseObject(in);
		}

		public ProductResponseObject[] newArray(int size) {
			return new ProductResponseObject[size];
		}
	};
}
