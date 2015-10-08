/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.nichepro.fragmenttab.account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;

/**
 * Display personalized greeting. This class contains boilerplate code to
 * consume the token but isn't integral to getting the tokens.
 */
public abstract class AbstractGetNameTask extends
		AsyncTask<Hashtable<String, String>, String, String> {
	private static final String TAG = "TokenInfoTask";
	protected AccountActivity mActivity;

	protected String mScope;
	protected String mEmail;
	protected int mRequestCode;
	ProgressDialog dialog;
	String error;

	AbstractGetNameTask(AccountActivity activity, String email, String scope,
			int requestCode) {
		this.mActivity = activity;
		this.mScope = scope;
		this.mEmail = email;
		this.mRequestCode = requestCode;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(mActivity, "", "Loading. Please wait...",
				true);

	}

	@Override
	protected String doInBackground(Hashtable<String, String>... params) {
		String json = null;
		try {
			json = fetchNameFromProfileServer();
		} catch (IOException ex) {
//			onError("Following Error occured, please try again. "
//					+ ex.getMessage(), ex);
			error = "Following Error occured, please try again. "
					+ ex.getMessage();
		} catch (JSONException e) {
			//onError("Bad response: " + e.getMessage(), e);
			error ="Bad response: " + e.getMessage();

		}
		return json;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (null != dialog && dialog.isShowing()) {
			dialog.cancel();
		}
		try {
			if (result != null) {
				mActivity.googleLoginSuccess(result);	
			}else{
				mActivity.googleLoginUnSuccess(error);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void onError(String msg, Exception e) {
		if (e != null) {
			Log.e(TAG, "Exception: ", e);
		}
		mActivity.googleLoginUnSuccess(msg);
	}

	/**
	 * Get a authentication token if one is not available. If the error is not
	 * recoverable then it displays the error message on parent activity.
	 */
	protected abstract String fetchToken() throws IOException;

	/**
	 * Contacts the user info server to get the profile of the user and extracts
	 * the first name of the user from the profile. In order to authenticate
	 * with the user info server the method first fetches an access token from
	 * Google Play services.
	 * 
	 * @throws IOException
	 *             if communication with user info server failed.
	 * @throws JSONException
	 *             if the response from the server could not be parsed.
	 */
	private String fetchNameFromProfileServer() throws IOException,
			JSONException {
		String token = fetchToken();
		String json = null;
		if (token == null) {
			// error has already been handled in fetchToken()
			return json;
		}
		URL url = new URL(
				"https://www.googleapis.com/oauth2/v1/userinfo?access_token="
						+ token);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int sc = con.getResponseCode();
		if (sc == 200) {
			InputStream is = con.getInputStream();
			// mActivity.googleLoginSuccess(readResponse(is));
			json = readResponse(is);
			is.close();
			return json;
		} else if (sc == 401) {
			GoogleAuthUtil.invalidateToken(mActivity, token);
			// onError("Server auth error, please try again.", null);
			error = "Server auth error, please try again.";
			Log.i(TAG,
					"Server auth error: " + readResponse(con.getErrorStream()));
			return json;
		} else {
			// onError("Server returned the following error code: " + sc, null);
			error = "Server auth error, please try again.";
			return json;
		}
	}

	/**
	 * Reads the response from the input stream and returns it as a string.
	 */
	private static String readResponse(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] data = new byte[2048];
		int len = 0;
		while ((len = is.read(data, 0, data.length)) >= 0) {
			bos.write(data, 0, len);
		}
		return new String(bos.toByteArray(), "UTF-8");
	}

}
