/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.account;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewAnimator;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.EmailResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.Log;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.google.android.gms.auth.GoogleAuthUtil;

public class AccountActivity extends BaseDefaultActivity implements
		ResponseParserListener, OnClickListener {
	private AccountManager mAccountManager;
	private static final String SCOPE = "oauth2:"
			+ "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";

	SharedPreferences sharedPrefs;

	WebRequestTask accountRequestTask;
	private ResponseMesssagType msgType;
	MCommerceApp myApp;
	static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
	static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;

	ViewAnimator viewAnimator;
	private Button login, register, forgotPassword, editProfile, logout;
	private EditText userName, password;
	private String mUserName, mPassword;
	private TextView name;
	boolean isFromCheckoutScreen;
	boolean isFromHomeScreen;
	String fbFristName, fbMiddleName, fbLastName, fbUserId, fbEmailId,
			fbGender;

	private String[] mNamesArray;
	private String mGmailId;

	public static String TYPE_KEY = "type_key";

	protected static final String TAG = "AccountActivity";
	boolean isForSyncContact;
	String mFirstName;
	String mLastName;
	String mName;
	String mEmailName;
	Session fbSession;
	MCommerceApp mApp;
	LoginButton authButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acc_screen);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mApp = (MCommerceApp) getApplication();
		authButton = (LoginButton) findViewById(R.id.authButton);
		
		mApp.setAuthButton(authButton);
		mNamesArray = getAccountNames();
		// mAccountTypesSpinner = initializeSpinner(
		// R.id.accounts_tester_account_types_spinner, mNamesArray);
		initializeFetchButton();
		myApp = (MCommerceApp) getApplication();
		Intent intent = getIntent();
		isFromCheckoutScreen = intent.getBooleanExtra("isFromCart", false);
		isFromHomeScreen = intent.getBooleanExtra("isFromHome", false);
		isForSyncContact = intent.getBooleanExtra("isForSyncContact", false);
		userName = (EditText) findViewById(R.id.etUserName);
		password = (EditText) findViewById(R.id.etPassword);
//		userName.setText("a123456@gmail.com");
//		password.setText("123456");
		if (isFromCheckoutScreen == true
				&& intent.getStringExtra("user") != null) {
			userName.setText(intent.getStringExtra("user"));
		}
		viewAnimator = (ViewAnimator) findViewById(R.id.viewFlipper);
		login = (Button) findViewById(R.id.bLogin);
		register = (Button) findViewById(R.id.bRegister);
		forgotPassword = (Button) findViewById(R.id.bForgotPassword);
		logout = (Button) findViewById(R.id.bLogout);
		editProfile = (Button) findViewById(R.id.bEditProfile);
		name = (TextView) findViewById(R.id.tvName);
		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(this);

		register.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
		login.setOnClickListener(this);
		editProfile.setOnClickListener(this);
		logout.setOnClickListener(this);
		LoginResponseObject ldro = myApp.getLoggedInUser();
		if (ldro != null && ldro._LOGIN_PASSED_.compareTo("TRUE") == 0) {
			viewAnimator.showNext();
			getDetailsOfThePerson();
		}
		
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened() && mApp.getLoggedInUser() == null) {
			authButton.logout();
		}
		

		

		authButton.setOnErrorListener(new OnErrorListener() {

			@Override
			public void onError(FacebookException error) {

				Log.i(TAG, "Error " + error.getMessage());
			}
		});
		authButton.clearPermissions();

		authButton.setReadPermissions(Arrays.asList("basic_info", "email"));
		
		// start Facebook Login
		authButton.setSessionStatusCallback(new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(final Session session, SessionState state,
					Exception exception) {

				if (session.isOpened()) {
					launcheDialog();

					Log.i(TAG, "Access Token" + session.getAccessToken());
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {
						
								@Override
								public void onCompleted(GraphUser user,
										Response response) {

									if (user != null) {

										fbFristName = user.getFirstName();
										fbMiddleName = user.getMiddleName();
										fbLastName = user.getLastName();
										if (user.getProperty("gender")
												.toString().compareTo("male") == 0) {
											fbGender = "M";
										} else if(user.getProperty("gender")
												.toString().compareTo("female") == 0) {
											fbGender = "F";
										}
										fbEmailId = user.asMap().get("email")
												.toString();
										fbUserId = user.getId();

										loginFromFacebook();

										if (fbUserId != null) {
											SharedPreferences.Editor editor = sharedPrefs
													.edit();
											editor.putString(
													Constants.FACEBOOK_USER_ID,
													fbUserId);
											editor.commit();
										}
									}
								}
							});
				}

			}

		});
		


	}

	public void launcheDialog(){
		new AsyncCommonDataFetcher(this)
		.execute();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgLogo:
			setHomeScreenOnTop();

			break;
		case R.id.bRegister:
			Intent regActivity = new Intent(AccountActivity.this,
					RegistrationActivity.class);
			startActivity(regActivity);

			break;
		case R.id.bLogin:
			loginRequest();
			break;
		case R.id.bForgotPassword:
			Intent fpActivity = new Intent(AccountActivity.this,
					ForgotPasswordActivity.class);
			startActivity(fpActivity);

			break;
		case R.id.bEditProfile:

			break;
		case R.id.bLogout:
			logoutRequest();
			break;
		}

	}

	public void getDetailsOfThePerson() {

		LoginResponseObject ldro = myApp.getLoggedInUser();
		EmailResponseObject pro = null;

		if (ldro.EMAIL_ADDRESS != null && ldro.EMAIL_ADDRESS.size() > 0) {
			pro = ldro.EMAIL_ADDRESS.get(0);
		}

		name.setText(ldro.person.firstName);

	}

	public void loginRequest() {
		mUserName = userName.getText().toString();
		mPassword = password.getText().toString();

		if (mUserName.matches("") && mPassword.matches("")) {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information,
					R.string.field_should_not_empty, R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else if (mUserName.trim().equals("")) {

			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information, R.string.empty_user,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else if (mPassword.trim().equals("")) {

			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information, R.string.empty_pwd,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});

		} else {

			accountRequestTask = new WebRequestTask(
					WebLinks.getLoginLink(WebLinks.LOGIN), this, this);

			accountRequestTask.AddParam(Constants.PARAM_USERNAME, mUserName);
			accountRequestTask.AddParam(Constants.PARAM_PASSWORD, mPassword);
			accountRequestTask.execute();

		}
	}

	public void loginFromFacebook() {
		accountRequestTask = new WebRequestTask(
				WebLinks.getLoginLink(WebLinks.LOGIN_FACEBOOK), this, this);

		accountRequestTask.AddParam(Constants.PARAM_FB_FIRST_NAME, fbFristName);
		accountRequestTask.AddParam(Constants.PARAM_FB_MIDDLE_NAME,
				fbMiddleName);
		accountRequestTask.AddParam(Constants.PARAM_FB_LAST_NAME, fbLastName);
		accountRequestTask.AddParam(Constants.PARAM_FB_GENDER, fbGender);
		accountRequestTask
				.AddParam(Constants.PARAM_FB_USER_LOGIN_ID, fbEmailId);
		accountRequestTask.AddParam(Constants.PARAM_FB_FBUSERID, fbUserId);
		accountRequestTask.AddParam(Constants.PARAM_FB_USER_TYPE,
				Constants.VALUE_FACEBOOK_USER);

		accountRequestTask.execute();
		launcheDialog();

	}

	public void logoutRequest() {
		accountRequestTask = new WebRequestTask(
				WebLinks.getLoginLink(WebLinks.LOGOUT), this, this);

		accountRequestTask.execute();
		authButton.logout();

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		MCommerceApp myApp = (MCommerceApp) getApplication();
		if (myApp.getLoggedInUser() != null) {
			finish();
		}

	}

	// for facebook
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

		if (this.msgType == ResponseMesssagType.LoginMessageType
				|| this.msgType == ResponseMesssagType.FBLoginMessageType
				|| this.msgType == ResponseMesssagType.GoogleLoginMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				UIUtilities.hideKeyboard(this);
				userName.setText("");
				password.setText("");

				LoginResponseObject ldro = (LoginResponseObject) items.get(0);
				myApp.setLoggedInUser(ldro);
				getDetailsOfThePerson();
				if (isFromHomeScreen) {
					Intent intent = new Intent();
					setResult(Constants.SUCCESSFULLY_LOGIN_REQUESTED_CODE,
							intent);
					setResult(Constants.SUCCESSFULLY_LOGIN_REQUESTED_CODE,
							intent);
					finish();
				} else if (isForSyncContact == true) {
					Intent intent = new Intent();
					setResult(Constants.SUCCESSFULLY_LOGIN_REQUESTED_FOR_SYNC,
							intent);
					finish();
				}else 
					AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				// 
			}
		} else if (this.msgType == ResponseMesssagType.LogoutMessageType) {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information,
					"You are logged out successfully!!",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							AnimationFactory.flipTransition(viewAnimator,
									FlipDirection.RIGHT_LEFT);
							myApp.setLoggedInUser(null);
						}
					});
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(this, ero.getErrorText());
			} else
				UIUtilities.showServerError(this);

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(this);
		} else {
			UIUtilities.showServerError(this);
		}

		Log.i("Response", "as");
	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(this, ex.getMessage());

	}

	public ResponseMesssagType getMsgType() {
		return msgType;
	}

	public void setMsgType(ResponseMesssagType msgType) {
		this.msgType = msgType;
	}

	public void googleLoginSuccess(String response) throws JSONException {
		JSONObject profile = new JSONObject(response);
		mFirstName = profile.getString(Constants.FIRST_NAME_KEY);
		mLastName = profile.getString(Constants.LAST_NAME_KEY);
		mName = profile.getString(Constants.NAME_KEY);
		mGmailId = profile.getString(Constants.EMAIL_KEY);
		loginFromGoogle();
		launcheDialog();

	}

	public void loginFromGoogle() {
		accountRequestTask = new WebRequestTask(
				WebLinks.getLoginLink(WebLinks.LOGIN_GOOGLE), this, this);

		accountRequestTask.AddParam(Constants.PARAM_GOOGLE_FIRST_NAME,
				mFirstName);
		accountRequestTask
				.AddParam(Constants.PARAM_GOOGLE_LAST_NAME, mLastName);
		accountRequestTask.AddParam(Constants.PARAM_GOOGLE_EMAIL, mGmailId);
		accountRequestTask.AddParam(Constants.PARAM_FB_USER_TYPE,
				Constants.VALUE_GOOGLE_USER);
		accountRequestTask.AddParam(Constants.PARAM_GOOGLE_COUNTRY,
				Constants.Empty_String);
		accountRequestTask.AddParam(Constants.PARAM_GOOGLE_CITY,
				Constants.Empty_String);
		accountRequestTask.AddParam(Constants.PARAM_GOOGLE_ZIPCODE,
				Constants.Empty_String);
		accountRequestTask.AddParam(Constants.PARAM_GOOGLE_ADDRESS,
				Constants.Empty_String);
		accountRequestTask.AddParam(Constants.PARAM_GOOGLE_PHONE,
				Constants.Empty_String);

		accountRequestTask.execute();
	}

	public void googleLoginUnSuccess(String response) {
		if (response != null) {
			showEmptyAlert(response);
		}
	}

	/**
	 * This method is a hook for background threads and async tasks that need to
	 * launch a dialog. It does this by launching a runnable under the UI
	 * thread.
	 */
	public void showErrorDialog(final int code) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Dialog d = GooglePlayServicesUtil.getErrorDialog(code,
				// AccountActivity.this,
				// REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
				// d.show();

				showEmptyAlert("Google play service is not enable in your device");
			}
		});
	}

	void showEmptyAlert(String error) {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information, error,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	private void handleAuthorizeResult(int resultCode, Intent data) {
		if (data == null) {
			showEmptyAlert("Unknown error, click the button again");
			return;
		}
		if (resultCode == RESULT_OK) {
			Log.i(TAG, "Retrying");
			getTask(this, mGmailId, SCOPE, REQUEST_CODE_RECOVER_FROM_AUTH_ERROR)
					.execute();
			return;
		}
		if (resultCode == RESULT_CANCELED) {
			showEmptyAlert("User rejected authorization.");
			return;
		}
		showEmptyAlert("Unknown error, click the button again");
	}

	private String[] getAccountNames() {
		mAccountManager = AccountManager.get(this);
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

	private Spinner initializeSpinner(int id, String[] values) {

		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// AccountActivity.this, android.R.layout.simple_spinner_item,
		// values);
		Spinner spinner = (Spinner) findViewById(id);
		// spinner.setAdapter(adapter);
		spinner.setAdapter(new MyCustomAdapter(this, R.layout.row, values));

		return spinner;
	}

	private void initializeFetchButton() {
		Button getToken = (Button) findViewById(R.id.bGmailLogin);
		getToken.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// int accountIndex = mAccountTypesSpinner
				// .getSelectedItemPosition();
				if (mNamesArray == null || mNamesArray.length == 0) {
					// this happens when the sample is run in an emulator which
					// has no google account
					// added yet.
					showEmptyAlert("No account available. Please add an account to the phone first.");
					return;
				}
				mGmailId = mNamesArray[0];
				getTask(AccountActivity.this, mGmailId, SCOPE,
						REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
			}
		});
	}

	/**
	 * Note: This approach is for demo purposes only. Clients would normally not
	 * get tokens in the background from a Foreground activity.
	 */
	private AbstractGetNameTask getTask(AccountActivity activity, String email,
			String scope, int requestCode) {
		return new GetNameInBackground(activity, email, scope, requestCode);
	}

	class MyCustomAdapter extends ArrayAdapter<String> {

		Context mContext;

		public MyCustomAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			this.mContext = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			// return super.getView(position, convertView, parent);

			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			TextView label = (TextView) row.findViewById(R.id.weekofday);
			label.setText(mNamesArray[position]);

			return row;
		}
	}

	public class AsyncCommonDataFetcher extends AsyncTask<Object, Void, Object> {
		Context context;
		boolean shouldContinue = true;
		public ProgressDialog dialog;
		String waitMessage = "Please wait, loading data...";

		public AsyncCommonDataFetcher(Context con) {
			this.context = con;
		}

		public void setMessage(String msg) {
			waitMessage = "Please wait, " + msg;
		}

		protected void onPreExecute() {

			dialog = ProgressDialog.show(context, null, waitMessage, true);

		}

		@Override
		protected void onPostExecute(Object result) {
			if (dialog != null) {
				if (dialog.isShowing())
					dialog.dismiss();
				dialog = null;
			}
		}

		@Override
		protected Object doInBackground(Object... params) {
			return params;
			// do uploading and other tasks
		}
	}

}
