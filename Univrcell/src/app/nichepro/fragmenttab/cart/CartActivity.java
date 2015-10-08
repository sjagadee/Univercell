/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.cart;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.fragmenttab.account.AccountActivity;
import app.nichepro.model.AddressResponseObject;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.CartItemListResponseObject;
import app.nichepro.model.CartItemResponseObject;
import app.nichepro.model.CheckoutBillingAddressbject;
import app.nichepro.model.EmailResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.model.PhoneResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class CartActivity extends BaseDefaultActivity implements
		ResponseParserListener, OnClickListener {

	public enum tabName {
		Email, Address, Summary, Payment,
	}

	private String[] stateCode;

	CheckBox accept;
	TextView terms;
	CartItemListResponseObject mCartItemList;
	LoginResponseObject lro;
	ArrayList<String> infoData;
	WebRequestTask infoRequestTask;
	private ResponseMesssagType msgType;
	Button bCntEmail;
	Button bAddress;
	Button bSummary;
	public tabName tName;
	public ViewFlipper vFlipper;
	Button bTabEmail;
	Button bTabAddress;
	Button bTabSummary;
	Button bTabPayment;
	RadioButton option1;
	RadioButton option2;
	EditText etEmail;
	EditText etFullName;
	EditText etAddress;
	EditText etLandMark;
	EditText etCity;
	Spinner sStateList;
	EditText etPinCode;
	EditText etPhoneNumber;

	// TextView tvTitle;
	LinearLayout tvTitle;
	TextView tvPrice;
	TextView tvShippingFree;
	TextView tvAmountPayble;
	TextView tvDeliveryTime;
	TextView tvName;
	TextView tvAddress;
	TextView tvLandmark;
	TextView tvCityPincode;
	TextView tvState;
	TextView tvPhone;
	TextView tvSymbol;
	TextView tvSymbolRupee;
	TextView tvQuantity;

	MCommerceApp mApp;
	private WebView mWebView;
	ProgressDialog mProgress;
	public String url;
	String mToName, mAddress1, mLandmark, mCity, mState, mPostelCode, mCountry,
			mMobile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_screen);
		mApp = (MCommerceApp) getApplication();
		tName = tabName.Email;
		Intent intent = getIntent();
		terms = (TextView) findViewById(R.id.tvTerms);
		accept = (CheckBox) findViewById(R.id.cbTermsConditions);
		mCartItemList = intent.getParcelableExtra("cartItemList");
		bCntEmail = (Button) findViewById(R.id.bContinueEmail);
		bAddress = (Button) findViewById(R.id.bContinueAddress);
		bSummary = (Button) findViewById(R.id.bContinueSummary);
		etEmail = (EditText) findViewById(R.id.etEmail);
		bTabEmail = (Button) findViewById(R.id.bTabEmail);
		bTabAddress = (Button) findViewById(R.id.bTabAddress);
		bTabSummary = (Button) findViewById(R.id.bTabSummary);
		bTabPayment = (Button) findViewById(R.id.bTabPayment);
		bCntEmail.setOnClickListener(this);
		bAddress.setOnClickListener(this);

		option1 = (RadioButton) findViewById(R.id.option1);
		option2 = (RadioButton) findViewById(R.id.option2);
		vFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

		etFullName = (EditText) findViewById(R.id.etFullName);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etLandMark = (EditText) findViewById(R.id.etLandMark);
		etCity = (EditText) findViewById(R.id.etCity);
		sStateList = (Spinner) findViewById(R.id.sState);
		etPinCode = (EditText) findViewById(R.id.etPincode);
		etPhoneNumber = (EditText) findViewById(R.id.etPhonenumber);

		tvTitle = (LinearLayout) findViewById(R.id.title);
		tvPrice = (TextView) findViewById(R.id.price);
		tvSymbol = (TextView) findViewById(R.id.symbolRup);
		tvSymbolRupee = (TextView) findViewById(R.id.symbolRupee);
		tvShippingFree = (TextView) findViewById(R.id.shipping_charges);
		tvAmountPayble = (TextView) findViewById(R.id.amount_payable);
		tvDeliveryTime = (TextView) findViewById(R.id.dtime);
		tvName = (TextView) findViewById(R.id.tName);
		tvAddress = (TextView) findViewById(R.id.tAddress);
		tvLandmark = (TextView) findViewById(R.id.tLandmark);
		tvCityPincode = (TextView) findViewById(R.id.tCity_Zip);
		tvState = (TextView) findViewById(R.id.tState);
		tvPhone = (TextView) findViewById(R.id.tPhone);
		tvQuantity = (TextView) findViewById(R.id.qty);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(this);
		terms.setOnClickListener(this);
		bSummary.setOnClickListener(this);

		accept.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;

			}
		});

		setStateCode();

		if (savedInstanceState == null) {
			MCommerceApp myApp = (MCommerceApp) getApplication();
			if (myApp.getLoggedInUser() != null
					&& myApp.getLoggedInUser().EMAIL_ADDRESS != null
					&& myApp.getLoggedInUser().EMAIL_ADDRESS.size() > 0) {
				EmailResponseObject ero = myApp.getLoggedInUser().EMAIL_ADDRESS
						.get(0);
				etEmail.setText(ero.email);
				option2.setChecked(true);
				onTabEmailSelection();
				onTabChanged();
			} else if (myApp.getLoggedInUser() != null
					&& myApp.getLoggedInUser().userLogin.userLoginId != null
					&& UIUtilities
							.isEmailValid(myApp.getLoggedInUser().userLogin.userLoginId)) {
				etEmail.setText(myApp.getLoggedInUser().userLogin.userLoginId);
				option2.setChecked(true);
				onTabEmailSelection();
				onTabChanged();
			}
		}
	}

	public void showLogistic() {

		String line0 = "Dear Customer, while receiving the Shipments from the logistics partners, please ensure to check the below terms";
		String line1 = "a. Please Check";
		String newline = "\n";
		String line2 = "\t i. The condition of the FLYER. ";
		String line3 = "\t ii. The condition of the HOLOGRAM Sticker with UID (Unique Identity Number ) on it.";
		String line4 = "b. If the packing or HOLOGRAM Sticker is not in intact condition CUSTOMER has to refuse the DELIVERY.";
		String line5 = "UniverCell or the logistics partners will not be responsible for any claim after DELIVERY in intact CONDITION";
		String msg = line0 + newline + line1 + newline + line2 + newline
				+ line3 + newline + line4+ newline + line5;

		UIUtilities.showConfirmationAlert(this,
				R.string.terms_and_condition_in_cart, msg,
				R.string.dialog_button_Ok, R.string.dialog_button_Cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//onTabAddressSelection();
						populateSummary();
						vFlipper.showNext();
						tName = tabName.Summary;

						onTabChanged();

					}
				}

				, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("tabname", tName.toString());

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		bTabEmail.setBackgroundResource(R.drawable.cart_white_border);
		bTabAddress.setBackgroundResource(R.drawable.cart_white_border);
		bTabSummary.setBackgroundResource(R.drawable.cart_white_border);
		bTabPayment.setBackgroundResource(R.drawable.cart_white_border);
		String tabname = (String) savedInstanceState.get("tabname");

		if (tabname != null && tabname.compareTo(tabName.Email.toString()) == 0) {
			tName = tabName.Email;
			bTabEmail.setBackgroundResource(R.drawable.cart_blue_border);

		} else if (tabname != null
				&& tabname.compareTo(tabName.Address.toString()) == 0) {
			tName = tabName.Address;
			vFlipper.showNext();
			bTabAddress.setBackgroundResource(R.drawable.cart_blue_border);

		} else if (tabname != null
				&& tabname.compareTo(tabName.Summary.toString()) == 0) {
			tName = tabName.Summary;
			vFlipper.showNext();
			vFlipper.showNext();
			bTabSummary.setBackgroundResource(R.drawable.cart_blue_border);
			populateSummary();

		} else if (tabname != null
				&& tabname.compareTo(tabName.Payment.toString()) == 0) {
			tName = tabName.Payment;
			vFlipper.showNext();
			vFlipper.showNext();
			vFlipper.showNext();

			bTabPayment.setBackgroundResource(R.drawable.cart_blue_border);
		}
	}

	void populateSummary() {

		int qty = 0;
		if (tvTitle != null && ((LinearLayout) tvTitle).getChildCount() > 0) {
			((LinearLayout) tvTitle).removeAllViews();
		}
		for (int i = 0; i < mCartItemList.cartItems.size(); i++) {
			CartItemResponseObject ciro = mCartItemList.cartItems.get(i);
			TextView txtView = new TextView(this);
			String str;
			str = "\u2022" + " " + ciro.productName;
			txtView.setText(str);

			tvTitle.addView(txtView);
			qty += Integer.parseInt(ciro.quantity);
		}

		tvSymbol.setTypeface(UIUtilities.createRupeeSymbol(this));
		tvSymbol.setText("`");

		tvSymbolRupee.setTypeface(UIUtilities.createRupeeSymbol(this));
		tvSymbolRupee.setText("`");

		tvPrice.setText(mCartItemList.subTotal);
		tvQuantity.setText(qty + "");
		tvAmountPayble.setText(mCartItemList.grandTotal);
		tvName.setText(etFullName.getText().toString());
		tvAddress.setText(etAddress.getText().toString());
		tvLandmark.setText(etLandMark.getText().toString());
		tvCityPincode.setText(etCity.getText().toString() + "-"
				+ etPinCode.getText().toString());
		tvState.setText(sStateList.getSelectedItem().toString());
		tvPhone.setText(etPhoneNumber.getText().toString());
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		if (this.msgType == ResponseMesssagType.CheckoutBillingAddressType) {
			CheckoutBillingAddressbject cbao = (CheckoutBillingAddressbject) items
					.get(0);
			toShipCustomer(cbao.partyId, cbao.shipping_contact_mech_id);
		} else if (this.msgType == ResponseMesssagType.CheckoutShippingAddressType) {
			url = "http://" + WebLinks.SERVER + "androidPaymentOptions;"
					+ Constants.PARAM_SESSION_ID + "="
					+ infoRequestTask.getSessionId() + "?"
					+ Constants.PARAM_SESSION_ID + "="
					+ infoRequestTask.getSessionId();
			onTabPlacedOrder();
			// Intent browserIntent = new Intent(Intent.ACTION_VIEW,
			// Uri.parse(url));
			// startActivity(browserIntent);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		UIUtilities.hideKeyboard(this);
		switch (v.getId()) {
		case R.id.tvTerms:
			// terms and conditions
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_warning, R.string.open_browser,
					R.string.continue2, R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Intent browserIntent = new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("http://www.univercell.in/control/insuranceterms"));
							startActivity(browserIntent);
						}
					}, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});
			break;
		case R.id.imgLogo:
			setHomeScreenOnTop();

			break;
		case R.id.bContinueEmail:
			onTabEmailSelection();
			break;
		case R.id.bContinueAddress:
			//showLogistic();
			onTabAddressSelection();
			break;
		case R.id.bContinueSummary:

			mToName = etFullName.getText().toString();
			mAddress1 = etAddress.getText().toString();
			mLandmark = etLandMark.getText().toString();
			mPostelCode = etPinCode.getText().toString();
			mCountry = "IND";
			mState = stateCode[sStateList.getSelectedItemPosition()];
			mCity = etCity.getText().toString();
			mMobile = etPhoneNumber.getText().toString();

			if (accept.isChecked()) {

				toBillCustomor();
			} else {
				UIUtilities.showErrorWithOkButton(CartActivity.this,
						"Please Check the Terms and Conditions.");
			}

			break;
		case R.id.bPlaceOrder:

			onTabPlacedOrder();

			break;
		default:
			break;
		}
		onTabChanged();
	}

	private void toBillCustomor() {

		infoRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.BILL_ADDRESS), this, this);

		infoRequestTask.AddParam(Constants.PARAM_TONAME, mToName);
		infoRequestTask.AddParam(Constants.PARAM_ADDRESS1, mAddress1);
		infoRequestTask.AddParam(Constants.PARAM_DIRECTIONS, mLandmark);
		infoRequestTask.AddParam(Constants.PARAM_CITY, mCity);
		infoRequestTask.AddParam(Constants.PARAM_STATE_GEOID, mState);
		infoRequestTask.AddParam(Constants.PARAM_POSTALCODE, mPostelCode);
		infoRequestTask.AddParam(Constants.PARAM_COUNTRY_GEOID, mCountry);
		infoRequestTask.AddParam(Constants.PARAM_MOBILE, mMobile);
		infoRequestTask.AddParam(Constants.PARAM_LANDLINE, "");
		infoRequestTask.AddParam(Constants.PARAM_CONTACT_MECHID, "");

		infoRequestTask.execute();

	}

	private void toShipCustomer(String partyId, String shippingId) {
		infoRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.SHIPPIN_ADDRESS), this, this);

		infoRequestTask.AddParam("useShipAddr", "Y");
		infoRequestTask.AddParam("checkoutpage", "shippingaddress");
		infoRequestTask.AddParam("shipping_contact_mech_id", shippingId);
		infoRequestTask.AddParam("contactMechTypeId", "");
		infoRequestTask.AddParam("partyId", partyId);
		infoRequestTask.AddParam("paymentMethodTypeId", "");
		infoRequestTask.AddParam("createNew", "Y");
		infoRequestTask.AddParam("toNameBilling", "");
		infoRequestTask.AddParam("address2", "");
		infoRequestTask.AddParam("directionsBilling", "");

		infoRequestTask.AddParam("cityBilling", mCity);
		infoRequestTask.AddParam("countryGeoIdBilling", mCountry);
		infoRequestTask.AddParam("stateProvinceGeoId1", mState);
		infoRequestTask.AddParam("stateProvinceGeoIdBilling", mState);
		infoRequestTask.AddParam("mobileBilling", mMobile);
		infoRequestTask.AddParam("landlineBilling", "");
		infoRequestTask.AddParam("postalCodeBilling", mPostelCode);

		infoRequestTask.execute();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

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
			if (ldro.TELECOM_NUMBER != null && ldro.TELECOM_NUMBER.size() > 0) {
				pro = ldro.TELECOM_NUMBER.get(0);
			}

			if (ldro.POSTAL_ADDRESS != null && ldro.POSTAL_ADDRESS.size() > 0) {
				aro = ldro.POSTAL_ADDRESS.get(0);
			}
			if (ldro != null && ldro.person != null
					&& ldro.person.firstName != null) {
				etFullName.setText(ldro.person.firstName);
			}
			if (aro != null && aro.address1 != null) {
				etAddress.setText(aro.address1);
			}

			if (aro != null && aro.address2 != null) {
				etLandMark.setText(aro.address2);
			}

			if (aro != null && aro.postalCode != null) {
				etPinCode.setText(aro.postalCode);
			}

			if (aro != null && aro.city != null) {
				etCity.setText(aro.city);
			}

			if (pro != null && pro.contactNumber != null) {
				etPhoneNumber.setText(pro.contactNumber);
			}

			if (aro != null && aro.stateProvinceGeoId != null
					&& aro.stateProvinceGeoId.length() > 0) {
				String stateName = aro.stateProvinceGeoId;
				int count = 0;

				for (int i = 0; i < stateCode.length; i++) {

					String s = stateCode[i];
					if (s.compareToIgnoreCase(stateName) != 0) {
						count++;

					}

					if (s.compareToIgnoreCase(stateName) == 0) {

						sStateList.setSelection(count);
						break;

					}
				}
			}
		}
	}

	public void onTabPlacedOrder() {
		// UIUtilities.showConfirmationAlert(this,
		// R.string.dialog_title_information, R.string.under_construction,
		// R.string.dialog_button_Ok,
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// }
		// });
		//
		android.webkit.CookieManager.getInstance().removeAllCookie();
		vFlipper.showNext();
		tName = tabName.Payment;
		onTabChanged();
		mProgress = ProgressDialog.show(this, "Loading",
				"Please wait for a moment...");
		WebViewClient yourWebClient = new WebViewClient() {
			// Override page so it's load on my view only
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// This line we let me load only pages inside Firstdroid Webpage
				// if ( url.contains("firstdroid") == true )
				// //Load new URL Don't override URL Link
				// return false;
				mProgress.show();

				view.loadUrl(url);

				// Return true to override url loading (In this case do
				// nothing).
				return true;

			}

			public void onPageFinished(WebView view, String url) {
				if (mProgress.isShowing()) {
					mProgress.dismiss();

				}
			}

			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed(); // Ignore SSL certificate errors
			}
		};

		mWebView = (WebView) findViewById(R.id.webView1);

		mWebView.getSettings().setJavaScriptEnabled(true);
		// mWebView.getSettings().setBuiltInZoomControls(true);
		// mWebView.getSettings().setSupportZoom(true);
		mWebView.setWebViewClient(yourWebClient);
		// final Activity activity = (Activity) this;

		// mWebView.setWebChromeClient(new WebChromeClient() {
		//
		// public void onProgressChanged(WebView view, int progress) {
		// activity.setTitle("Loading...");
		// activity.setProgress(progress * 100);
		// if (progress == 100)
		// activity.setTitle("My title");
		// }
		// });
		mWebView.loadUrl(url);
		android.webkit.CookieManager.getInstance().removeAllCookie();

		// if (rCreditCard.isChecked() || rDebigCard.isChecked()) {
		// Intent cartActivity = new Intent(CartActivity.this,
		// CreditCardActivity.class);
		// startActivity(cartActivity);
		// } else {
		// UIUtilities.showConfirmationAlert(this,
		// R.string.dialog_title_information,
		// "Select a card type", R.string.dialog_button_Ok,
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog,
		// int which) {
		// }
		// });
		// }
	}

	public void onTabAddressSelection() {
		boolean isAllFilled = (etFullName.getText().toString().length() > 0)
				&& (etAddress.getText().toString().length() > 0)
				&& (etCity.getText().toString().length() > 0)
				&& (etPinCode.getText().toString().length() > 0)
				&& (etPhoneNumber.getText().toString().length() > 0);

		if (isAllFilled) {

			if (etPhoneNumber.getText().toString().length() < 10
					|| etPhoneNumber.getText().toString().length() > 10) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_information,
						"Mobile number is not valid",
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
			} else {
				showLogistic();
			}
		} else {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information,
					"Fill all (*) marked field", R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		}
	}

	public void onTabEmailSelection() {
		if (!option1.isChecked() && !option2.isChecked()) {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information, "Select user type",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		} else if (etEmail.getText().toString().length() == 0) {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information,
					"Email address field is empty", R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		} else if (!UIUtilities.isEmailValid(etEmail.getText().toString())) {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information,
					"Email address is not valid", R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		} else {
			vFlipper.showNext();
			tName = tabName.Address;

			if (option2.isChecked()) {
				MCommerceApp myApp = (MCommerceApp) getApplication();
				LoginResponseObject ldro = myApp.getLoggedInUser();
				PhoneResponseObject pro = null;
				AddressResponseObject aro = null;

				if (ldro == null) {

					UIUtilities
							.showConfirmationAlert(
									this,
									R.string.dialog_title_information,
									"You have not logged in, Please Enter your details",
									R.string.dialog_button_Ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent accountActivity = new Intent(
													CartActivity.this,
													AccountActivity.class);
											accountActivity.putExtra(
													"isFromCart", true);
											accountActivity.putExtra("user",
													etEmail.getText()
															.toString());
											startActivity(accountActivity);

										}
									});
				} else {

					if (ldro.TELECOM_NUMBER != null
							&& ldro.TELECOM_NUMBER.size() > 0) {
						pro = ldro.TELECOM_NUMBER.get(0);
					}

					if (ldro.POSTAL_ADDRESS != null
							&& ldro.POSTAL_ADDRESS.size() > 0) {
						aro = ldro.POSTAL_ADDRESS.get(0);
					}
					if (ldro != null && ldro.person != null
							&& ldro.person.firstName != null) {
						etFullName.setText(ldro.person.firstName);
					}
					if (aro != null && aro.address1 != null) {
						etAddress.setText(aro.address1);
					}

					if (aro != null && aro.address2 != null) {
						etLandMark.setText(aro.address2);
					}

					if (aro != null && aro.postalCode != null) {
						etPinCode.setText(aro.postalCode);
					}

					if (aro != null && aro.city != null) {
						etCity.setText(aro.city);
					}

					if (pro != null && pro.contactNumber != null) {
						etPhoneNumber.setText(pro.contactNumber);
					}

					if (aro != null && aro.stateProvinceGeoId != null
							&& aro.stateProvinceGeoId.length() > 0) {
						String stateName = aro.stateProvinceGeoId;
						int count = 0;

						for (int i = 0; i < stateCode.length; i++) {

							String s = stateCode[i];
							if (s.compareToIgnoreCase(stateName) != 0) {
								count++;

							}

							if (s.compareToIgnoreCase(stateName) == 0) {

								sStateList.setSelection(count);
								break;

							}
						}
					}
				}
			}
		}

	}

	public void onTabChanged() {
		bTabEmail.setBackgroundResource(R.drawable.cart_white_border);
		bTabAddress.setBackgroundResource(R.drawable.cart_white_border);
		bTabSummary.setBackgroundResource(R.drawable.cart_white_border);
		bTabPayment.setBackgroundResource(R.drawable.cart_white_border);

		if (tName != null && tName == tabName.Email) {
			bTabEmail.setBackgroundResource(R.drawable.cart_blue_border);
		} else if (tName != null && tName == tabName.Address) {
			bTabAddress.setBackgroundResource(R.drawable.cart_blue_border);
		} else if (tName != null && tName == tabName.Summary) {
			bTabSummary.setBackgroundResource(R.drawable.cart_blue_border);
		} else if (tName != null && tName == tabName.Payment) {
			bTabPayment.setBackgroundResource(R.drawable.cart_blue_border);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (tName == tabName.Email) {
				finish();
			} else if (tName == tabName.Address
					&& mApp.getLoggedInUser() != null) {
				finish();
			} else if (tName == tabName.Payment) {
				mWebView.clearCache(true);
				CookieSyncManager.createInstance(this);
				CookieManager cookieManager = CookieManager.getInstance();
				cookieManager.removeAllCookie();
				android.webkit.CookieManager.getInstance().removeAllCookie();
				Intent intent = new Intent();
				setResult(Constants.CART_REFRESH, intent);
				finish();
				return true;
			} else {
				vFlipper.showPrevious();
				if (tName != null && tName == tabName.Address) {
					tName = tabName.Email;
				} else if (tName != null && tName == tabName.Summary) {
					tName = tabName.Address;
				} else if (tName != null && tName == tabName.Payment) {
					tName = tabName.Summary;
				}
				onTabChanged();
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);

	}

	public void setStateCode() {
		this.stateCode = getResources()
				.getStringArray(R.array.state_array_code);
	}
}
