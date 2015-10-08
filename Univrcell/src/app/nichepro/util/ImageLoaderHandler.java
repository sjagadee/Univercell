/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

package app.nichepro.util;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * The Class ImageLoaderHandler. For notifying image download to the caller
 * class
 */
public class ImageLoaderHandler extends Handler {

	/**
	 * Instantiates a new image loader handler.
	 */
	public ImageLoaderHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Handler#handleMessage(android.os.Message)
	 */
	@Override
	public final void handleMessage(Message msg) {
		if (msg.what == ImageLoader.HANDLER_MESSAGE_ID) {
			handleImageLoadedMessage(msg);
		}
	}

	/**
	 * Handle image loaded message. Get the data from message that was packaged
	 * in image loader after loading the image.
	 * 
	 * @param msg
	 *            the msg
	 */
	protected void handleImageLoadedMessage(Message msg) {
		Bundle data = msg.getData();
		Bitmap bitmap = data.getParcelable(ImageLoader.BITMAP_EXTRA);
		int rowIndex = data.getInt(ImageLoader.ROW_INDEX_EXTRA);
		int colIndex = data.getInt(ImageLoader.COL_INDEX_EXTRA);
		String imageUrl = data.getString(ImageLoader.IMAGE_URL_EXTRA);
		boolean noImageFound = data.getBoolean(ImageLoader.NO_IMAGE_FOUND);
		handleImageLoaded(bitmap, msg, rowIndex,colIndex, imageUrl, noImageFound);
	}

	/**
	 * Handle image loaded.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param msg
	 *            the msg
	 * @param rowIndex
	 *            the row index
	 * @param imageUrl
	 *            the image url
	 * @param noImageFound
	 *            this value will be true if image download was unsuccessful
	 * @return true, if successful
	 */
	protected boolean handleImageLoaded(Bitmap bitmap, Message msg,
			int rowIndex,int colIndex, String imageUrl, boolean noImageFound) {
		return false;
	}
}
