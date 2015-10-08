/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

package app.nichepro.model;

import android.os.Parcel;
import android.os.Parcelable;



public class ErrorResponseObject extends BaseResponseObject {
	
	/** The error text. */
	private String errorText;

	/**
	 * Gets the error text.
	 *
	 * @return the errorText
	 */
	public String getErrorText() {
		return errorText;
	}

	/**
	 * Sets the error text.
	 *
	 * @param errorText the errorText to set
	 */
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	

	public int describeContents() {
		super.describeContents();
		return 0;
	}


	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(errorText);
	}

	/** The Constant CREATOR. */
	public static final Parcelable.Creator<ErrorResponseObject> CREATOR = new Parcelable.Creator<ErrorResponseObject>() {
		public ErrorResponseObject createFromParcel(Parcel in) {
			return new ErrorResponseObject(in);
		}

		public ErrorResponseObject[] newArray(int size) {
			return new ErrorResponseObject[size];
		}
	};

	/**
	 * Instantiates a new error response object.
	 */
	public ErrorResponseObject() {
	}
	
	/**
	 * Instantiates a new error response object.
	 *
	 * @param in the in
	 */
	private ErrorResponseObject(Parcel in) {
		this.errorText = in.readString(); 
	}
}
