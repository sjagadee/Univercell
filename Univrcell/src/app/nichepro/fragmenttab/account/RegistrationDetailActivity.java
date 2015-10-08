/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.account;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.model.db.UserRegistrationInfo;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class RegistrationDetailActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener {

	private String[] stateCode;

	Button register;
	Spinner state, country;
	EditText phoneNumber, zipCode, street, area, city;
	String mName, mEmail, mPassword, mConfirmPassword;

	WebRequestTask registrationRequestTask;
	ResponseMesssagType msgType;

	String mState, mCountry, mPhoneNumber, mZipCode, mStreet, mArea, mCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_details);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			mName = null;
			mEmail = null;
			mPassword = null;
			mConfirmPassword = null;
		} else {
			mName = extras.getString("name");
			mEmail = extras.getString("email");
			mPassword = extras.getString("password");
			mConfirmPassword = extras.getString("confirmPassword");
		}

		register = (Button) findViewById(R.id.bRegister);

		street = (EditText) findViewById(R.id.etStreet);
		area = (EditText) findViewById(R.id.etArea);

		state = (Spinner) findViewById(R.id.sState);
		country = (Spinner) findViewById(R.id.sCountry);

		city = (EditText) findViewById(R.id.etCity);
		phoneNumber = (EditText) findViewById(R.id.etPhoneNum);
		zipCode = (EditText) findViewById(R.id.etZipCode);

		setStateCode();
		register.setOnClickListener(this);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgLogo:
			setHomeScreenOnTop();

			break;
		case R.id.bRegister:
			mStreet = street.getText().toString();
			mArea = area.getText().toString();
			mState = stateCode[state.getSelectedItemPosition()];
			mCountry = country.getSelectedItem().toString();
			mCity = city.getText().toString();
			mZipCode = zipCode.getText().toString();
			mPhoneNumber = phoneNumber.getText().toString();

			if (mStreet.matches("") || mArea.matches("") || mState.matches("")
					|| mCountry.matches("") || mCity.matches("")
					|| mZipCode.matches("") || mPhoneNumber.matches("")) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_error, R.string.fill_all_details,
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
			} else {
				if (mZipCode.length() > 6 || mZipCode.length() <= 5) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error, R.string.invalid_pin,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				} else if (mPhoneNumber.length() < 10
						|| mPhoneNumber.length() > 10) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_phone, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				} else {
					initiateRegistrationRequest();
				}

			}

			break;
		}
	}

	public void initiateRegistrationRequest() {

		final UserRegistrationInfo user = new UserRegistrationInfo();
		MCommerceApp mApp = (MCommerceApp) getApplication();
		user.setFirstname(this.mName);
		user.setEmail(this.mEmail);
		user.setPassword(this.mPassword);
		user.setCpassword(this.mConfirmPassword);
		user.setCountry(this.mCountry);
		user.setCity(this.mCity);
		user.setState(this.mState);
		user.setZipcode(this.mZipCode);
		user.setPhonenumber(this.mPhoneNumber);
		user.setArea(this.mArea);
		user.setStreet(this.mStreet);
		registrationRequestTask = null;
		registrationRequestTask = new WebRequestTask(
				WebLinks.getLoginLink(WebLinks.CREATE_CUSTOMER), this, this);

		registrationRequestTask.AddParam(Constants.PARAM_USER_FIRST_NAME,
				user.getFirstname());

		registrationRequestTask.AddParam(Constants.PARAM_CUSTOMER_EMAIL,
				user.getEmail());
		registrationRequestTask.AddParam(Constants.PARAM_PASSWORD,
				user.getPassword());
		registrationRequestTask.AddParam(Constants.PARAM_CONFIRM_PASSWORD,
				user.getCpassword());
		registrationRequestTask.AddParam(Constants.PARAM_CUSTOMER_ADDRESS1,
				user.getStreet());
		registrationRequestTask.AddParam(Constants.PARAM_CUSTOMER_ADDRESS2,
				user.getArea());
		registrationRequestTask.AddParam(Constants.PARAM_CUSTOMER_CITY,
				user.getCity());
		registrationRequestTask.AddParam(Constants.PARAM_CUSTOMER_STATE1,
				user.getState());
		registrationRequestTask.AddParam(Constants.PARAM_CUSTOMER_COUNTRY,
				"IND");
		registrationRequestTask.AddParam(Constants.PARAM_CUSTOMER_POSTAL_CODE,
				user.getZipcode());
		registrationRequestTask.AddParam(
				Constants.PARAM_CUSTOMER_MOBILE_CONTACT, user.getPhonenumber());
		if (mApp.getStoreId() != null) {

			registrationRequestTask.AddParam(
					Constants.PARAM_EMAIL_PRODUCT_STORE_ID, mApp.getStoreId());
		} else {
			registrationRequestTask.AddParam(
					Constants.PARAM_EMAIL_PRODUCT_STORE_ID, "10112");
		}

		registrationRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.RegistrationMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				LoginResponseObject lro = (LoginResponseObject) items.get(0);
				MCommerceApp myApp = (MCommerceApp) getApplication();
				myApp.setLoggedInUser(lro);
				if (lro._LOGIN_PASSED_.compareTo("TRUE") == 0) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							"Registered successful", R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							});
				} else {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_information,
							"User Already Exist!", R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});

				}
			} else {

			}
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

	public void setStateCode() {
		this.stateCode = getResources()
				.getStringArray(R.array.state_array_code);
	}
}
