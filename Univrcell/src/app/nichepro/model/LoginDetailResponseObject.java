/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class LoginDetailResponseObject extends BaseResponseObject {

	@SerializedName("custLevel")
	public String custLevel;
	@SerializedName("userLoginId")
	public String userLoginId;
	@SerializedName("currentPassword")
	public String currentPassword;
	@SerializedName("passwordHint")
	public String passwordHint;
	@SerializedName("isSystem")
	public String isSystem;
	@SerializedName("enabled")
	public String enabled;
	@SerializedName("hasLoggedOut")
	public String hasLoggedOut;
	@SerializedName("requirePasswordChange")
	public String requirePasswordChange;
	@SerializedName("lastCurrencyUom")
	public String lastCurrencyUom;
	@SerializedName("lastLocale")
	public String lastLocale;
	@SerializedName("lastTimeZone")
	public String lastTimeZone;
	@SerializedName("disabledDateTime")
	public String disabledDateTime;
	@SerializedName("successiveFailedLogins")
	public String successiveFailedLogins;
	@SerializedName("userLdapDn")
	public String userLdapDn;
	@SerializedName("externalAuthId")
	public String externalAuthId;
	@SerializedName("lastUpdatedStamp")
	public String lastUpdatedStamp;
	@SerializedName("lastUpdatedTxStamp")
	public String lastUpdatedTxStamp;
	@SerializedName("partyId")
	public String partyId;

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
