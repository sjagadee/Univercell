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

public interface SearchHandlerInterface {
	void sendSearchHandler(ResponseMesssagType msgType,
			ArrayList<BaseResponseObject> items);
}
