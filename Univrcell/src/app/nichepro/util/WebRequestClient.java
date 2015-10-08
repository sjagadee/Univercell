/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import app.nichepro.util.EnumFactory.ResponseMesssagType;

/**
 * The Class WebRequestClient. Used for making web service request and download
 * the content
 */
public class WebRequestClient {
	/** The TAG. */
	private static String TAG = WebRequestClient.class.getSimpleName();

	/**
	 * The Enum RequestMethod.
	 */
	public enum RequestMethod {
		/** The GET. */
		GET,
		/** The POST. */
		POST,
		/** The POST. */
		POST_WITH_MULTIPART

	}

	/** The http client. */
	private DefaultHttpClient httpClient;

	/** The response code. */
	private int responseCode;

	/** The message. */
	private String message;

	private ResponseMesssagType responseMessageType;

	ArrayList<NameValuePair> header = null;

	/**
	 * Gets the error message.
	 * 
	 * @return the error message
	 */
	public String getErrorMessage() {
		return message;
	}

	/**
	 * Gets the response code.
	 * 
	 * @return the response code
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * Instantiates a new web request client.
	 * 
	 * @param url
	 *            the url
	 */
	public WebRequestClient() {
		responseMessageType = ResponseMesssagType.UnknownResponseMessageType;
		intializeConnectionParams();
	}

	private void intializeConnectionParams() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(),
				443));

		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE,
				new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		ClientConnectionManager cm = new SingleClientConnManager(params,
				schemeRegistry);
		httpClient = new DefaultHttpClient(cm, params);
		// httpClient = new DefaultHttpClient(manager, params);
	}

	/**
	 * Execute.
	 * 
	 * @param method
	 *            the method
	 * @throws Exception
	 *             the exception
	 */
	public String Execute(String url, RequestMethod requestMethod,
			ArrayList<NameValuePair> params, ArrayList<NameValuePair> headers,
			String imageName, Bitmap image) throws Exception {
		String responseStr = null;

		this.header = headers;
		switch (requestMethod) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (params != null && !params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName()
							+ "="
							+ URLEncoder.encode(
									p.getValue().toString() != null ? p
											.getValue().toString()
											: Constants.DEFAULT_emptystring,
									"UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}

			Log.i(TAG, url + combinedParams);
			HttpGet request = new HttpGet(url + combinedParams);

			// add headers
			if (headers != null && !headers.isEmpty()) {
				for (NameValuePair h : headers) {
					Log.i(TAG,
							h.getName()
									+ "="
									+ (h.getValue().toString() != null ? h
											.getValue().toString()
											: Constants.DEFAULT_emptystring));
					request.addHeader(
							h.getName(),
							(h.getValue().toString() != null ? h.getValue()
									.toString() : Constants.DEFAULT_emptystring));
				}
			}

			responseStr = executeRequest(request);
			break;
		}
		case POST: {// //////// for post request
			// POST the envelope
			HttpPost httppost = new HttpPost(url);

			if (header != null && !header.isEmpty()) {
				for (NameValuePair p : header) {
					httppost.addHeader(p.getName(), p.getValue());
				}
			}
			httppost.setEntity(new UrlEncodedFormEntity(params));

			responseStr = executeRequest(httppost);
			break;
			// Log.d("", "Response=====" + resMessage);

		}
		// case POST_WITH_MULTIPART: {// //////// for post request
		// // POST the envelope
		// HttpPost httppost = new HttpPost(url);
		//
		//
		// ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// image.compress(Bitmap.CompressFormat.PNG, 75, bos);
		// byte[] data = bos.toByteArray();
		//
		// ByteArrayBody bab = new ByteArrayBody(data, "photo" + ".png");
		//
		// // File file= new File("/mnt/sdcard/forest.png");
		// // FileBody bin = new FileBody(file);
		// MultipartEntity reqEntity = new MultipartEntity();
		// reqEntity.addPart(Constants.PARAM_DATA_FILE, bab);
		// NameValuePair nvp = params.get(0);
		// reqEntity.addPart(Constants.PARAM_CAPTION,
		// new StringBody(nvp.getValue()));
		//
		// if (header != null && !header.isEmpty()) {
		// for (NameValuePair p : header) {
		// httppost.addHeader(p.getName(), p.getValue());
		// }
		// }
		// //httppost.setHeader( "Content-Type",
		// "multipart/form-data:boundary=AaB03x");
		// httppost.setEntity(new UrlEncodedFormEntity(params));
		//
		// responseStr = executeRequest(httppost);
		//
		// break;
		//
		// }
		}

		return responseStr;
	}

	/**
	 * Execute request.
	 * 
	 * @param request
	 *            the request
	 * @param url
	 *            the url
	 * @throws Exception
	 */
	private String executeRequest(HttpUriRequest request)
			throws ClientProtocolException, IOException, Exception {

		String responsStr = null;

		try {
			ResponseHandler<String> reshandler = new ResponseHandler<String>() {
				// invoked when client receives response
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					// get response entity
					HttpEntity entity = response.getEntity();
					// get response code
					responseCode = response.getStatusLine().getStatusCode();
					// read the response as byte array
					StringBuffer out = new StringBuffer();
					byte[] b = EntityUtils.toByteArray(entity);
					// write the response byte array to a string buffer
					out.append(new String(b, 0, b.length));
					return out.toString();
				}
			};

			responsStr = httpClient.execute(request, reshandler);
			JSONObject json = null;
			if (responseCode == 200) {
				try {
					json = new JSONObject(responsStr);
					if (json != null) {

						
						if (json.has("_ERROR_MESSAGE_")) {
							responsStr = json.getString("_ERROR_MESSAGE_");
							setResponseMessageType(ResponseMesssagType.ErrorResponseMessageType);
						} else if (json.has("MSGTYPE")) {
							setResponseMessageType(ResponseMesssagType
									.toEnum(json.getString("MSGTYPE")));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				throw new Exception();
			}

		} catch (ClientProtocolException e) {
			// Log.d(TAG, e);
			httpClient.getConnectionManager().shutdown();
			throw e;
		} catch (IOException e) {
			// Log.d(TAG, e);
			httpClient.getConnectionManager().shutdown();
			throw e;
		} catch (Exception e) {
			throw e;
		}

		return responsStr;
	}

	public ResponseMesssagType getResponseMessageType() {
		return responseMessageType;
	}

	public void setResponseMessageType(ResponseMesssagType responseMessageType) {
		this.responseMessageType = responseMessageType;
	}
}
