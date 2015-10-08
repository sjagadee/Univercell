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

public class CartItemListResponseObject extends BaseResponseObject {

	public List<CartItemResponseObject> cartItems;
	public List<DiscountResponseObject> Discount;
	@SerializedName("grandTotal")
	public String grandTotal;
	@SerializedName("subTotal")
	public String subTotal;
	@SerializedName("_EVENT_MESSAGE_")
	public String _EVENT_MESSAGE_;
	
	

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
		
		dest.writeTypedList(cartItems);
		dest.writeTypedList(Discount);
		dest.writeString(grandTotal);
		dest.writeString(subTotal);
		dest.writeString(_EVENT_MESSAGE_);
	}
	
	
	

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<CartItemListResponseObject> CREATOR = new Parcelable.Creator<CartItemListResponseObject>() {
		public CartItemListResponseObject createFromParcel(Parcel in) {
			return new CartItemListResponseObject(in);
		}

		public CartItemListResponseObject[] newArray(int size) {
			return new CartItemListResponseObject[size];
		}
	};

	public CartItemListResponseObject() {
		

	}

	
	private CartItemListResponseObject(Parcel in) {
		// this();
		super(in);
		this.cartItems = new ArrayList<CartItemResponseObject>();
		in.readTypedList(cartItems, CartItemResponseObject.CREATOR);
		this.Discount = new ArrayList<DiscountResponseObject>();
		in.readTypedList(Discount, DiscountResponseObject.CREATOR);
		
		this.grandTotal = in.readString();
		this.subTotal = in.readString();
		this._EVENT_MESSAGE_ = in.readString();
	}
}
