/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model.db;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class DoctorRegistrationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String USERNAME = "username";
	public static String TITLE = "title";
	public static String SPECILIZATION = "specilization";
	public static String CITY = "city";
	public static String STATE = "state";
	public static String ZIPCODE = "zipcode";
	public static String PHONENUMBER = "phonenumber";
	public static String EMAIL = "email";
	public static String PASSWORD = "password";
	public static String CPASSWORD = "cpassword";
	
	

	public static String getTITLE() {
		return TITLE;
	}

	public static void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public static String getSPECILIZATION() {
		return SPECILIZATION;
	}

	public static void setSPECILIZATION(String sPECILIZATION) {
		SPECILIZATION = sPECILIZATION;
	}
	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public static String getSTATE() {
		return STATE;
	}

	public static void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public static String getZIPCODE() {
		return ZIPCODE;
	}

	public static void setZIPCODE(String zIPCODE) {
		ZIPCODE = zIPCODE;
	}

	public static String getPHONENUMBER() {
		return PHONENUMBER;
	}

	public static void setPHONENUMBER(String pHONENUMBER) {
		PHONENUMBER = pHONENUMBER;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getSpecilization() {
		return specilization;
	}

	public void setSpecilization(String specilization) {
		this.specilization = specilization;
	}

	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String username;
	@DatabaseField
	String title;
	@DatabaseField
	String specilization;
	@DatabaseField
	String city;
	@DatabaseField
	String state;
	@DatabaseField
	String zipcode;
	@DatabaseField
	String phonenumber;
	@DatabaseField
	String email;
	@DatabaseField
	String password;
	@DatabaseField
	String cpassword;
	public DoctorRegistrationInfo() {
		// needed by ormlite
	}

	public DoctorRegistrationInfo(String username, String title,String specilization,
			String city, String state, String zipcode, String phonenumber, String email, String password, String cpassword) {
		this.title = title;
		this.specilization = specilization;
		this.username = username;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phonenumber = phonenumber;
		this.email = email;
		this.password = password;
		this.cpassword = cpassword;
	}
}
