/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

public interface AbstractAsyncTaskCallable<ParameterT, ProgressT, ReturnT> {
    public ReturnT call(AbstractAsyncTask<ParameterT, ProgressT, ReturnT> task) throws Exception;
}
