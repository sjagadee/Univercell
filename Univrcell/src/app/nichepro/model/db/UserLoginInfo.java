/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model.db;

import java.io.Serializable;

import android.content.Context;
import android.text.TextUtils;
import app.nichepro.util.db.DbUtils;

import com.j256.ormlite.field.DatabaseField;

public class UserLoginInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String USERNAME = "username";
	public static String PASSWORD = "password";
	public static String SECURITYTOKEN = "securitytoken";
	public static String TYPE = "type";
	public static String ISKEEPMELOGIN = "iskeepmelogin";

	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String username;
	@DatabaseField
	String password;
	@DatabaseField
	String securitytoken;
	@DatabaseField
	String type;
	@DatabaseField
	boolean keepMeLogin;

	public UserLoginInfo() {
		// needed by ormlite
	}

	public UserLoginInfo(String username, String password,
			String securitytoken, String type, boolean keepMeLogin) {
		this.username = username;
		this.password = password;
		this.securitytoken = securitytoken;
		this.type = type;
		this.keepMeLogin = keepMeLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecuritytoken() {
		return securitytoken;
	}

	public void setSecuritytoken(Context ctx, String userName, String password,
			String securityToken, String accountType, boolean keepMeLoggedIn) {

		this.securitytoken = securityToken;
		this.setUsername(userName);
		this.setPassword(password);
		this.setType(accountType);
		this.setKeepMeLogin(keepMeLoggedIn);
		
		if (keepMeLoggedIn)
			DbUtils.addUserAccountInfo(ctx, userName, password, securityToken,
					accountType, keepMeLoggedIn);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isKeepMeLogin() {
		return keepMeLogin;
	}

	public void setKeepMeLogin(boolean keepMeLogin) {
		this.keepMeLogin = keepMeLogin;
	}

	

	public boolean isUserLoggedIn() {
		return !TextUtils.isEmpty(securitytoken);
	}

}
