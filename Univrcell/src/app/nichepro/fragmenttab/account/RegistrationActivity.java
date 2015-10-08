/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.account;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebRequestTask;

public class RegistrationActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener {

	EditText firstName;

	EditText email;
	EditText password;
	EditText confirmPassword;
	TextView headerTitle;

	Button register;
	String tFirstName, tEmail, tPass, tConPass;

	String loginType;
	WebRequestTask registrationRequestTask;
	ResponseMesssagType msgType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		firstName = (EditText) findViewById(R.id.etFirstName);
		email = (EditText) findViewById(R.id.etEmail);
		password = (EditText) findViewById(R.id.etRegPassword);
		confirmPassword = (EditText) findViewById(R.id.etRegConfirmPassword);
		register = (Button) findViewById(R.id.bSaveAndContinue);
		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(this);
		register.setOnClickListener(this);

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgLogo:
			setHomeScreenOnTop();

			break;

		case R.id.bSaveAndContinue:
			tFirstName = firstName.getText().toString();

			tEmail = email.getText().toString();
			tPass = password.getText().toString();
			tConPass = confirmPassword.getText().toString();

			boolean emailCheck;
			emailCheck = UIUtilities.isEmailValid(tEmail);

			if (tFirstName.matches("") || tEmail.matches("")
					|| tPass.matches("") || tConPass.matches("")) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_error, R.string.fill_all_details,
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			}

			else {
				if (!emailCheck) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_email, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				} else if (tPass.length() < 6 || tPass.length() > 30) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_password,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				}

				else if (!tPass.equals(tConPass)) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.password_cpassowrd_should_be_same,
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				} else {
					String token[] = tEmail.split("@");
					if (token != null && token.length > 0) {
						String mail = token[0];
						if (mail != null && mail.length() > 30) {
							UIUtilities.showConfirmationAlert(this,
									R.string.dialog_title_error,
									R.string.invalid_email_length,
									R.string.dialog_button_Ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									});
						} else {
							Intent regToRegDetail = new Intent(
									RegistrationActivity.this,
									RegistrationDetailActivity.class);

							regToRegDetail.putExtra("name", tFirstName);
							regToRegDetail.putExtra("email", tEmail);
							regToRegDetail.putExtra("password", tPass);
							regToRegDetail
									.putExtra("confirmPassword", tConPass);

							startActivity(regToRegDetail);
						}
					} else {
						UIUtilities.showConfirmationAlert(this,
								R.string.dialog_title_error,
								R.string.invalid_email,
								R.string.dialog_button_Ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
					}
				}

			}

			break;
		}

	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
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
		UIUtilities.showErrorWithOkButton(this, ex.getMessage());

	}

	public ResponseMesssagType getMsgType() {
		return msgType;
	}

	public void setMsgType(ResponseMesssagType msgType) {
		this.msgType = msgType;
	}

}
