/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class KeyValue implements Parcelable {

	private String parentName;
	private ArrayList<String> children;
	private boolean isExandate;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

	public ArrayList<String> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public boolean isExandate() {
		return isExandate;
	}

	public void setExandate(boolean isExandate) {
		this.isExandate = isExandate;
	}

}
