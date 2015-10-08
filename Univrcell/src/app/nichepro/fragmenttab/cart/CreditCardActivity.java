/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.cart;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebRequestTask;

public class CreditCardActivity extends BaseDefaultActivity implements
		ResponseParserListener, OnClickListener {

	public enum tabName {
		Email, Address, Summary, Payment,
	}

	ArrayList<String> infoData;
	WebRequestTask infoRequestTask;
	private ResponseMesssagType msgType;
	Button bCntEmail;
	Button bAddress;
	Button bSummary;
	Button bPayment;
	public tabName tName;
	public ViewFlipper vFlipper;
	Button bTabEmail;
	Button bTabAddress;
	Button bTabSummary;
	Button bTabPayment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credit_card);

	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
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

	@Override
	public void onClick(View v) {
	}

}
