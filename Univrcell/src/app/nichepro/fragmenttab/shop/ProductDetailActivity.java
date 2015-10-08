/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.shop;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.fragmenttab.account.AccountActivity;
import app.nichepro.fragmenttab.cart.CartItemListActivity;
import app.nichepro.fragmenttab.shop.BasePagerAdapter.OnItemChangeListener;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.CartItemListResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.ImageKeyValueResponseObject;
import app.nichepro.model.KeyValueObject;
import app.nichepro.model.ProductAttirbuteNameValueResponseObject;
import app.nichepro.model.ProductAttirbutesResponseObject;
import app.nichepro.model.ProductDetailListResponseObject;
import app.nichepro.model.ProductResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.ImageLoader;
import app.nichepro.util.ImageLoaderHandler;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.GooglePlusUtil;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusShare;

public class ProductDetailActivity extends BaseDefaultActivity implements
		ResponseParserListener, android.view.View.OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener {

	SharedPreferences sharedPrefs;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	String TAG = "ProductDetailActivity";
	private ProductDetailListResponseObject mProductDetailObject;
	private ProductResponseObject mProductResposeObject;
	private WebRequestTask productRequestTask;
	private ResponseMesssagType msgType;
	private LinearLayout mMainLayout;
	private TextView phoneName;
	private TextView phonePrice;
	private TextView offerPrice;
	private TextView offerSymbol;
	private TextView brandName;
	private TextView priceSymbol;
	private Button bNow;
	private Button share;
	private String prodUrl;
	private TextView tvSoldOut;
	private ImageView zoomView;

	private RelativeLayout imageLayout;
	private LinearLayout priceLayout;
	private LinearLayout offerlayout;
	private LinearLayout preLaunchLayout;
	private TextView preLaunchMsg;
	private TextView prePriceMsg;
	private LinearLayout preLaunchExpectedPriceLayout;
	private TextView preLaunchExpectedPrice;
	private Spinner sBankPicker;
	private Spinner sEmiPicker;
	private String mSelectedBank;
	private String mSelectedEmi;
	private TextView mEmiText;
	private LinearLayout emiLayoutKeyValue;
	private LinearLayout emiLayout;

	private GalleryViewPager mViewPager;
	private List<ImageKeyValueResponseObject> mImageList;

	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private TextView tvImageCount;
	private int mImageIndex;
	private AlertDialog mAlert;

	private Button bCancelSharing;
	private Button bPost;
	private app.nichepro.util.RoundedImageView iprofileImgView;
	private EditText etPost;
	private ToggleButton bFBShare;
	private ToggleButton bGmailShare;
	private LinearLayout bShareAll;
	private UiLifecycleHelper uiHelper;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private ImageView productImage;
	private TextView titleName;

	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (session.isOpened()) {
				Log.v("ReferEmployee",
						"Facebook Access Token : " + session.getAccessToken());

			}
		}
	}

	/** The ihandler. */
	private ImageLoaderHandler ihandler = new ImageLoaderHandler() {
		@Override
		protected boolean handleImageLoaded(Bitmap bitmap, Message msg,
				int rowIndex, int colIndex, String imageUrl,
				boolean noImageFound) {
			iprofileImgView.setImageBitmap(bitmap);
			return false;
		}
	};

	private void intializeLayout() {
		mImageIndex = 0;
		mMainLayout = (LinearLayout) findViewById(R.id.productlayout);
		share = (Button) findViewById(R.id.bShareBtn);
		brandName = (TextView) findViewById(R.id.tvBrandName);
		phoneName = (TextView) findViewById(R.id.tvName);
		phonePrice = (TextView) findViewById(R.id.tvPrice);
		offerPrice = (TextView) findViewById(R.id.tvOfferPrice);
		offerSymbol = (TextView) findViewById(R.id.tvOfferSymbol);
		priceSymbol = (TextView) findViewById(R.id.tvPriceSymbol);
		zoomView = (ImageView) findViewById(R.id.zoomImage);
		priceLayout = (LinearLayout) findViewById(R.id.tvPriceLayout);
		offerlayout = (LinearLayout) findViewById(R.id.tvOfferPriceLayout);
		preLaunchLayout = (LinearLayout) findViewById(R.id.tvPreLaunchLayout);
		preLaunchMsg = (TextView) findViewById(R.id.tvPreLaunchMsg);
		prePriceMsg = (TextView) findViewById(R.id.tvPrePriceMsg);
		preLaunchExpectedPriceLayout = (LinearLayout) findViewById(R.id.tvPreLaunchExpectedLayout);
		preLaunchExpectedPrice = (TextView) findViewById(R.id.tvExpectedPrice);
		bNow = (Button) findViewById(R.id.buyNow);
		tvSoldOut = (TextView) findViewById(R.id.tvSoldOut);
		sBankPicker = (Spinner) findViewById(R.id.sBankSpinner);
		sEmiPicker = (Spinner) findViewById(R.id.sEmiSpinner);
		mEmiText = (TextView) findViewById(R.id.header_title);
		tvImageCount = (TextView) findViewById(R.id.tvImageCount);
		emiLayoutKeyValue = (LinearLayout) findViewById(R.id.emiLayoutKeyValue);

		emiLayout = (LinearLayout) findViewById(R.id.emiLayout);

		mEmiText.setText("EMI ON CREDIT CARD");
		imageLayout = (RelativeLayout) findViewById(R.id.image_layout);
		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(this);
		bNow.setOnClickListener(this);
		share.setOnClickListener(this);

	}

	public void alignImages(Bundle savedInstanceState) {
		ProductResponseObject pro = mProductDetailObject.productDetail.get(0);

		ImageView leftImgView = (ImageView) findViewById(R.id.leftimg);
		ImageView rightImgView = (ImageView) findViewById(R.id.rightimg);
		leftImgView.setVisibility(View.GONE);
		rightImgView.setVisibility(View.GONE);

		if (savedInstanceState == null) {
			mImageList = new ArrayList<ImageKeyValueResponseObject>();
			for (int i = 0; i < pro.additionalImages.size(); i++) {
				ImageKeyValueResponseObject ikvro = new ImageKeyValueResponseObject();
				ikvro.link = pro.additionalImages.get(i);
				ikvro.prodImage = null;
				mImageList.add(ikvro);
			}
		} else {
			mImageList = savedInstanceState.getParcelableArrayList("images");
			mSelectedBank = savedInstanceState.getString("mSelectedBank");
			mSelectedEmi = savedInstanceState.getString("mSelectedEmi");
		}

		for (int i = 0; i < pro.additionalImages.size(); i++) {
			pro.additionalImages.set(i,
					pro.additionalImages.get(i).replaceAll(" ", "%20"));
		}

		UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this,
				pro.additionalImages, mImageList, null, null);
		pagerAdapter.setOnItemChangeListener(new OnItemChangeListener() {
			@Override
			public void onItemChange(int currentPosition) {
				ProductResponseObject pro = mProductDetailObject.productDetail
						.get(0);
				String str = currentPosition + 1 + " of "
						+ pro.additionalImages.size();
				tvImageCount.setText(str);
				mImageIndex = currentPosition;
			}
		});

		// imageLayout.setBackgroundResource(R.drawable.image_border);
		mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
		mViewPager.isFromProductDetail = true;
		mViewPager.setAdapter(pagerAdapter);
		imageLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadZoomImageView();

			}
		});

		zoomView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadZoomImageView();
			}
		});

	}

	public void loadZoomImageView() {
		Intent prodActivity = new Intent(ProductDetailActivity.this,
				GalleryUrlActivity.class);
		ProductResponseObject pro = mProductDetailObject.productDetail.get(0);
		prodActivity.putExtra("image", pro);
		prodActivity.putExtra("index", mImageIndex);
		startActivity(prodActivity);
	}

	private void setPreBooking(ProductResponseObject pro) {
		preLaunchLayout.setVisibility(View.VISIBLE);
		preLaunchExpectedPriceLayout.setVisibility(View.VISIBLE);
		preLaunchMsg.setText(pro.preBookingLaunchMsg);
		prePriceMsg.setText(pro.preBookingPriceMsg);
		preLaunchExpectedPrice.setText(pro.expectedPrice);
		priceLayout.setVisibility(View.GONE);
		offerlayout.setVisibility(View.GONE);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("images",
				(ArrayList<? extends Parcelable>) mImageList);
		outState.putString("mSelectedBank", mSelectedBank);
		outState.putString("mSelectedEmi", mSelectedEmi);
		// outState.putBoolean(PENDING_PUBLISH_KEY,
		// pendingPublishReauthorization);
		// uiHelper.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);

	}

	void shareInGooglePLus() {
		final int errorCode = GooglePlusUtil.checkGooglePlusApp(this);
		if (errorCode == GooglePlusUtil.SUCCESS) {
			try {
				ProductResponseObject pro = mProductDetailObject.productDetail
						.get(0);
				PlusShare.Builder builder = new PlusShare.Builder(this,
						mPlusClient)
						.setText(
								"Awesome, can't wait to get one of these "
										+ etPost.getText().toString())
						.setContentUrl(Uri.parse(pro.productUrl))
						.setType("text/plain")
						.setContentDeepLinkId("/pages/create", /**
						 * Deep-link
						 * identifier
						 */
						pro.productName, /** Snippet title */
						"Univercell for Android", /** Snippet description */
						Uri.parse(pro.productUrl));
				//
				Intent shareIntent = builder.getIntent();
				startActivityForResult(shareIntent, 0);
			} catch (Exception e) {
				Log.i("error=", e.toString());
				Toast.makeText(getApplicationContext(), e.toString(),
						Toast.LENGTH_SHORT).show();
			}

		} else {

			GooglePlusUtil.getErrorDialog(errorCode, this, 0).show();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		Log.i("onConnectionFailed", result.toString());
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
			} catch (SendIntentException e) {
				mPlusClient.connect();
			}
		}

	}

	private void setPrice(ProductResponseObject pro) {
		if (pro.basePrice.contains(".")) {
			String priceList[] = pro.basePrice.split(".");
			if (priceList != null && priceList.length > 0) {
				int iPrice = Integer.parseInt(priceList[0]);

				if (iPrice < 3000) {
					emiLayout.setVisibility(View.GONE);
				}

				if (iPrice > 0) {
					priceSymbol
							.setTypeface(UIUtilities.createRupeeSymbol(this));
					priceSymbol.setText("`");
					phonePrice.setText(pro.basePrice);
				} else {
					phonePrice.setText("Not announced");
					priceSymbol.setVisibility(View.GONE);
					bNow.setVisibility(View.GONE);
				}
			} else {
				priceSymbol.setVisibility(View.GONE);
				phonePrice.setText("Not announced");
				bNow.setVisibility(View.GONE);
			}

		} else {
			int iPrice = Integer.parseInt(pro.basePrice);
			if (iPrice < 3000) {
				emiLayout.setVisibility(View.GONE);
			}
			if (iPrice > 0) {
				priceSymbol.setTypeface(UIUtilities.createRupeeSymbol(this));
				priceSymbol.setText("`");
				phonePrice.setText(pro.basePrice);
			} else {
				priceSymbol.setVisibility(View.GONE);
				phonePrice.setText("Not announced");
				bNow.setVisibility(View.GONE);
			}
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
		mPlusClient.connect();

	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
		mPlusClient.disconnect();

	}

	private void setOfferPrice(ProductResponseObject pro) {
		if (pro.priceAfterDiscount != null) {
			phonePrice.setPaintFlags(phonePrice.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);

			if (pro.priceAfterDiscount.contains(".")) {
				String priceList[] = pro.priceAfterDiscount.split(".");
				if (priceList != null && priceList.length > 0) {
					int iPrice = Integer.parseInt(priceList[0]);
					if (iPrice > 0) {
						offerSymbol.setTypeface(UIUtilities
								.createRupeeSymbol(this));
						offerSymbol.setText("`");
						offerPrice.setText(pro.priceAfterDiscount);
					} else {
						offerPrice.setText("Not announced");
						offerSymbol.setVisibility(View.GONE);
						bNow.setVisibility(View.GONE);
					}
				} else {
					offerSymbol.setVisibility(View.GONE);
					offerPrice.setText("Not announced");
					bNow.setVisibility(View.GONE);
				}

			} else {
				int iPrice = Integer.parseInt(pro.priceAfterDiscount);
				if (iPrice > 0) {
					offerSymbol
							.setTypeface(UIUtilities.createRupeeSymbol(this));
					offerSymbol.setText("`");
					offerPrice.setText(pro.priceAfterDiscount);
				} else {
					offerSymbol.setVisibility(View.GONE);
					offerPrice.setText("Not announced");
					bNow.setVisibility(View.GONE);
				}
			}

		} else {
			offerSymbol.setVisibility(View.GONE);
			offerPrice.setVisibility(View.GONE);
		}

	}

	private void setPriceInfoLayout() {
		ProductResponseObject pro = mProductDetailObject.productDetail.get(0);
		prodUrl = pro.productUrl;
		brandName.setText(pro.brandName);
		phoneName.setText(pro.productName);

		if (pro.allowPreBooking != null
				&& pro.allowPreBooking.compareTo("Y") == 0) {
			bNow.setText("Book Now");
			setPreBooking(pro);
		} else {
			setPrice(pro);
			setOfferPrice(pro);
		}

		if (pro.salesDiscontinuationDate != null) {
			checkProductAvail(pro.salesDiscontinuationDate);
		} else {
			tvSoldOut.setVisibility(View.GONE);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_detail);
		ImageLoader.initialize(this);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		Intent intent = getIntent();
		mProductDetailObject = intent.getParcelableExtra("productdetail");

		intializeLayout();
		alignImages(savedInstanceState);
		initializeBankSpinner();
		initializeEmiSpinner();
		setPriceInfoLayout();
		setUpTableLayout();
		// LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		// authButton.setReadPermissions(Arrays.asList("publish_actions"));
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			pendingPublishReauthorization = savedInstanceState.getBoolean(
					PENDING_PUBLISH_KEY, false);
		}
		// Session session = Session.getActiveSession();
		//
		// session.requestNewPublishPermissions(new
		// Session.NewPermissionsRequest(
		// this, PERMISSIONS));
		// requestPublishPermissions(session);

		// loadProdImage();

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}
		mPlusClient = new PlusClient.Builder(this, this, this)
				.setScopes(Scopes.PLUS_LOGIN)
				.setVisibleActivities("http://schemas.google.com/AddActivity",
						"http://schemas.google.com/BuyActivity").build();

	}

	private void requestPublishPermissions(Session session) {
		if (session != null) {
			Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
					this, PERMISSIONS).setRequestCode(REAUTH_ACTIVITY_CODE);
			session.requestNewPublishPermissions(newPermissionsRequest);
		}
	}

	// private static Session openActiveSession(Activity activity, boolean
	// allowLoginUI, StatusCallback callback, List<String> permissions) {
	// OpenRequest openRequest = new
	// OpenRequest(activity).setPermissions(permissions).setCallback(callback);
	// Session session =new Builder(activity).build();
	// if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) ||
	// allowLoginUI) {
	// Session.setActiveSession(session);
	// session.openForRead(openRequest);
	// return session;
	// }
	// return null;
	// }
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_RESOLVE_ERR && resultCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		} else if (requestCode == Constants.SUCCESSFULLY_LOGIN_REQUESTED_CODE
				&& data != null) {
			requstForAddToCart();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// uiHelper.onDestroy();
	}

	public void checkProductAvail(String date) {

		String dateList[] = date.split("T");
		String dd = null;
		String mm = null;
		String yy = null;
		if (dateList != null && dateList.length > 0) {
			String dList[] = dateList[0].split("-");
			if (dList != null && dList.length > 2) {
				mm = dList[0];
				dd = dList[1];
				yy = dList[2];
			}
		}
		Calendar setCal = Calendar.getInstance();
		Calendar curCal = Calendar.getInstance();

		if (mm != null && dd != null && yy != null
				&& UIUtilities.isIntNumber(mm) && UIUtilities.isIntNumber(dd)
				&& UIUtilities.isIntNumber(yy)) {
			setCal.set(Calendar.SECOND, 0);
			setCal.set(Calendar.MINUTE, 0);
			setCal.set(Calendar.HOUR_OF_DAY, 0);
			setCal.set(Calendar.MONTH, Integer.parseInt(mm));
			setCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));
			setCal.set(Calendar.YEAR, Integer.parseInt(yy));
			Log.i("Calender", "Week: " + setCal.get(Calendar.DAY_OF_WEEK));
			Log.i("Calender", "Year: " + setCal.get(Calendar.YEAR));
			Log.i("Calender", "Month: " + setCal.get(Calendar.MONTH));
			Log.i("Calender",
					"Day of Month: " + setCal.get(Calendar.DAY_OF_MONTH));
			Log.i("Calender",
					"Hour of Day: " + setCal.get(Calendar.HOUR_OF_DAY));
			Log.i("Calender", "Minut: " + setCal.get(Calendar.MINUTE));

			long smilis = setCal.getTimeInMillis();
			long cmilis = curCal.getTimeInMillis();

			if (smilis < cmilis) {
				bNow.setVisibility(View.GONE);
				tvSoldOut.setVisibility(View.VISIBLE);
			} else {
				tvSoldOut.setVisibility(View.GONE);
			}

			setCal.set(Calendar.DAY_OF_WEEK, curCal.get(Calendar.DAY_OF_WEEK));
			setCal.set(Calendar.YEAR, curCal.get(Calendar.YEAR));
			setCal.set(Calendar.MONTH, curCal.get(Calendar.MONTH));
			setCal.set(Calendar.DAY_OF_MONTH, curCal.get(Calendar.DAY_OF_MONTH));
			setCal.set(Calendar.HOUR_OF_DAY, curCal.get(Calendar.HOUR_OF_DAY));
			setCal.set(Calendar.MINUTE, curCal.get(Calendar.MINUTE));

			Log.i("Calender", "Week: " + setCal.get(Calendar.DAY_OF_WEEK));
			Log.i("Calender", "Year: " + setCal.get(Calendar.YEAR));
			Log.i("Calender", "Month: " + setCal.get(Calendar.MONTH));
			Log.i("Calender",
					"Day of Month: " + setCal.get(Calendar.DAY_OF_MONTH));
			Log.i("Calender",
					"Hour of Day: " + setCal.get(Calendar.HOUR_OF_DAY));
			Log.i("Calender", "Minute: " + setCal.get(Calendar.MINUTE));
		}
	}

	private void initializeBankSpinner() {

		if (this.mProductDetailObject != null
				&& this.mProductDetailObject.cardType != null
				&& this.mProductDetailObject.cardType.size() > 0) {

			final ArrayList<String> kList = new ArrayList<String>();
			ArrayList<String> vList = new ArrayList<String>();
			vList.add("Choose Bank");
			kList.add("tenure");
			for (Entry<String, String> entry : this.mProductDetailObject.cardType
					.entrySet()) {
				kList.add(entry.getKey());
				vList.add(entry.getValue());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, vList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sBankPicker.setAdapter(adapter);
			sBankPicker.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					int index = sBankPicker.getSelectedItemPosition();
					mSelectedBank = (String) kList.get(index);
					initializeEmiSpinner();
					setEmiLayout();

				}

				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			int index = sBankPicker.getSelectedItemPosition();
			if (mSelectedBank == null) {
				mSelectedBank = (String) kList.get(index);
			} else {
				for (int i = 0; i < kList.size(); i++) {
					if (mSelectedBank.compareTo(kList.get(i)) == 0) {
						sBankPicker.setSelection(i);
					}
				}

			}

		} else {
			sBankPicker.setVisibility(View.GONE);
		}
	}

	private void initializeEmiSpinner() {

		if (this.mProductDetailObject != null
				&& this.mProductDetailObject.emi != null
				&& this.mProductDetailObject.emi.size() > 0) {

			HashMap<String, String> hm = this.mProductDetailObject.emi
					.get(mSelectedBank);
			if (hm != null && hm.size() > 0) {
				ArrayList<String> vList = new ArrayList<String>();
				final ArrayList<String> kList = new ArrayList<String>();

				vList.add("Opt Tenure");
				kList.add("tenure");
				for (int i = 3; i <= 36; i++) {
					String str = "EMI" + i;
					if (hm.get(str) != null) {
						vList.add(hm.get(str));
						kList.add(str);
					}
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, vList);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sEmiPicker.setAdapter(adapter);
				sEmiPicker
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								int index = sEmiPicker
										.getSelectedItemPosition();
								mSelectedEmi = (String) kList.get(index);
								setEmiLayout();
							}

							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
				int index = sEmiPicker.getSelectedItemPosition();
				if (mSelectedEmi == null) {
					mSelectedEmi = (String) kList.get(index);
				} else {
					for (int i = 0; i < kList.size(); i++) {
						if (mSelectedEmi.compareTo(kList.get(i)) == 0) {
							sEmiPicker.setSelection(i);
						}
					}
				}
			} else {
				ArrayList<String> vList = new ArrayList<String>();

				vList.add("Opt Tenure");

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, vList);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sEmiPicker.setAdapter(adapter);
				sEmiPicker
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
							}

							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});

			}
		} else {
			sEmiPicker.setVisibility(View.GONE);
		}
	}

	private void setEmiLayout() {

		if (emiLayoutKeyValue != null
				&& ((LinearLayout) emiLayoutKeyValue).getChildCount() > 0) {
			((LinearLayout) emiLayoutKeyValue).removeAllViews();
		}

		if (mSelectedEmi != null && mSelectedEmi.length() > 0
				&& mSelectedBank != null && mSelectedBank.length() > 0
				&& this.mProductDetailObject != null
				&& this.mProductDetailObject.emiDetails != null
				&& this.mProductDetailObject.emiDetails.size() > 0
				&& mSelectedEmi.compareTo("tenure") != 0
				&& mSelectedBank.compareTo("tenure") != 0) {

			LayoutInflater factory = LayoutInflater.from(this);
			TableLayout tableLayout = (TableLayout) factory.inflate(
					R.layout.custom_tablelayout_cell, null);

			ArrayList<KeyValueObject> kvList = new ArrayList<KeyValueObject>();
			HashMap<String, String> hm = new HashMap<String, String>();
			hm = this.mProductDetailObject.emiDetails.get(mSelectedBank);

			hm.get(mSelectedEmi + "_" + "PREMIUM");
			KeyValueObject kvo = new KeyValueObject();
			kvo.key = "INSTALLMENT";
			kvo.value = hm.get(mSelectedEmi + "_" + "PREMIUM");
			kvList.add(kvo);

			hm.get(mSelectedEmi + "_" + "PROCESSING_FEE");
			kvo = new KeyValueObject();
			kvo.key = "PROC FEE";
			kvo.value = hm.get(mSelectedEmi + "_" + "PROCESSING_FEE");
			kvList.add(kvo);

			hm.get(mSelectedEmi + "_" + "TOTAL");
			kvo = new KeyValueObject();
			kvo.key = "TOTAL";
			kvo.value = hm.get(mSelectedEmi + "_" + "TOTAL");
			kvList.add(kvo);

			for (int i = 0; i < kvList.size(); i++) {
				kvo = kvList.get(i);
				TableRow tableRow = (TableRow) factory.inflate(
						R.layout.table_emi_cell_row, null);

				TextView key = (TextView) tableRow
						.findViewById(R.id.product_key);
				TextView value = (TextView) tableRow
						.findViewById(R.id.product_value);
				TextView offSym = (TextView) tableRow.findViewById(R.id.sym);

				key.setText(kvo.key);

				offSym.setTypeface(UIUtilities.createRupeeSymbol(this));
				offSym.setText("`");
				value.setText(kvo.value);
				tableLayout.addView(tableRow);
			}

			emiLayoutKeyValue.addView(tableLayout);

		}

	}

	private void setKeyFeature() {
		if (mProductDetailObject.productKeyFeatures != null
				&& mProductDetailObject.productKeyFeatures.size() > 0) {
			LayoutInflater factory = LayoutInflater.from(this);
			TableLayout tableLayout = (TableLayout) factory.inflate(
					R.layout.custom_tablelayout_cell, null);

			TableRow headerRow = (TableRow) factory.inflate(
					R.layout.table_header_row, null);

			TextView headerView = (TextView) headerRow
					.findViewById(R.id.header_title);
			headerView.setText("Key Features");

			tableLayout.addView(headerRow);

			for (int i = 0; i < mProductDetailObject.productKeyFeatures.size(); i++) {
				String keyFeature = mProductDetailObject.productKeyFeatures
						.get(i);

				TableRow tableRow = (TableRow) factory.inflate(
						R.layout.key_feature_cell_row, null);
				TextView key = (TextView) tableRow
						.findViewById(R.id.product_value);

				key.setText(keyFeature);
				tableLayout.addView(tableRow);
			}

			mMainLayout.addView(tableLayout);

		}

	}

	private void setBrandProductType() {

		ProductResponseObject paro = mProductDetailObject.productDetail.get(0);

		LayoutInflater factory = LayoutInflater.from(this);
		TableLayout tableLayout = (TableLayout) factory.inflate(
				R.layout.custom_tablelayout_cell, null);

		TableRow headerRow = (TableRow) factory.inflate(
				R.layout.table_header_row, null);

		TextView headerView = (TextView) headerRow
				.findViewById(R.id.header_title);
		headerView.setText("Product");

		tableLayout.addView(headerRow);

		for (int i = 0; i < 2; i++) {
			TableRow tableRow = (TableRow) factory.inflate(
					R.layout.table_cell_row, null);
			TextView key = (TextView) tableRow.findViewById(R.id.product_key);
			TextView value = (TextView) tableRow
					.findViewById(R.id.product_value);
			if (i == 0) {
				key.setText("Brand");
				value.setText(paro.brandName);

			} else {
				key.setText("Model");
				value.setText(paro.productName);

			}
			tableLayout.addView(tableRow);

		}

		mMainLayout.addView(tableLayout);

	}

	private void setProductDescription() {
		for (int i = 0; i < mProductDetailObject.productAttributes.size(); i++) {
			ProductAttirbutesResponseObject paro = mProductDetailObject.productAttributes
					.get(i);

			LayoutInflater factory = LayoutInflater.from(this);
			TableLayout tableLayout = (TableLayout) factory.inflate(
					R.layout.custom_tablelayout_cell, null);

			TableRow headerRow = (TableRow) factory.inflate(
					R.layout.table_header_row, null);

			TextView headerView = (TextView) headerRow
					.findViewById(R.id.header_title);
			headerView.setText(paro.attributeType);

			tableLayout.addView(headerRow);

			for (int j = 0; j < paro.attrNameAndValue.size(); j++) {

				TableRow tableRow = (TableRow) factory.inflate(
						R.layout.table_cell_row, null);
				TextView key = (TextView) tableRow
						.findViewById(R.id.product_key);
				TextView value = (TextView) tableRow
						.findViewById(R.id.product_value);
				ProductAttirbuteNameValueResponseObject panvro = paro.attrNameAndValue
						.get(j);
				key.setText(panvro.attrName);
				value.setText(panvro.attrValue);
				tableLayout.addView(tableRow);
			}

			mMainLayout.addView(tableLayout);

		}

	}

	public void setProductDesclaimer() {
		LayoutInflater factory = LayoutInflater.from(this);
		TableRow headerRow = (TableRow) factory.inflate(
				R.layout.table_header_row, null);

		TextView headerView = (TextView) headerRow
				.findViewById(R.id.header_title);
		headerView.setText("DISCLAIMER");
		TableRow tableRow = (TableRow) factory.inflate(R.layout.table_cell_row,
				null);
		TextView key = (TextView) tableRow.findViewById(R.id.product_key);
		TextView value = (TextView) tableRow.findViewById(R.id.product_value);
		value.setVisibility(View.GONE);
		key.setText(R.string.disclaimer);
		mMainLayout.addView(headerRow);
		mMainLayout.addView(tableRow);
	}

	public void setUpTableLayout() {
		setEmiLayout();
		setKeyFeature();
		setBrandProductType();
		setProductDescription();
		setProductDesclaimer();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		MCommerceApp mApp = (MCommerceApp) getApplication();
		if (mApp.isContinueShopping() == true) {
			mApp.setContinueShopping(false);
			finish();
		}

	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		UIUtilities.hideKeyboard(this, eSearch);

		if (this.msgType == ResponseMesssagType.AddItemMessageType
				|| this.msgType == ResponseMesssagType.ShowCartMessageType) {
			CartItemListResponseObject clro = (CartItemListResponseObject) items
					.get(0);
			if (clro != null && clro.cartItems != null
					&& clro.cartItems.size() > 0) {
				Intent prodActivity = new Intent(ProductDetailActivity.this,
						CartItemListActivity.class);
				prodActivity.putExtra("cartItemlist", clro);

				startActivity(prodActivity);
			} else {
				UIUtilities.showNoResultfound(this);
			}
		} else if (this.msgType == ResponseMesssagType.ShowCartMessageType) {

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

	}

	public void requstForAddToCart() {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ADD_ITEM), this, this);
		ProductResponseObject pro = mProductDetailObject.productDetail.get(0);
		productRequestTask.AddParam(Constants.PARAM_ADD_PRODUCT_ID,
				pro.productId);
		productRequestTask.AddParam(Constants.PARAM_QUANTITY, "1");
		productRequestTask.AddParam(Constants.PARAM_CLEAR_SEARCH, "N");
		productRequestTask.execute();

	}

	public void requestForShowCart() {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_SHOW_CART), this, this);
		productRequestTask.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buyNow:
			MCommerceApp mApp = (MCommerceApp) getApplication();
			if (mApp.getLoggedInUser() == null) {
				Intent accountActivity = new Intent(ProductDetailActivity.this,
						AccountActivity.class);
				accountActivity.putExtra("isFromHome", true);
				startActivityForResult(accountActivity,
						Constants.SUCCESSFULLY_LOGIN_REQUESTED_CODE);

			} else {
				requstForAddToCart();
			}
			break;
		case R.id.bShareBtn:

			launchStateAlertDialog();

			break;
		case R.id.bCancelSharing:
			mAlert.dismiss();
			break;

		case R.id.bPost:
			if (bFBShare.isChecked()) {
				publishStory();
			} else if (bGmailShare.isChecked()) {
				shareInGooglePLus();
			} else {
				UIUtilities.showErrorWithOkButton(this,
						"Please Check facebook/ google Button to Share");
			}

			break;
		case R.id.bFBShare:

			break;
		case R.id.bGmailShare:
			break;
		case R.id.bShareAll:
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
			i.putExtra(Intent.EXTRA_TEXT, prodUrl);
			startActivity(Intent.createChooser(i, "Share URL"));

			break;
		case R.id.imgLogo:
			setHomeScreenOnTop();
			break;
		default:
			break;
		}
	}

	void launchStateAlertDialog() {
		ProductResponseObject pro = mProductDetailObject.productDetail.get(0);
		LayoutInflater factory = LayoutInflater.from(this);
		final View mainView = factory.inflate(R.layout.share_screen, null);
		mainView.setBackgroundColor(getResources()
				.getColor(R.color.transparent));
		bCancelSharing = (Button) mainView.findViewById(R.id.bCancelSharing);
		bPost = (Button) mainView.findViewById(R.id.bPost);
		iprofileImgView = (app.nichepro.util.RoundedImageView) mainView
				.findViewById(R.id.profileImgView);
		productImage = (ImageView) mainView.findViewById(R.id.prodImage);
		productImage.setImageBitmap(mImageList.get(0).prodImage);
		titleName = (TextView) mainView.findViewById(R.id.mobileNameTitle);
		etPost = (EditText) mainView.findViewById(R.id.etPost);
		bFBShare = (ToggleButton) mainView.findViewById(R.id.bFBShare);
		bGmailShare = (ToggleButton) mainView.findViewById(R.id.bGmailShare);
		bShareAll = (LinearLayout) mainView.findViewById(R.id.bShareAll);

		mAlert = new AlertDialog.Builder(this).create();

		mAlert.setCancelable(true);
		mAlert.setView(mainView, 0, 0, 0, 0);

		bCancelSharing.setOnClickListener(this);
		bPost.setOnClickListener(this);
		bFBShare.setOnClickListener(this);
		bGmailShare.setOnClickListener(this);
		bShareAll.setOnClickListener(this);
		titleName.setText(pro.productName);
		// productImage.setImage
		bShareAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
				i.putExtra(Intent.EXTRA_TEXT, prodUrl);
				startActivity(Intent.createChooser(i, "Share URL"));
			}
		});

		DownloadfbImage();
		Session session = Session.getActiveSession();
		if (session != null) {
			Log.i(TAG, "Access Token" + session.getAccessToken());
			Request request = Request.newMeRequest(session,
					new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							if (user != null) {
								Log.i("userid=", user.getId());
								SharedPreferences.Editor editor = sharedPrefs
										.edit();
								editor.putString(Constants.FACEBOOK_USER_ID,
										user.getId());
								editor.commit();
								DownloadfbImage();
							}
							if (response.getError() != null) {

								Log.i("error", response.getError().toString());
							}
						}
					});
			request.executeAsync();
		} else {
			// login here
			Log.i("info", "dhello");
		}

		mAlert.show();

	}

	private void DownloadfbImage() {
		String imageId = sharedPrefs.getString(Constants.FACEBOOK_USER_ID, "");
		if (imageId == null || imageId.isEmpty()) {
			return;
		}
		String img_value = null;

		img_value = "http://graph.facebook.com/" + imageId
				+ "/picture?type=small";
		ImageLoader.start(img_value, 0, ihandler);

	}

	private void postToWall() {

	}

	private void publishStory() {

		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(
					ProductDetailActivity.this).setCallback(statusCallback));

			// .setPermissions(arg0)

		} else {
			// Session.openActiveSession(this,
			// ProductDetailActivity.this, true, statusCallback);

			// Session session = Session.getActiveSession();

			if (session != null) {

				// Check for publish permissions
				List<String> permissions = session.getPermissions();
				if (!isSubsetOf(PERMISSIONS, permissions)) {
					pendingPublishReauthorization = true;
					Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
							this, PERMISSIONS);
					session.requestNewPublishPermissions(newPermissionsRequest);
					return;
				}

				ProductResponseObject pro = mProductDetailObject.productDetail
						.get(0);
				String post = "Awesome, can't wait to get one of these "
						+ etPost.getText().toString();
				Bundle postParams = new Bundle();
				postParams.putString("message", post);
				postParams.putString("name", "Univercell for Android - "
						+ pro.productName);
				postParams.putString("caption", "Buy Mobiles And Accesories");
				postParams
						.putString("description",
								"Univercell Helps You Find Best Mobile: Search Compare & Buy");
				postParams.putString("link", pro.productUrl);
				postParams.putString("picture", pro.image);

				Request.Callback callback = new Request.Callback() {
					public void onCompleted(Response response) {
						if (bGmailShare.isChecked()) {
							shareInGooglePLus();
						}
						try {
							JSONObject graphResponse = response
									.getGraphObject().getInnerJSONObject();
							String postId = null;
							try {
								postId = graphResponse.getString("id");
							} catch (JSONException e) {
								Log.i("ProductDetail",
										"JSON error " + e.getMessage());
							}
							FacebookRequestError error = response.getError();
							if (error != null) {
								Toast.makeText(getApplicationContext(),
										error.getErrorMessage(),
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(),
										"You Have Shared a Post on Facebook",
										Toast.LENGTH_LONG).show();
								mAlert.dismiss();
							}
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),
									"crashed in fb sharing", Toast.LENGTH_SHORT)
									.show();

						}
					}
				};

				Request request = new Request(session, "me/feed", postParams,
						HttpMethod.POST, callback);

				RequestAsyncTask task = new RequestAsyncTask(request);
				task.execute();
			} else {
				Log.i("session is not valide", "done");
			}
		}

	}

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}
}
