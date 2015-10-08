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

public class ImageKeyValueResponseObject extends BaseResponseObject {

	public String link;
	public Bitmap prodImage;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ImageKeyValueResponseObject(Parcel in) {
		super(in);
		
		this.link = in.readString();
		this.prodImage = (Bitmap) in.readValue(null);

	}
	
	public ImageKeyValueResponseObject(){
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(link);
		dest.writeValue(prodImage);
	}
	
	public static final Parcelable.Creator<ImageKeyValueResponseObject> CREATOR = new Parcelable.Creator<ImageKeyValueResponseObject>() {
		public ImageKeyValueResponseObject createFromParcel(Parcel in) {
			return new ImageKeyValueResponseObject(in);
		}

		public ImageKeyValueResponseObject[] newArray(int size) {
			return new ImageKeyValueResponseObject[size];
		}
	};

}
