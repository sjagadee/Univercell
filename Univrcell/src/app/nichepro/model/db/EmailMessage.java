/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.model.db;

public class EmailMessage {

	public boolean isSelected;
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getPartyIdTo() {
		return PartyIdTo;
	}
	public void setPartyIdTo(String partyIdTo) {
		PartyIdTo = partyIdTo;
	}
	public String getPartyIdFrom() {
		return PartyIdFrom;
	}
	public void setPartyIdFrom(String partyIdFrom) {
		PartyIdFrom = partyIdFrom;
	}
	public String getCommunicationEventId() {
		return communicationEventId;
	}
	public void setCommunicationEventId(String communicationEventId) {
		this.communicationEventId = communicationEventId;
	}
	public String USERNAME;
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public String getISREAD() {
		return ISREAD;
	}
	public void setISREAD(String iSREAD) {
		ISREAD = iSREAD;
	}
	public int getQTY() {
		return QTY;
	}
	public void setQTY(int qTY) {
		QTY = qTY;
	}
	public String SUBJECT;
	public String MESSAGE;
	public String ISREAD;
	public int QTY;
	public String TIME;
	public String PartyIdTo;
	public String PartyIdFrom;
	public String communicationEventId;

	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}

}
