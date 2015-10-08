/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model.db;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class DoctroAlarmData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int APPOINTMENTID = -1;

	public static int getAPPOINTMENTID() {
		return APPOINTMENTID;
	}

	public static void setAPPOINTMENTID(int aPPOINTMENTID) {
		APPOINTMENTID = aPPOINTMENTID;
	}

	public int getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(int appointmentid) {
		this.appointmentid = appointmentid;
	}

	@DatabaseField(generatedId = true)
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@DatabaseField
	int appointmentid;

	public DoctroAlarmData() {
		// needed by ormlite

		this.appointmentid = -1;
	}

	public DoctroAlarmData(long appid) {

	}
}
