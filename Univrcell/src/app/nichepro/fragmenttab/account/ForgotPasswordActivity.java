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
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.Log;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class ForgotPasswordActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener {

	private EditText sendEmail;
	private Button send;
	private WebRequestTask forgotPasswordRequestTask;
	private ResponseMesssagType msgType;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);

		sendEmail = (EditText) findViewById(R.id.etSendToMail);
		send = (Button) findViewById(R.id.send_to_server);
		send.setOnClickListener(this);
		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgLogo:
			setHomeScreenOnTop();

			break;
		case R.id.send_to_server:

			sendToServer();
			sendEmail.setText("");
			break;
		}

	}

	private void sendToServer() {

		username = sendEmail.getText().toString();

		boolean emailCheck;

		if (username != null && !username.isEmpty()) {
			emailCheck = UIUtilities.isEmailValid(username);
			if (!emailCheck) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_information,
						R.string.invalid_email, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			} else {
				forgotPasswordRequestTask = new WebRequestTask(
						WebLinks.getLoginLink(WebLinks.FORGOT_PASSWORD), this,
						this);

				forgotPasswordRequestTask.AddParam(Constants.PARAM_USERNAME,
						username);

				forgotPasswordRequestTask.AddParam(
						Constants.PARAM_EMAIL_PASSWORD, "Y");

				forgotPasswordRequestTask.execute();

			}
		} else {
			UIUtilities.showErrorWithOkButton(this, "Enter the Email Id!");
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
		if (this.msgType == ResponseMesssagType.ForgotPasswordListMessageType) {
			UIUtilities.showInformationWithOkButton(this,
					"Your password has been sent to your Email id!");

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

}