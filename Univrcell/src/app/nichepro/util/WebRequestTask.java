/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.provider.Settings.Secure;
import android.util.Base64;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.responsehandler.BaseParser;
import app.nichepro.responsehandler.ParserFactoryGenerator;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.WebRequestClient.RequestMethod;

public class WebRequestTask extends
		AbstractAsyncTask<Void, String, ArrayList<BaseResponseObject>> {
	/** The Constant TAG_review_count. */
	private final String TAG = WebRequestTask.class.getSimpleName();

	/** The DEVICE. */
	private static String DEVICE = "device";
	private static String DEVICEID = "deviceid";
	/** The DEVICEVERSION. */
	private static String DEVICEVERSION = "deviceversion";

	/** The DEVICEOSVERSION. */
	private static String DEVICEOSVERSION = "deviceosversion";

	/** The APPVERSION. */
	private static String APPVERSION = "appversion";

	/** The APPINSTANCENO. */
	private static String APPINSTANCENO = "appinstanceno";

	/** The m response parser listner. */
	private ResponseParserListener mResponseParserListner;

	/** The m web request client. */
	private WebRequestClient mWebRequestClient;

	/** The params. */
	private ArrayList<NameValuePair> parameters;

	private Bitmap image;
	private String imageName;

	/** The headers. */
	private ArrayList<NameValuePair> headers;

	private String url;

	private RequestMethod rm;

	private MCommerceApp app;

	private Context mContext;
	private boolean isBackgroundTask;

	/**
	 * Adds the param.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void AddParam(String name, String value) {
		parameters.add(new BasicNameValuePair(name, value));
	}

	public void AddParam(String name, Bitmap value) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		value.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] imageBytes = baos.toByteArray();
		String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
		parameters.add(new BasicNameValuePair(name, encodedImage));

	}

	/**
	 * Adds the header.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	/**
	 * Add Common parameters which needs to go along with every request
	 */
	public String getSessionId()
	{
		return  this.app.getSessionId();
	}
	private void AddCommonParams() {
		String android_id = Secure.getString(getCallingContext()
				.getContentResolver(), Secure.ANDROID_ID);
		if (this.app.getStoreId() != null && this.app.getSessionId() != null) {
			AddParam(Constants.PARAM_SESSION_ID, this.app.getSessionId());
			AddParam(Constants.PARAM_PRODUCT_STORE_ID, this.app.getStoreId());
		}
		AddParam(DEVICE, android.os.Build.BRAND);
		AddParam(DEVICEVERSION, android.os.Build.DEVICE);
		AddParam(DEVICEID, android_id);
		// AddParam(DEVICEOSVERSION, Constants.OS_CLASS
		// + Constants.OS_CLASS_VERSION_SEPARATOR
		// + android.os.Build.VERSION.RELEASE);
		AddParam(APPVERSION,
				UIUtilities.getVersionName(this.getCallingContext()));
	}

	/**
	 * Add Common headers which needs to go along with every request
	 */
	private void AddCommonHeaders() {
	}

	/**
	 * Instantiates a new web request task.
	 * 
	 * @param url
	 *            the url
	 * @param context
	 *            the context
	 * @param responseListener
	 *            the response listener
	 * @param params
	 *            the params
	 * @param headers
	 *            the headers
	 */
	// public WebRequestTask(String url, Context context,
	// EnhancedResponseParserListener responseListener, Bundle invokerData) {
	// this(url, context, responseListener, RequestMethod.GET, invokerData);
	// }
	//
	// public WebRequestTask(String url, Fragment fragment,
	// EnhancedResponseParserListener responseListener, Bundle invokerData) {
	// this(url, fragment.getActivity(), responseListener, invokerData);
	// }

	/**
	 * Instantiates a new web request task.
	 * 
	 * @param url
	 *            the url
	 * @param context
	 *            the context
	 * @param responseListener
	 *            the response listener
	 * @param params
	 *            the params
	 * @param headers
	 *            the headers
	 */
	public WebRequestTask(String url, Context context,
			ResponseParserListener responseListener) {
		this(url, context, responseListener, RequestMethod.POST);
	}


	
	public WebRequestTask(String url, Fragment fragment,
			ResponseParserListener responseListener) {
		this(url, fragment.getActivity(), responseListener);
	}

	/**
	 * Instantiates a new web request task.
	 * 
	 * @param url
	 *            the url
	 * @param context
	 *            the context
	 * @param responseListener
	 *            the response listener
	 * @param params
	 *            the params
	 * @param headers
	 *            the headers
	 * @param rm
	 *            the rm
	 */
	public WebRequestTask(String url, Context context,
			ResponseParserListener responseListener, RequestMethod rm) {
		this(url, context, responseListener, rm, false);
	}

	/**
	 * Instantiates a new web request task.
	 * 
	 * @param url
	 *            the url
	 * @param context
	 *            the context
	 * @param responseListener
	 *            the response listener
	 * @param params
	 *            the params
	 * @param headers
	 *            the headers
	 * @param rm
	 *            the rm
	 * @param hideProgressDialog
	 *            the hide progress dialog flag
	 */
	public WebRequestTask(String url, Context context,
			ResponseParserListener responseListener, RequestMethod rm,
			boolean hideProgressDialog) {
		// this(url, context, responseListener, rm, hideProgressDialog);
		super(context);
		this.mContext = context;
		this.url = url;
		this.rm = rm;
		this.mResponseParserListner = responseListener;
		this.app = (MCommerceApp) context.getApplicationContext();
		this.isBackgroundTask = hideProgressDialog;
		if (this.isBackgroundTask)
			disableDialog();
		mWebRequestClient = new WebRequestClient();
		parameters = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	/**
	 * Cancel task.
	 */
	public void cancelTask() {
		if (this != null
				&& (this.getStatus() == Status.RUNNING || this.getStatus() == Status.PENDING)) {
			cancel(true);
			mResponseParserListner = null;
			// mEnhancedResponseParserListner = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onCancelled() called if the cancel button is
	 * pressed
	 */
	@Override
	protected void onCancelled() {
		Log.d(TAG, "In onCancelled()");
		super.onCancelled();
	}

	/**
	 * Override to run code in the UI thread before this Task is run.
	 * 
	 * @param context
	 *            the context
	 */
	protected void before(Context context) {
		Log.d(TAG, "In before()");

		// Adding the task map in HealthCare App, so that it can be cleaned up
		// in case user wants to cancel the progress, or web request
		if (!this.isBackgroundTask) {
			app.addWebRequestTask(getDialogId(), this);
		} else {
			if (!UIUtilities.determineConnectivity(getCallingContext())) {
				WebRequestTask.this.cancelTask();

				UIUtilities.showConfirmationAlert(getCallingContext(),
						R.string.dialog_title_information,
						R.string.network_err_msg, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				return;
			}
		}

		// Check if progress dialog needs to be shown to the user
		if (getDialogId() > -1 && getCallingContext() != null) {
			if (!UIUtilities.determineConnectivity(getCallingContext())) {
				Activity activity = (Activity) context;
				if (activity != null)
					activity.removeDialog(getDialogId());
				WebRequestTask.this.cancelTask();

				UIUtilities.showConfirmationAlert(getCallingContext(),
						R.string.dialog_title_information,
						R.string.network_err_msg, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				return;
			}
		}

		// Initialize the Web Request Client
		AddCommonParams();
		AddCommonHeaders();

	}

	/**
	 * Override to perform computation in a background thread.
	 * 
	 * @param context
	 *            the context
	 * @param params
	 *            the params
	 * @return the void
	 * @throws Exception
	 *             the exception
	 */
	protected ArrayList<BaseResponseObject> doCheckedInBackground(
			Context context, Void... params) throws Exception {
		Log.d(TAG, "In doInBackground()");
		ArrayList<BaseResponseObject> items = null;
		// If the task is not canceled then start the download task
		if (!isCancelled()) {
			// Add app token the the parser
			if (this.app.getSessionId() != null) {
				url = url + ";" + Constants.PARAM_SESSION_ID + "="
						+ this.app.getSessionId();
			}
			String responseStr = mWebRequestClient.Execute(url, rm, parameters,
					headers, imageName, image);

			if (mWebRequestClient.getResponseCode() == 200
					&& mWebRequestClient.getResponseMessageType() != ResponseMesssagType.UnknownResponseMessageType) {

				items = initalizeParser(
						mWebRequestClient.getResponseMessageType(), responseStr);

			} else {

			}

			// Close the Input Stream

		}
		return items;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.nichepro.util.BaseAsyncTask#handleError(android.content.Context,
	 * java.lang.Exception)
	 */
	@Override
	protected void handleError(Context context, Exception error) {
		Log.d("Error", error.toString());
		// Throwing the error that occured during the parsing
		if (null != mResponseParserListner) {
			mResponseParserListner.onError(error);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.nichepro.util.BaseAsyncTask#after(android.content.Context,
	 * java.lang.Object)
	 */
	@Override
	protected void after(Context context, ArrayList<BaseResponseObject> items) {
		Log.d(TAG, "In after");

		// If the request is not yet cancelled
		if (!isCancelled()) {
			// Check if response is known
			if (mWebRequestClient.getResponseMessageType() != ResponseMesssagType.UnknownResponseMessageType) {
				// First Step: Return the message type
				notifyListenerAboutXmlMsgType();

				// Second Step: Start parsing and return the response object
				notifyListenerAboutData(items);
			} else {
				if (getDialogId() > -1) {
					UIUtilities.showConfirmationAlert(context,
							R.string.dialog_title_error, R.string.server_error,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}
			}
		}
	}

	private void notifyListenerAboutData(ArrayList<BaseResponseObject> items) {
		if (null != mResponseParserListner) {
			if (items != null) {
				// if (mWebRequestClient.getResponseMessageType() ==
				// ResponseMesssagType.ErrorResponseMessageType) {
				// mResponseParserListner.onErrorResponse(items);
				// }else
				if (items != null && items.size() > 0) {
					MCommerceApp mApp = (MCommerceApp) this.mContext
							.getApplicationContext();
					BaseResponseObject bro = items.get(0);
					if (bro.jsessionid != null && bro.jsessionid.length() > 0 ) {
						mApp.setSessionId(bro.jsessionid);	
					}
				}
				mResponseParserListner.onEndParsingResponse(items);
			} else {
				mResponseParserListner
						.onEndParsingResponse(new ArrayList<BaseResponseObject>());
			}
		}
	}

	private void notifyListenerAboutXmlMsgType() {
		if (null != mResponseParserListner) {
			mResponseParserListner.onEndParsingMsgType(mWebRequestClient
					.getResponseMessageType());
		}
	}

	private void updateDataForErrors(ArrayList<BaseResponseObject> retList) {
		if (retList.size() > 0) {
		}

	}

	private ArrayList<BaseResponseObject> initalizeParser(
			ResponseMesssagType msgType, String parsedString) throws Exception {
		ArrayList<BaseResponseObject> retList = null;

		BaseParser parser = ParserFactoryGenerator.createParser(msgType);
		retList = parser.getParsedData(parsedString);

		return retList;
	}

	public void setBaseResponseHeaderType(ResponseMesssagType headerType) {
		mWebRequestClient.setResponseMessageType(headerType);
	}

	public boolean isBackgroundTask() {
		return isBackgroundTask;
	}

	public void setBackgroundTask(boolean isBackgroundTask) {
		this.isBackgroundTask = isBackgroundTask;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
