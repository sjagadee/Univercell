/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ViewAnimator;
import app.nichepro.base.MCommerceApp;
import app.nichepro.fragmenttab.account.AccountActivity;
import app.nichepro.fragmenttab.cart.CartItemListActivity;
import app.nichepro.fragmenttab.home.BulkOrderActivity;
import app.nichepro.fragmenttab.home.MobileInsuranceActivity;
import app.nichepro.fragmenttab.home.TechSupport;
import app.nichepro.fragmenttab.shop.ProductDetailActivity;
import app.nichepro.fragmenttab.shop.ProductListActivity;
import app.nichepro.fragmenttab.shop.ShopActivity;
import app.nichepro.fragmenttab.store.StoreActivity;
import app.nichepro.model.AllStoresListResponseObject;
import app.nichepro.model.AllStoresResponseObject;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.CartItemListResponseObject;
import app.nichepro.model.CatalogCategoryListResponseObject;
import app.nichepro.model.CatalogListResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.OfferListResponseObject;
import app.nichepro.model.OfferProductListResponseObject;
import app.nichepro.model.PhoneContactObject;
import app.nichepro.model.ProductDetailListResponseObject;
import app.nichepro.model.ProductListResponseObject;
import app.nichepro.model.ProductResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.HorizontalListView;
import app.nichepro.util.ImageLoader;
import app.nichepro.util.Log;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestClient;
import app.nichepro.util.WebRequestTask;

import com.google.android.gcm.GCMRegistrar;

public class HomeScreenActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener, OnEditorActionListener,
		UpdateUiFromAdapterListener, OnItemSelectedListener {
	public enum tabName {
		FirstTimeLoadingScreen, LoadingScreen, HomeScreen,
	}
	Button btnGo;

	public tabName tName;
	private TextView guestName;
	private ViewAnimator viewAnimator;
	private WebRequestTask productRequestTask;
	private ResponseMesssagType msgType;
	private LinearLayout mOfferLayout;
	private LinearLayout mContent;
	private MCommerceApp mApp;
	private OfferListResponseObject mOfferList;
	private Button bSkip;
	private Button bRegister;
	private EditText etEmail;
	private EditText etMobile;

	public void loadFirstTimeRegistrationScreen() {

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String name = preferences.getString(Constants.KEY_IS_FIRST_TIME, "");
		if (name.equalsIgnoreCase(Constants.VALUE_IS_FIRST_TIME)) {
			viewAnimator.showNext();
		} else {
			bSkip = (Button) findViewById(R.id.bSkip);
			bRegister = (Button) findViewById(R.id.bRegister);
			etEmail = (EditText) findViewById(R.id.etEmail);
			etMobile = (EditText) findViewById(R.id.etPhone);
			bSkip.setOnClickListener(this);
			bRegister.setOnClickListener(this);

			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(Constants.KEY_IS_FIRST_TIME,
					Constants.VALUE_IS_FIRST_TIME);
			editor.commit();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mApp.setmOfferList(mOfferList);
	}

	public void registerForRemoteNotification() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, "358401258485");
		} else {
			Log.v("TAG", "Already registered");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		// registerForRemoteNotification();
		tName = tabName.LoadingScreen;
		ImageLoader.initialize(this);
		mApp = (MCommerceApp) getApplication();

		guestName = (TextView) findViewById(R.id.guestChange);
		viewAnimator = (ViewAnimator) findViewById(R.id.viewFlipper);
		mOfferLayout = (LinearLayout) findViewById(R.id.offer_layout);
		Button btnBuy = (Button) findViewById(R.id.bBuy);
		Button btnShop = (Button) findViewById(R.id.bShop);
		Button btnAccount = (Button) findViewById(R.id.bAccount);
		Button btnStore = (Button) findViewById(R.id.bStoreLocator);
		Button btnMobileInsurance = (Button) findViewById(R.id.bMobileInsurance);
		Button btnSync = (Button) findViewById(R.id.bPhotoContest);
		Button btnPhotoContest = (Button) findViewById(R.id.bSyncContact);

		Button btnTechSupport = (Button) findViewById(R.id.bTechSupport);
		Button btnBulkOrder = (Button) findViewById(R.id.bBulkOrder);
		eSearch = (AutoCompleteTextView) findViewById(R.id.etSearch);
		eSearch.setOnEditorActionListener(this);
		sFilterList = (Spinner) findViewById(R.id.sFilter);
		sFilterList.setOnItemSelectedListener(this);
		btnGo = (Button)findViewById(R.id.bGo);
		sFilterList.setSelection(mApp.iSelectedSearch);
		btnBuy.setOnClickListener(this);
		btnShop.setOnClickListener(this);
		btnAccount.setOnClickListener(this);
		btnStore.setOnClickListener(this);
		btnMobileInsurance.setOnClickListener(this);
		btnSync.setOnClickListener(this);
		btnPhotoContest.setOnClickListener(this);
		btnBulkOrder.setOnClickListener(this);
		btnTechSupport.setOnClickListener(this);
		btnGo.setOnClickListener(this);

		mContent = (LinearLayout) findViewById(R.id.panelContent);
		mOfferList = mApp.getmOfferList();

		if ((mApp.getStoreId() == null || mApp.getSessionId() == null)) {
			requestForStores();
		} else {
			if (savedInstanceState != null
					&& savedInstanceState.getParcelable("offer") != null) {
				mOfferList = savedInstanceState.getParcelable("offer");
			} else {
				// requestForInitialHomeCategory();
			}
		}

		if (mOfferList != null) {
			if (tName != null && tName == tabName.LoadingScreen) {
				tName = tabName.HomeScreen;
				viewAnimator.showNext();
				viewAnimator.showNext();
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}

			populateView();
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parentView, View arg1,
			int position, long arg3) {
		mApp.iSelectedSearch = position;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putString("tabname", tName.toString());
		outState.putParcelable("offer", mOfferList);
		int isVisible = mContent.getVisibility();
		outState.putInt("drawer", isVisible);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		int isVisible = savedInstanceState.getInt("drawer");
		if (isVisible == 0) {
			mContent.setVisibility(View.VISIBLE);
		}
	}

	public void requestForShowCart() {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_SHOW_CART), this, this);
		productRequestTask.execute();
	}

	public void requestForCategory() {

		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_OFFERS), this, this,
				WebRequestClient.RequestMethod.POST, true);
		productRequestTask.AddParam(Constants.PARAM_IS_FIRST_REQUEST,
				Constants.VALUE_NO);

		productRequestTask.execute();
	}

	public void requestForRegistrationAtInstallation() {

		if (etEmail != null && etMobile != null) {

			if (etEmail.getText().toString().compareTo("") == 0) {
				showEmptyAlert("Enter email id");
			} else if (!UIUtilities.isEmailValid(etEmail.getText().toString())) {
				showEmptyAlert("Invalid email id");
			} else if (etMobile.getText().toString().compareTo("") == 0) {
				showEmptyAlert("Enter mobile number");
			} else if (etMobile.getText().toString().length() != 10) {
				showEmptyAlert("Invalid mobile number");
			} else {

				productRequestTask = new WebRequestTask(
						WebLinks.getLoginLink(WebLinks.REGISTER_AT_INSTALLATION),
						this, this, WebRequestClient.RequestMethod.POST);
				productRequestTask.AddParam(
						Constants.PARAM_EMAIL_PRODUCT_STORE_ID,
						mApp.getStoreId());
				productRequestTask.AddParam(Constants.PARAM_CUSTOMER_EMAIL,
						etEmail.getText().toString());
				productRequestTask.AddParam(
						Constants.PARAM_CUSTOMER_MOBILE_CONTACT, etMobile
								.getText().toString());
				productRequestTask.AddParam(Constants.PARAM_PASSWORD, "123456");
				productRequestTask.AddParam(Constants.PARAM_CONFIRM_PASSWORD,
						"123456");

				productRequestTask.execute();
			}
		}
	}

	public void requestForInitialHomeCategory() {

		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_OFFERS), this, this,
				WebRequestClient.RequestMethod.POST, true);
		productRequestTask.AddParam(Constants.PARAM_IS_FIRST_REQUEST,
				Constants.VALUE_YES);

		productRequestTask.execute();
	}

	public void requestForStores() {

		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_STORES), this, this,
				WebRequestClient.RequestMethod.POST, true);

		productRequestTask.execute();
	}

	public void requestForProductDetail(String productCatID) {
		int isVisible = mContent.getVisibility();
		if (isVisible != 0) {
			productRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.GET_PRODUCT_DETAIL), this, this);
			productRequestTask.AddParam(Constants.PARAM_PRODUCT_ID,
					productCatID);

			productRequestTask.execute();
		}
	}

	public void populateView() {

		LayoutInflater factory = LayoutInflater.from(this);
		if (mOfferList != null
				&& ((LinearLayout) mOfferLayout).getChildCount() > 0) {
			((LinearLayout) mOfferLayout).removeAllViews();
		}
		// final OfferListResponseObject offerList = mApp.getmOfferList();
		for (int i = 0; i < mOfferList.offersList.size(); i++) {
			OfferProductListResponseObject oplro = mOfferList.offersList.get(i);

			LinearLayout horizontolLayout = (LinearLayout) factory.inflate(
					R.layout.horizontal_image_screen, null);
			ImageView leftImage = (ImageView) horizontolLayout
					.findViewById(R.id.leftimg);
			ImageView rightImage = (ImageView) horizontolLayout
					.findViewById(R.id.rightimg);
			horizontolLayout.setPadding(0, 1, 0, 0);
			TextView titleView = (TextView) horizontolLayout
					.findViewById(R.id.cat_title);
			titleView.setText(oplro.prodCatalogCateType);
			if (oplro.productsList.size() == 0) {
				horizontolLayout.setVisibility(View.GONE);
				titleView.setVisibility(View.GONE);
			}

			android.util.Log.i("size", oplro.productsList.size() + "");
			HorizontalListView listview = (HorizontalListView) horizontolLayout
					.findViewById(R.id.listview);

			listview.leftImgView = leftImage;
			listview.rightImgView = rightImage;
			HomeHorizontalScrollViewAdapter mAdapter = new HomeHorizontalScrollViewAdapter(
					oplro, this, this, leftImage, rightImage, true);

			listview.setAdapter(mAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

				}
			});

			mOfferLayout.addView(horizontolLayout);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mApp.setContinueShopping(false);
		if (mApp.getLoggedInUser() != null
				&& mApp.getLoggedInUser().person != null) {

			guestName.setText(mApp.getLoggedInUser().person.firstName);
		} else {
			guestName.setText("Guest");
		}

	}

	public void loadPhotoContest() {

		if (mApp.getLoggedInUser() == null) {
			Intent accountActivity = new Intent(HomeScreenActivity.this,
					AccountActivity.class);
			accountActivity.putExtra("isFromHome", true);
			startActivityForResult(accountActivity,
					Constants.SUCCESSFULLY_LOGIN_REQUESTED_CODE);

		} else {
			loadPhotoContestView();
		}
	}

	public void syncContactData() {

		if (mApp.getLoggedInUser() == null) {
			Intent accountActivity = new Intent(HomeScreenActivity.this,
					AccountActivity.class);
			accountActivity.putExtra("isForSyncContact", true);
			startActivityForResult(accountActivity,
					Constants.SUCCESSFULLY_LOGIN_REQUESTED_FOR_SYNC);

		} else {
			launchSyncOption();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.SUCCESSFULLY_LOGIN_REQUESTED_CODE
				&& data != null) {
			loadPhotoContestView();
		} else if (requestCode == Constants.SUCCESSFULLY_LOGIN_REQUESTED_FOR_SYNC
				&& data != null) {
			launchSyncOption();
		}
	}

	public void loadPhotoContestView() {
		Intent photoActivity = new Intent(HomeScreenActivity.this,
				PhotoContestActivity.class);
		startActivity(photoActivity);
	}

	public void syncContact() {
		ArrayList<PhoneContactObject> contactDataList = new ArrayList<PhoneContactObject>();
		ContentResolver cr = getContentResolver();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null,
				ContactsContract.Contacts.DISPLAY_NAME + " ASC");
		ProgressDialog progress = new ProgressDialog(this);
		progress.setTitle("Please Wait!!");
		progress.setMessage("Wait!!");
		progress.setCancelable(false);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
		while (cursor.moveToNext()) {
			try {
				PhoneContactObject pco = new PhoneContactObject();
				String contactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				pco.displayName = name;

				if (Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					pco.phoneNumberList = new ArrayList<String>();
					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					while (phones.moveToNext()) {
						String phoneNumber = phones
								.getString(phones
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						pco.phoneNumberList.add(phoneNumber);
					}
					phones.close();
				}

				Cursor emailCur = cr.query(
						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
								+ " = ?", new String[] { contactId }, null);
				pco.emailIdList = new ArrayList<String>();
				while (emailCur.moveToNext()) {
					// This would allow you get several email addresses
					// if the email addresses were stored in an array
					String email = emailCur
							.getString(emailCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					pco.emailIdList.add(email);

					// String emailType = emailCur
					// .getString(emailCur
					// .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
				}
				emailCur.close();

				contactDataList.add(pco);
			} catch (Exception e) {
			}
		}

		progress.cancel();
		requestForPushContact(contactDataList);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		android.util.Log.i("asdf", "asdf");
	}

	public void requestForPushContact(ArrayList<PhoneContactObject> contactList) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLoginLink(WebLinks.GET_PUSH_CONTACTS), this, this);
		productRequestTask.AddParam(Constants.PARAM_NO_RECORDS,
				contactList.size() + "");
		for (int i = 0; i < contactList.size(); i++) {
			PhoneContactObject pco = contactList.get(i);
			String name = "name" + i;
			String mobileNo = "mobileNo" + i;
			String email = "email" + i;
			productRequestTask.AddParam(name, pco.displayName);
			String mobile = "";
			if (pco.phoneNumberList != null && pco.phoneNumberList.size() > 0) {
				mobile = pco.phoneNumberList.get(0);
			}
			String emailId = "";
			if (pco.emailIdList != null && pco.emailIdList.size() > 0) {
				emailId = pco.emailIdList.get(0);
			}

			productRequestTask.AddParam(mobileNo, mobile);
			productRequestTask.AddParam(email, emailId);

		}
		productRequestTask.execute();
	}

	public void launchSyncOption() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Sync contact");
		// Set an EditText view to get user input
		alert.setMessage("Sync contact from Phone to Account");
		alert.setPositiveButton("Sync", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				syncContact();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		

		switch (v.getId()) {
		case R.id.bGo:
			requestForProductSearchList(eSearch.getText().toString());
			break;
		case R.id.bSkip:
			viewAnimator.showNext();
			break;
		case R.id.bRegister:
			requestForRegistrationAtInstallation();
			break;
		case R.id.bSyncContact:
			syncContactData();
			break;
		case R.id.bPhotoContest:
			loadPhotoContest();
			break;
		case R.id.bBuy:
			requestForShowCart();
			break;
		case R.id.bShop:
			requestForInitialCategory();
			break;
		case R.id.bAccount:
			Intent accountActivity = new Intent(HomeScreenActivity.this,
					AccountActivity.class);
			startActivity(accountActivity);
			break;
		case R.id.bStoreLocator:
			Intent storeActivity = new Intent(HomeScreenActivity.this,
					StoreActivity.class);
			startActivity(storeActivity);
			break;
		case R.id.bMobileInsurance:
			Intent mobInsurance = new Intent(HomeScreenActivity.this,
					MobileInsuranceActivity.class);
			startActivity(mobInsurance);
			break;

		case R.id.bTechSupport:
			Intent techSupportActivity = new Intent(HomeScreenActivity.this,
					TechSupport.class);
			startActivity(techSupportActivity);
			break;
		case R.id.bBulkOrder:
			Intent bulkOrderActivity = new Intent(HomeScreenActivity.this,
					BulkOrderActivity.class);
			startActivity(bulkOrderActivity);
			break;
		default:
			break;
		}
	}

	public void requestForInitialCategory() {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_CATALOG_CATEGORY), this, this);

		productRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		Log.i("Response", "as");
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		UIUtilities.hideKeyboard(this, eSearch);
		if (this.msgType == ResponseMesssagType.RegistrationMessageType) {
			showEmptyAlert("You Have Successfully Registered With Us, Enjoy Shopping!");
			viewAnimator.showNext();
		} else if (this.msgType == ResponseMesssagType.PushSyncMessageType) {
			UIUtilities
					.showConfirmationAlert(
							this,
							R.string.dialog_title_information,
							"Your Phone Contacts Has Been uploaded to Univercell Account!",
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
		} else if (this.msgType == ResponseMesssagType.ShowCartMessageType) {
			CartItemListResponseObject cilro = (CartItemListResponseObject) items
					.get(0);
			if (cilro.cartItems != null && cilro.cartItems != null
					&& cilro.cartItems.size() > 0) {
				mApp.setContinueShopping(true);
				Intent prodActivity = new Intent(HomeScreenActivity.this,
						CartItemListActivity.class);
				prodActivity.putExtra("cartItemlist", cilro);

				startActivity(prodActivity);
			} else {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_information,
						"Your shopping cart is empty, please add an item!!!",
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
			}

		} else if (this.msgType == ResponseMesssagType.GetAllStoresMessageType) {
			AllStoresListResponseObject aslro = (AllStoresListResponseObject) items
					.get(0);
			if (aslro != null && aslro.productStoreList != null
					&& aslro.productStoreList.size() > 0) {
				AllStoresResponseObject asro = null;
				for (AllStoresResponseObject element : aslro.productStoreList) {
					if (element.storeName != null
							&& element.storeName.compareTo("UniverCell Store") == 0) {
						asro = element;
					}
				}
				if (asro != null) {
					mApp.setStoreId(asro.productStoreId);
					mApp.setSessionId(aslro.jsessionid);
					requestForInitialHomeCategory();
				} else {
					UIUtilities.showServerError(this);
				}
			} else {
				UIUtilities.showServerError(this);
			}

		} else if (this.msgType == ResponseMesssagType.CatalogCategoryMessageType) {
			if (items != null && items.size() > 0) {
				CatalogCategoryListResponseObject clro = (CatalogCategoryListResponseObject) items
						.get(0);
				if (clro != null && clro.catalogCategoryList != null
						&& clro.catalogCategoryList.size() > 0) {
					Intent shopActivity = new Intent(HomeScreenActivity.this,
							ShopActivity.class);
					shopActivity.putExtra("cataloglist", clro);
					startActivity(shopActivity);
				} else {
					UIUtilities.showNoResultfound(this);
				}
			} else {
				UIUtilities.showNoResultfound(this);
			}
		} else if (this.msgType == ResponseMesssagType.ProductDetailMessageType) {
			ProductDetailListResponseObject clro = (ProductDetailListResponseObject) items
					.get(0);

			if (clro != null && clro.productDetail != null
					&& clro.productDetail.size() > 0) {
				Intent prodActivity = new Intent(HomeScreenActivity.this,
						ProductDetailActivity.class);
				prodActivity.putExtra("productdetail", clro);
				startActivity(prodActivity);
			} else {
				UIUtilities.showNoResultfound(this);
			}
		} else if (this.msgType == ResponseMesssagType.CatalogMessageType) {
			CatalogListResponseObject clro = (CatalogListResponseObject) items
					.get(0);
			if (clro != null && clro.response != null
					&& clro.response.size() > 0) {
				Intent shopActivity = new Intent(HomeScreenActivity.this,
						ShopActivity.class);
				shopActivity.putExtra("cataloglist", clro);
				startActivity(shopActivity);
			} else {
				// No cate found
			}
		} else if (this.msgType == ResponseMesssagType.OfferListMessageType) {
			OfferListResponseObject clro = (OfferListResponseObject) items
					.get(0);
			if (clro != null && clro.offersList != null
					&& clro.offersList.size() > 0) {
				if (this.mOfferList == null) {
					this.mOfferList = clro;

					mApp.setmOfferList(clro);
					if (tName != null && tName == tabName.LoadingScreen) {
						tName = tabName.HomeScreen;
						viewAnimator.showNext();

					}
					populateView();
					requestForCategory();
					loadFirstTimeRegistrationScreen();
				} else {
					populateOfferResponseValue(clro);
				}
			} else {
				UIUtilities.showNoResultfound(this);
			}
		} else if (this.msgType == ResponseMesssagType.KeywordSearchMessageType) {
			ProductListResponseObject clro = (ProductListResponseObject) items
					.get(0);
			if (clro != null
					&& ((clro.exactSearchResults != null && clro.exactSearchResults
							.size() > 0) || (clro.productsList != null && clro.productsList
							.size() > 0))) {
				Intent prodActivity = new Intent(HomeScreenActivity.this,
						ProductListActivity.class);
				clro.mTitle = eSearch.getText().toString();
				prodActivity.putExtra("productlist", clro);

				startActivity(prodActivity);
			} else {
				UIUtilities.showNoResultfound(this);
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

		Log.i("Response", "as");
	}

	public void populateOfferResponseValue(OfferListResponseObject olro) {
		if (mOfferList != null && mOfferList.offersList != null
				&& mOfferList.offersList.size() > 0) {
			for (int i = 0; i < mOfferList.offersList.size(); i++) {
				OfferProductListResponseObject existing = mOfferList.offersList
						.get(i);
				OfferProductListResponseObject latest = olro.offersList.get(i);
				for (int j = 0; j < existing.productsList.size(); j++) {
					ProductResponseObject existingProduct = existing.productsList
							.get(j);
					ProductResponseObject latestProduct = latest.productsList
							.get(j);
					latestProduct.prodImg = existingProduct.prodImg;
				}
			}
			mOfferList = olro;
			mApp.setmOfferList(mOfferList);
			populateView();
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub

		UIUtilities.showErrorWithOkButton(this, ex.getMessage());

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		String error = "Error";

		if (items != null && items.size() > 0) {
			ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
			error = ero.getErrorText();
		}
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information, error,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int isVisible = mContent.getVisibility();
			if (isVisible == 0) {
				mContent.setVisibility(View.GONE);
			} else {

				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_warning, R.string.app_exit_msg,
						R.string.exit, R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								mApp.setLoggedInUser(null);
								mApp.setSessionId(null);
								mApp.setStoreId(null);
								mApp.setmOfferList(null);
								if (mApp.getAuthButton() != null) {
									mApp.getAuthButton().logout();
								}
								finish();
								System.exit(0);
							}
						}, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

			}
			return false;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			requestForProductSearchList(v.getText().toString());
			return true;
		}
		return false;
	}

	public void requestForProductSearchList(String searchKeyWord) {
		UIUtilities.hideKeyboard(this, eSearch);
		if (searchKeyWord != null && searchKeyWord.length() > 0) {
			int index = sFilterList.getSelectedItemPosition();
			productRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.GET_KEYWORD_SEARCH), this, this);

			productRequestTask.AddParam(Constants.PARAM_SEARCH_CATALOG_ID, "");
			productRequestTask.AddParam(Constants.PARAM_PAGING,
					Constants.VALUE_PAGING_Y);
			productRequestTask.AddParam(Constants.PARAM_SEARCH_STRING,
					searchKeyWord);
			productRequestTask.AddParam(Constants.PARAM_SEARCH_BY,
					filterCode[index]);
			productRequestTask.AddParam(Constants.PARAM_VIEW_INDEX, "0");
			productRequestTask.execute();
		} else {
			UIUtilities.showConfirmationAlert(this,
					R.string.dialog_title_information, R.string.empty_search,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		}

	}

	void showAlert() {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information, R.string.under_construction,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

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

	@Override
	public void updateUI(String message) {
		// TODO Auto-generated method stub
		requestForProductDetail(message);
	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
