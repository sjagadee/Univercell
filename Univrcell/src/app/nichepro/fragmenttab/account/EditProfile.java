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
import android.widget.Spinner;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.AddressResponseObject;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.model.PhoneResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebRequestTask;

public class EditProfile extends BaseDefaultActivity implements
		ResponseParserListener, OnClickListener {

	private String[] stateCode;

	Button update;
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
		setContentView(R.layout.edit_profile);

		update = (Button) findViewById(R.id.bUpdate);

		street = (EditText) findViewById(R.id.etStreet);
		area = (EditText) findViewById(R.id.etArea);

		state = (Spinner) findViewById(R.id.sState);
		country = (Spinner) findViewById(R.id.sCountry);

		city = (EditText) findViewById(R.id.etCity);
		phoneNumber = (EditText) findViewById(R.id.etPhoneNum);
		zipCode = (EditText) findViewById(R.id.etZipCode);
		

		setStateCode();
		
		upload();

		update.setOnClickListener(this);

	}
	
	public void upload(){
		MCommerceApp myApp = (MCommerceApp) getApplication();
		LoginResponseObject ldro = myApp.getLoggedInUser();
		PhoneResponseObject pro = null;
		AddressResponseObject aro = null;
		if (ldro != null) {

			if (ldro.TELECOM_NUMBER != null && ldro.TELECOM_NUMBER.size() > 0) {
				pro = ldro.TELECOM_NUMBER.get(0);
			}

			if (ldro.POSTAL_ADDRESS != null && ldro.POSTAL_ADDRESS.size() > 0) {
				aro = ldro.POSTAL_ADDRESS.get(0);
			}
			
			street.setText(aro.address1);
			area.setText(aro.address2);
			zipCode.setText(aro.postalCode);
			phoneNumber.setText(pro.contactNumber);
		}
	}

	@Override
	public void onClick(View arg0) {
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
						public void onClick(DialogInterface dialog, int which) {

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
			} else if (mPhoneNumber.length() < 10 || mPhoneNumber.length() > 20) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_error, R.string.invalid_phone,
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			} else {
				editProfileRequest();
			}

		}

	}
	
	public void editProfileRequest(){
		
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub

	}

	public void setStateCode() {
		this.stateCode = getResources()
				.getStringArray(R.array.state_array_code);
	}

}
