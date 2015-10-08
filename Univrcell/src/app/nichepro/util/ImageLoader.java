/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

package app.nichepro.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import app.nichepro.activities.mcommerce.R;

/**
 * The Class ImageLoader.
 */
public class ImageLoader implements Runnable {

    /** The Constant HANDLER_MESSAGE_ID. */
    public static final int HANDLER_MESSAGE_ID = 0;
    
    /** The Constant LOG_TAG. */
    private static final String TAG = ImageLoader.class.getSimpleName();
    
    /** The Constant DEFAULT_POOL_SIZE. */
    private static final int DEFAULT_POOL_SIZE = 3;
    
    /** The Constant DEFAULT_RETRY_HANDLER_SLEEP_TIME. */
    private static final int DEFAULT_RETRY_HANDLER_SLEEP_TIME = 1000;
    
    /** The Constant DEFAULT_NUM_RETRIES. */
    private static final int DEFAULT_NUM_RETRIES = 1;
	
	/** The Constant BITMAP_EXTRA. */
	public static final String BITMAP_EXTRA = "BITMAP";
	
	/** The Constant IMAGE_URL_EXTRA. */
	public static final String IMAGE_URL_EXTRA = "IMAGEURL";
	
	/** The Constant ROW_INDEX_EXTRA. */
	public static final String ROW_INDEX_EXTRA = "ROWINDEX";
	
	public static final String COL_INDEX_EXTRA = "COLINDEX";
	
	/** The Constant ROW_INDEX_EXTRA. */
	public static final String NO_IMAGE_FOUND = "NOIMAGEFOUND";

    /** The executor. */
    private static ThreadPoolExecutor executor;
    private static HashMap<String, String> imageUrlBeingDownloadedMap;
    
    /** The num retries. */
    private static int numRetries = DEFAULT_NUM_RETRIES;
    
    /** The no image found bmp. */
    public static Bitmap noImageFoundBmp = null;
    
    /** The ctx. */
    private static Context ctx;

    /**
     * Sets the thread pool size.
     *
     * @param numThreads the maximum number of threads that will be started to download images in parallel
     */
    public static void setThreadPoolSize(int numThreads) {
        executor.setMaximumPoolSize(numThreads);
    }

    /**
     * Sets the max download attempts.
     *
     * @param numAttempts how often the image loader should retry the image download if network connection
     * fails
     */
    public static void setMaxDownloadAttempts(int numAttempts) {
        ImageLoader.numRetries = numAttempts;
    }

    /**
     * This method must be called before any other method is invoked on this class.
     * 
     * @param context
     *            the current context
     */
    public static synchronized void initialize(Context context) {
        if (executor == null) {
        	imageUrlBeingDownloadedMap = new HashMap<String, String>();
            executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
        }
        ctx = context;
    }

    /** The image url. */
    private String imageUrl;
    
    /** The row index. */
    private int rowIndex;

    /** The row index. */
    private int colIndex;

    /** The handler. */
    private ImageLoaderHandler handler;

    /**
     * Instantiates a new image loader.
     *
     * @param imageUrl the image url
     * @param rowIndex the row index
     * @param handler the handler
     */
    private ImageLoader(String imageUrl, int rowIndex, ImageLoaderHandler handler) {
        this.imageUrl = imageUrl;
        this.rowIndex = rowIndex;
        this.handler = handler;
    }
    
    private ImageLoader(String imageUrl, int rowIndex,int colIndex, ImageLoaderHandler handler) {
        this.imageUrl = imageUrl;
        this.rowIndex = rowIndex;
        this.handler = handler;
        this.colIndex = colIndex;
    }

    /**
     * Start.
     *
     * @param imageUrl the image url
     * @param rowIndex the row index
     * @param handler the handler
     */
    public static void start(String imageUrl, int rowIndex, ImageLoaderHandler handler) {
    	if(!imageUrlBeingDownloadedMap.containsKey(imageUrl)) {
    		imageUrlBeingDownloadedMap.put(imageUrl, imageUrl);
    		executor.execute(new ImageLoader(imageUrl, rowIndex, handler));
    	}
    }
    
    public static void start(String imageUrl, int rowIndex, int colIndex,ImageLoaderHandler handler) {
    	if(!imageUrlBeingDownloadedMap.containsKey(imageUrl)) {
    		imageUrlBeingDownloadedMap.put(imageUrl, imageUrl);
    		executor.execute(new ImageLoader(imageUrl, rowIndex,colIndex, handler));
    	}
    }
    
    public static void cancelAllTask() throws InterruptedException{
    	if (imageUrlBeingDownloadedMap.size() > 0) {
//    		executor.shutdownNow();
//    		executor.awaitTermination(10, TimeUnit.SECONDS);
    		imageUrlBeingDownloadedMap.clear();
		}
    	
    	executor = null;
    }

    /**
     * The job method run on a worker thread.
     */
    public void run() {
        Bitmap bitmap = null; 

        if (bitmap == null) {
            bitmap = downloadImage();
        }

        boolean noImageFound = false;
        if (bitmap == null) {
        	//return placeholder no image found bmp
        	synchronized (ImageLoader.class) {
        		if(noImageFoundBmp==null)
            		noImageFoundBmp = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.noimage);
			}

        	bitmap = noImageFoundBmp;
        	noImageFound = true;
        }
        //notify about the loaded image
        notifyImageLoaded(imageUrl, bitmap, rowIndex,colIndex, noImageFound);
    }

    /**
     * Download image.
     *
     * @return the bitmap
     */
    protected Bitmap downloadImage() {
        int timesTried = 1;

        while (timesTried <= numRetries) {
            try {
                byte[] imageData = retrieveImageData();
                return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            } catch (Throwable e) {
                Log.w(TAG, "download for " + imageUrl + " failed (attempt " + timesTried + ")");
                e.printStackTrace();
                SystemClock.sleep(DEFAULT_RETRY_HANDLER_SLEEP_TIME);
                timesTried++;
            }
        }
        return null;
    }

    /**
     * Retrieve image data.
     *
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected byte[] retrieveImageData() throws IOException {
    	Log.d(TAG, "fetching image " + imageUrl);
        byte[] imageData;
        URL url = new URL(imageUrl);
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// determine the image size and allocate a buffer
			int fileSize = connection.getContentLength();
			imageData = new byte[fileSize];
			BufferedInputStream istream = new BufferedInputStream(connection.getInputStream());
			int bytesRead = 0;
			int offset = 0;
			while (bytesRead != -1 && offset < fileSize) {
			    bytesRead = istream.read(imageData, offset, fileSize - offset);
			    offset += bytesRead;
			}

			// clean up
			istream.close();
			connection.disconnect();
		} catch (Exception e) {
			//This is retry to download when image is not downloaded once
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			ByteArrayBuffer baf = new ByteArrayBuffer(100);
            int current = 0;
            while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
            }
            imageData = baf.toByteArray();
		}
        return imageData;
    }

    /**
     * Notify image loaded.
     *
     * @param url the url
     * @param bitmap the bitmap
     * @param rowIndex the row index
     */
    public void notifyImageLoaded(String url, Bitmap bitmap, int rowIndex, int colIndex,boolean noImageFound) {
        Message message = new Message();
        message.what = HANDLER_MESSAGE_ID;
        Bundle data = new Bundle();
        
        data.putInt(ROW_INDEX_EXTRA, rowIndex);
        data.putInt(COL_INDEX_EXTRA, colIndex);
        data.putString(IMAGE_URL_EXTRA, url);
        data.putParcelable(BITMAP_EXTRA, bitmap);
        data.putBoolean(NO_IMAGE_FOUND, noImageFound);
        message.setData(data);

        handler.sendMessage(message);
        
        //remove url from the map
        imageUrlBeingDownloadedMap.remove(url);
    }
}
