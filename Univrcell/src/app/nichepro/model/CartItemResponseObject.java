/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CartItemResponseObject extends BaseResponseObject {

	@SerializedName("insurance")
	public String insurance;
	@SerializedName("cartLineIndex")
	public String cartLineIndex;
	@SerializedName("insuranceAmount")
	public String insuranceAmount;
	@SerializedName("itemTotal")
	public String itemTotal;
	@SerializedName("image")
	public String image;
	@SerializedName("unitPrice")
	public String unitPrice;
	@SerializedName("quantity")
	public String quantity;
	@SerializedName("productName")
	public String productName;
	@SerializedName("adjustments")
	public String adjustments;
	@SerializedName("productId")
	public String productId;
	@SerializedName("insuranceAdded")
	public String insuranceAdded;
	@SerializedName("allowPreBooking")
	public String allowPreBooking;
	@SerializedName("isAccessoryItem")
	public String isAccessoryItem;
	 
	public Bitmap prodImg;
	public boolean isDeleted;

	public CartItemResponseObject(Parcel in) {
		super(in);

		this.insurance = in.readString();
		this.cartLineIndex = in.readString();
		this.insuranceAmount = in.readString();
		this.itemTotal = in.readString();
		this.image = in.readString();
		this.unitPrice = in.readString();
		this.quantity = in.readString();
		this.productName = in.readString();
		this.adjustments = in.readString();
		this.productId = in.readString();
		this.insuranceAdded = in.readString();
		this.allowPreBooking = in.readString();
		this.isAccessoryItem = in.readString();
		// TODO Auto-generated constructor stub
	}

	public CartItemResponseObject() {
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

		dest.writeString(insurance);
		dest.writeString(cartLineIndex);
		dest.writeString(insuranceAmount);
		dest.writeString(itemTotal);
		dest.writeString(image);
		dest.writeString(unitPrice);
		dest.writeString(quantity);
		dest.writeString(productName);
		dest.writeString(adjustments);
		dest.writeString(productId);
		dest.writeString(insuranceAdded);
		dest.writeString(allowPreBooking);
		dest.writeString(isAccessoryItem);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<CartItemResponseObject> CREATOR = new Parcelable.Creator<CartItemResponseObject>() {
		public CartItemResponseObject createFromParcel(Parcel in) {
			return new CartItemResponseObject(in);
		}

		public CartItemResponseObject[] newArray(int size) {
			return new CartItemResponseObject[size];
		}
	};
}
