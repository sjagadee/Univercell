/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model.db;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class AlarmData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String MESSAGE = "message";
	public static Boolean MONDAY = false;
	public static Boolean TUESDAY = false;
	public static Boolean WEDNESDAY = false;
	public static Boolean THURSDAY = false;
	public static Boolean FRIDAY = false;
	public static Boolean SATURDAY = false;
	public static Boolean SUNDAY = false;
	public static Boolean ISREPEATALARM = false;
	public static int MONDAYREQUESTEDCODE = -1;
	public static int TUEDAYREQUESTEDCODE = -1;
	public static int WEDNESDAYREQUESTEDCODE = -1;
	public static int THURSDAYREQUESTEDCODE = -1;
	public static int FRIDAYREQUESTEDCODE = -1;
	public static int SATURDAYREQUESTEDCODE = -1;
	public static int SUNDAYREQUESTEDCODE = -1;

	public static Boolean getISREPEATALARM() {
		return ISREPEATALARM;
	}

	public static void setISREPEATALARM(Boolean iSREPEATALARM) {
		ISREPEATALARM = iSREPEATALARM;
	}

	public static long TIME = -1;
	public static int REQUESTEDCODE = -1;

	public static long getTIME() {
		return TIME;
	}

	public static void setTIME(long tIME) {
		TIME = tIME;
	}

	public static Boolean getMONDAY() {
		return MONDAY;
	}

	public static void setMONDAY(Boolean mONDAY) {
		MONDAY = mONDAY;
	}

	public static Boolean getTUESDAY() {
		return TUESDAY;
	}

	public static void setTUESDAY(Boolean tUESDAY) {
		TUESDAY = tUESDAY;
	}

	public static Boolean getWEDNESDAY() {
		return WEDNESDAY;
	}

	public static void setWEDNESDAY(Boolean wEDNESDAY) {
		WEDNESDAY = wEDNESDAY;
	}

	public static Boolean getTHURSDAY() {
		return THURSDAY;
	}

	public static void setTHURSDAY(Boolean tHURSDAY) {
		THURSDAY = tHURSDAY;
	}

	public static Boolean getFRIDAY() {
		return FRIDAY;
	}

	public static void setFRIDAY(Boolean fRIDAY) {
		FRIDAY = fRIDAY;
	}

	public static Boolean getSATURDAY() {
		return SATURDAY;
	}

	public static void setSATURDAY(Boolean sATURDAY) {
		SATURDAY = sATURDAY;
	}

	public static Boolean getSUNDAY() {
		return SUNDAY;
	}

	public static void setSUNDAY(Boolean sUNDAY) {
		SUNDAY = sUNDAY;
	}

	public Boolean getMonday() {
		return monday;
	}

	public void setMonday(Boolean monday) {
		this.monday = monday;
	}

	public Boolean getTuesday() {
		return tuesday;
	}

	public void setTuesday(Boolean tuesday) {
		this.tuesday = tuesday;
	}

	public Boolean getWednesday() {
		return wednesday;
	}

	public void setWednesday(Boolean wednesday) {
		this.wednesday = wednesday;
	}

	public Boolean getThursday() {
		return thursday;
	}

	public void setThursday(Boolean thursday) {
		this.thursday = thursday;
	}

	public Boolean getFriday() {
		return friday;
	}

	public void setFriday(Boolean friday) {
		this.friday = friday;
	}

	public Boolean getSaturday() {
		return saturday;
	}

	public void setSaturday(Boolean saturday) {
		this.saturday = saturday;
	}

	public Boolean getSunday() {
		return sunday;
	}

	public void setSunday(Boolean sunday) {
		this.sunday = sunday;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getMESSAGE() {
		return MESSAGE;
	}

	public static void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	Boolean monday;
	@DatabaseField
	Boolean tuesday;
	@DatabaseField
	Boolean wednesday;
	@DatabaseField
	Boolean thursday;
	@DatabaseField
	Boolean friday;
	@DatabaseField
	Boolean saturday;
	@DatabaseField
	Boolean sunday;
	@DatabaseField
	Boolean isrepeatalarm;

	public Boolean getIsrepeatalarm() {
		return isrepeatalarm;
	}

	public void setIsrepeatalarm(Boolean isrepeatalarm) {
		this.isrepeatalarm = isrepeatalarm;
	}

	@DatabaseField
	String message;
	@DatabaseField
	long time;
	@DatabaseField
	int mondayrequestedcode;

	public static int getMONDAYREQUESTEDCODE() {
		return MONDAYREQUESTEDCODE;
	}

	public static void setMONDAYREQUESTEDCODE(int mONDAYREQUESTEDCODE) {
		MONDAYREQUESTEDCODE = mONDAYREQUESTEDCODE;
	}

	public static int getTUEDAYREQUESTEDCODE() {
		return TUEDAYREQUESTEDCODE;
	}

	public static void setTUEDAYREQUESTEDCODE(int tUEDAYREQUESTEDCODE) {
		TUEDAYREQUESTEDCODE = tUEDAYREQUESTEDCODE;
	}

	public static int getWEDNESDAYREQUESTEDCODE() {
		return WEDNESDAYREQUESTEDCODE;
	}

	public static void setWEDNESDAYREQUESTEDCODE(int wEDNESDAYREQUESTEDCODE) {
		WEDNESDAYREQUESTEDCODE = wEDNESDAYREQUESTEDCODE;
	}

	public static int getTHURSDAYREQUESTEDCODE() {
		return THURSDAYREQUESTEDCODE;
	}

	public static void setTHURSDAYREQUESTEDCODE(int tHURSDAYREQUESTEDCODE) {
		THURSDAYREQUESTEDCODE = tHURSDAYREQUESTEDCODE;
	}

	public static int getFRIDAYREQUESTEDCODE() {
		return FRIDAYREQUESTEDCODE;
	}

	public static void setFRIDAYREQUESTEDCODE(int fRIDAYREQUESTEDCODE) {
		FRIDAYREQUESTEDCODE = fRIDAYREQUESTEDCODE;
	}

	public static int getSATURDAYREQUESTEDCODE() {
		return SATURDAYREQUESTEDCODE;
	}

	public static void setSATURDAYREQUESTEDCODE(int sATURDAYREQUESTEDCODE) {
		SATURDAYREQUESTEDCODE = sATURDAYREQUESTEDCODE;
	}

	public static int getSUNDAYREQUESTEDCODE() {
		return SUNDAYREQUESTEDCODE;
	}

	public static void setSUNDAYREQUESTEDCODE(int sUNDAYREQUESTEDCODE) {
		SUNDAYREQUESTEDCODE = sUNDAYREQUESTEDCODE;
	}

	public int getMondayrequestedcode() {
		return mondayrequestedcode;
	}

	public void setMondayrequestedcode(int mondayrequestedcode) {
		this.mondayrequestedcode = mondayrequestedcode;
	}

	public int getTuesdayrequestedcode() {
		return tuesdayrequestedcode;
	}

	public void setTuesdayrequestedcode(int tuesdayrequestedcode) {
		this.tuesdayrequestedcode = tuesdayrequestedcode;
	}

	public int getWednesdayrequestedcode() {
		return wednesdayrequestedcode;
	}

	public void setWednesdayrequestedcode(int wednesdayrequestedcode) {
		this.wednesdayrequestedcode = wednesdayrequestedcode;
	}

	public int getThursdayrequestedcode() {
		return thursdayrequestedcode;
	}

	public void setThursdayrequestedcode(int thursdayrequestedcode) {
		this.thursdayrequestedcode = thursdayrequestedcode;
	}

	public int getFridayrequestedcode() {
		return fridayrequestedcode;
	}

	public void setFridayrequestedcode(int fridayrequestedcode) {
		this.fridayrequestedcode = fridayrequestedcode;
	}

	public int getSatrudayrequestedcode() {
		return satrudayrequestedcode;
	}

	public void setSatrudayrequestedcode(int satrudayrequestedcode) {
		this.satrudayrequestedcode = satrudayrequestedcode;
	}

	public boolean isDeleteAlarm() {
		return isDeleteAlarm;
	}

	public void setDeleteAlarm(boolean isDeleteAlarm) {
		this.isDeleteAlarm = isDeleteAlarm;
	}

	@DatabaseField
	int tuesdayrequestedcode;
	@DatabaseField
	int wednesdayrequestedcode;
	@DatabaseField
	int thursdayrequestedcode;
	@DatabaseField
	int fridayrequestedcode;
	@DatabaseField
	int satrudayrequestedcode;
	@DatabaseField
	int sundayrequestedcode;

	public int getSundayrequestedcode() {
		return sundayrequestedcode;
	}

	public void setSundayrequestedcode(int sundayrequestedcode) {
		this.sundayrequestedcode = sundayrequestedcode;
	}

	public static int getREQUESTEDCODE() {
		return REQUESTEDCODE;
	}

	public static void setREQUESTEDCODE(int rEQUESTEDCODE) {
		REQUESTEDCODE = rEQUESTEDCODE;
	}

	public boolean isDeleteAlarm;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public AlarmData() {
		// needed by ormlite
		this.message = "";
		this.monday = false;
		this.tuesday = false;
		this.wednesday = false;
		this.thursday = false;
		this.friday = false;
		this.saturday = false;
		this.sunday = false;
		this.time = -1;
		this.isDeleteAlarm = false;
		this.isrepeatalarm = false;
		this.mondayrequestedcode = -1;
		this.tuesdayrequestedcode = -1;
		this.wednesdayrequestedcode = -1;
		this.satrudayrequestedcode = -1;
		this.thursdayrequestedcode = -1;
		this.fridayrequestedcode = -1;
		this.sundayrequestedcode = -1;
	}

	public AlarmData(String username, String subject, String msg) {

		this.message = msg;
	}
}
