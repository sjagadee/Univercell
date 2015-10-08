/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.responsehandler;

import java.util.ArrayList;

import app.nichepro.model.BaseResponseObject;
import app.nichepro.util.EnumFactory.ResponseMesssagType;

public interface ResponseParserListener {

	/**
	 * On end parsing msg type.
	 * 
	 * @param msgType
	 *            the msg type
	 */
	void onEndParsingMsgType(ResponseMesssagType msgType);

	/**
	 * On end parsing response.
	 * 
	 * @param items
	 *            the items
	 * @param rpb
	 *            the rpb
	 */
	void onEndParsingResponse(ArrayList<BaseResponseObject> items);

	
	void onErrorResponse(ArrayList<BaseResponseObject> items);
	/**
	 * On parse error.
	 * 
	 * @param ex
	 *            the ex
	 */
	void onError(Exception ex);
}
