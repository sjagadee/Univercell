/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model.db;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class UserRegistrationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String USERNAME = "username";
	public static String FIRSTNAME = "firstname";
	public static String COUNTRY = "country";
	public static String CITY = "city";
	public static String STATE = "state";
	public static String ZIPCODE = "zipcode";
	public static String PHONENUMBER = "phonenumber";
	public static String EMAIL = "email";
	public static String PASSWORD = "password";
	public static String CPASSWORD = "cpassword";
	public static String AREA = "area";
	public static String STREET = "street";

 	
	
	
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String username;
	@DatabaseField
	String firstname;
	@DatabaseField
	String city;
	@DatabaseField
	String country;
	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	@DatabaseField
	String area;
	@DatabaseField
	String street;
	
	

	public static String getFIRSTNAME() {
		return FIRSTNAME;
	}

	public static void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME = fIRSTNAME;
	}

	public static String getCOUNTRY() {
		return COUNTRY;
	}

	public static void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}

	public static String getCITY() {
		return CITY;
	}

	public static void setCITY(String cITY) {
		CITY = cITY;
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

	public static String getEMAIL() {
		return EMAIL;
	}

	public static void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public static String getCPASSWORD() {
		return CPASSWORD;
	}

	public static void setCPASSWORD(String cPASSWORD) {
		CPASSWORD = cPASSWORD;
	}

	public static String getAREA() {
		return AREA;
	}

	public static void setAREA(String aREA) {
		AREA = aREA;
	}

	public static String getSTREET() {
		return STREET;
	}

	public static void setSTREET(String sTREET) {
		STREET = sTREET;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "UserRegistrationInfo []";
	}

	public UserRegistrationInfo() {
		// needed by ormlite
	}

	public UserRegistrationInfo( String firstname, String email, String password, String cpassword, String area, 
			String street, String country, String state, String city, String zipcode, String phonenumber ) {
		
		
		this.firstname = firstname;
		this.email = email;
		this.password = password;
		this.cpassword = cpassword;
		this.area = area;
		this.country = country;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phonenumber = phonenumber;
		
	}
}
