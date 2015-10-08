/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

public interface UpdateUiFromAdapterListener {

	/**
	 * On end parsing msg type.
	 * 
	 * @param msgType
	 *            the msg type
	 */
	void updateUI(String message);

	void updateUI(String message, int position);

	void updateUI(int position);


}
