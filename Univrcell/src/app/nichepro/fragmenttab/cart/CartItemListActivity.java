/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.cart;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.CartItemListResponseObject;
import app.nichepro.model.CartItemResponseObject;
import app.nichepro.model.DiscountResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.ImageLoader;
import app.nichepro.util.ImageLoaderHandler;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class CartItemListActivity extends BaseDefaultActivity implements
		ResponseParserListener, UpdateUiFromAdapterListener,
		OnEditorActionListener {

	CartItemListResponseObject mCartItemList;
	private WebRequestTask productRequestTask;
	private ResponseMesssagType msgType;
	private LinearLayout mlistScrollView;
	int mSelectedIndex;
	Button btnContinue;
	Button btnCheckout;
	// TextView tvQty;
	TextView tvGrandPrice;
	TextView discount;
	TextView symbol, ruSymbol;
	EditText coupon;
	Button btnApply;
	LinearLayout discountLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ImageLoader.initialize(this);

		setContentView(R.layout.cart_list);
		Intent intent = getIntent();
		if (savedInstanceState == null) {
			mCartItemList = intent.getParcelableExtra("cartItemlist");
		} else
			mCartItemList = savedInstanceState.getParcelable("cartItemlist");

		mlistScrollView = (LinearLayout) findViewById(R.id.lvInfoItems);
		btnContinue = (Button) findViewById(R.id.btnContinue);
		// tvQty = (TextView) findViewById(R.id.qty);
		tvGrandPrice = (TextView) findViewById(R.id.gTotal);
		btnCheckout = (Button) findViewById(R.id.btnCheckout);
		symbol = (TextView) findViewById(R.id.symbolRupee);
		btnApply = ((Button) findViewById(R.id.btnApply));
		coupon = ((EditText) findViewById(R.id.coupon));
		ruSymbol = (TextView) findViewById(R.id.symbol_rup);
		discountLayout = (LinearLayout) findViewById(R.id.discountLayout);
		discount = (TextView) findViewById(R.id.discount);

		btnCheckout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (CartItemListActivity.this.mCartItemList != null) {
					try {
						Intent cartActivity = new Intent(
								CartItemListActivity.this, CartActivity.class);
						cartActivity.putExtra("cartItemList", mCartItemList);

						startActivityForResult(cartActivity,
								Constants.CART_REFRESH);
					} catch (Exception e) {
						Log.i("crash", e.toString());
					}
				} else {
					finish();
				}

			}
		});
		btnContinue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MCommerceApp mApp = (MCommerceApp) getApplication();
				if (mApp.isContinueShopping() == true) {
					mApp.setContinueShopping(false);
				} else
					mApp.setContinueShopping(true);
				finish();
			}
		});

		btnApply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (CartItemListActivity.this.mCartItemList != null) {
					View parent = (View) v.getParent();
					EditText couponCode = (EditText) parent
							.findViewById(R.id.coupon);
					String str2 = couponCode.getText().toString();
					if (str2 != null && str2.length() > 0) {
						requestForApplyPromo(str2);
					} else {
						showError("Enter Coupon code");
					}
				} else {
					finish();
				}
			}
		});
		if (savedInstanceState == null) {
			populateView();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		requestForShowCart();
		if (resultCode == Constants.CART_REFRESH && data != null) {

		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("cartItemlist", mCartItemList);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		populateView();

	}

	private ImageLoaderHandler ihandler = new ImageLoaderHandler() {
		@Override
		protected boolean handleImageLoaded(Bitmap bitmap, Message msg,
				int rowIndex, int col, String imageUrl, boolean noImageFound) {
			if (mCartItemList.cartItems.size() > rowIndex) {
				CartItemResponseObject pliro = mCartItemList.cartItems
						.get(rowIndex);

				if (pliro != null && pliro.image.compareTo(imageUrl) == 0) {
					pliro.prodImg = bitmap;
					populateView();
				}

			}
			return false;
		}
	};

	public void populateView() {

		if (mCartItemList != null) {
			if (mlistScrollView != null
					&& ((LinearLayout) mlistScrollView).getChildCount() > 0) {
				((LinearLayout) mlistScrollView).removeAllViews();
			}

			LayoutInflater factory = LayoutInflater.from(this);

			for (int j = 0; j < mCartItemList.cartItems.size(); j++) {
				LinearLayout cellLayout = (LinearLayout) factory.inflate(
						R.layout.cart_item_cell, null);
				CartItemResponseObject pro = mCartItemList.cartItems.get(j);
				ProgressBar pBar;
				ImageView imgView, logoImg;
				TextView ProductName;
				TextView price;
				TextView adjustments;
				TextView itemTotal;
				TextView symbol1, symbol2, symbol3;
				EditText qty;
				Button btnUpdate;
				TextView no_insurance;
				LinearLayout insurancelayout;
				final CheckBox btnChkBox;
				final TextView insuaranceTxt;
				Button btnRemove;
				RelativeLayout layoutRemove;
				insurancelayout = (LinearLayout) cellLayout
						.findViewById(R.id.insurancelayout);

				pBar = (ProgressBar) cellLayout.findViewById(R.id.progress_bar);
				imgView = (ImageView) cellLayout.findViewById(R.id.img);
				logoImg = (ImageView) cellLayout.findViewById(R.id.imgLogo);
				imgLogo = (ImageView) cellLayout.findViewById(R.id.imgLogo);
				imgLogo.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						setHomeScreenOnTop();

					}
				});
				ProductName = ((TextView) cellLayout
						.findViewById(R.id.tvProductName));
				symbol1 = ((TextView) cellLayout.findViewById(R.id.symbol1));
				price = ((TextView) cellLayout.findViewById(R.id.tvPrice));
				symbol2 = ((TextView) cellLayout.findViewById(R.id.symbol2));
				adjustments = ((TextView) cellLayout
						.findViewById(R.id.adjustments));
				symbol3 = ((TextView) cellLayout.findViewById(R.id.symbol3));
				itemTotal = ((TextView) cellLayout.findViewById(R.id.itemTotal));
				qty = ((EditText) cellLayout.findViewById(R.id.qty));

				btnUpdate = ((Button) cellLayout.findViewById(R.id.btnUpdate));
				no_insurance = (TextView) cellLayout
						.findViewById(R.id.tvInsuranceTitle);
				layoutRemove = ((RelativeLayout) cellLayout
						.findViewById(R.id.layoutRemove));
				btnRemove = ((Button) cellLayout.findViewById(R.id.btnRemove));
				btnChkBox = ((CheckBox) cellLayout
						.findViewById(R.id.checkbox_insurance));
				insuaranceTxt = ((TextView) cellLayout
						.findViewById(R.id.tv_insurance));
				if (pro.allowPreBooking != null
						&& pro.allowPreBooking.length() > 0
						&& pro.allowPreBooking.compareToIgnoreCase("Y") == 0) {
					insurancelayout.setVisibility(View.GONE);
					btnChkBox.setVisibility(View.GONE);
					insuaranceTxt.setVisibility(View.GONE);
				}

				if (pro.isAccessoryItem != null
						&& pro.isAccessoryItem.compareToIgnoreCase("Y") == 0) {
					insurancelayout.setVisibility(View.GONE);
					btnChkBox.setVisibility(View.GONE);
					insuaranceTxt.setVisibility(View.GONE);
					no_insurance.setVisibility(View.VISIBLE);
				} else {
					insurancelayout.setVisibility(View.VISIBLE);
					btnChkBox.setVisibility(View.VISIBLE);
					insuaranceTxt.setVisibility(View.VISIBLE);
					no_insurance.setVisibility(View.GONE);
				}

				if (j == 0) {
					logoImg.setVisibility(View.VISIBLE);
				} else {
					logoImg.setVisibility(View.GONE);
				}

				if (pro.insuranceAdded.contains("false")) {
					btnChkBox.setChecked(false);
				} else if (pro.insuranceAdded.contains("true")) {
					btnChkBox.setChecked(true);
				}

				btnChkBox.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;

						final int pos = (Integer) cb.getTag();
						CartItemResponseObject ciro = mCartItemList.cartItems
								.get(pos);
						if (ciro.insuranceAdded.contains("false")) {
							showInsurance(pos, cb);

						} else if (ciro.insuranceAdded.contains("true")) {
							requestForRemoveInsurance(pos + "");

						}

						// if (cb.isChecked()) {
						// requestForAddInsurance(pos + "");
						// insuaranceTxt.setText(R.string.remove_insurance);
						// } else {
						// insuaranceTxt.setText(R.string.add_insurance);
						// }
					}
				});
				btnUpdate.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int index = (Integer) v.getTag();
						View parent = (View) v.getParent();
						EditText q = (EditText) parent.findViewById(R.id.qty);
						String str = q.getText().toString();
						if (isNumeric(str)) {
							updateUI(q.getText().toString(), index);
						} else {
							showError("Invalid input for quantity!!!");
						}

					}
				});

				btnRemove.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int index = (Integer) v.getTag();
						updateUI(index);
					}
				});

				layoutRemove.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int index = (Integer) v.getTag();
						updateUI(index);
					}
				});

				btnUpdate.setTag(j);
				btnRemove.setTag(j);
				layoutRemove.setTag(j);
				btnChkBox.setTag(j);

				ProductName.setText(pro.productName);
				price.setText(pro.unitPrice);
				adjustments.setText(pro.adjustments);
				itemTotal.setText(pro.itemTotal);
				qty.setText(pro.quantity);
				insuaranceTxt.setText(pro.insurance);

				Typeface tf = UIUtilities.createRupeeSymbol(this);
				symbol1.setTypeface(tf);
				symbol1.setText("`");
				symbol2.setTypeface(tf);
				symbol2.setText("`");
				symbol3.setTypeface(tf);
				symbol3.setText("`");

				if (pro.prodImg == null) {
					if (pro.image != null && pro.image.length() > 0) {
						pro.image = pro.image.replaceAll(" ", "%20");
					}
					ImageLoader.start(pro.image, j, ihandler);
				} else {
					imgView.setImageBitmap(pro.prodImg);
					imgView.setVisibility(View.VISIBLE);
					pBar.setVisibility(View.GONE);
				}

				mlistScrollView.addView(cellLayout);
			}
		} else {
			if (mlistScrollView != null
					&& ((LinearLayout) mlistScrollView).getChildCount() > 0) {
				((LinearLayout) mlistScrollView).removeAllViews();
			}
		}

		if (mCartItemList != null && mCartItemList.Discount != null
				&& mCartItemList.Discount.size() > 0) {

			String discountValue = null;
			int value = 0;
			for (int i = 0; i < mCartItemList.Discount.size(); i++) {
				DiscountResponseObject dro = mCartItemList.Discount.get(i);
				if (i == 0) {
					value = Integer.parseInt(dro.amount.replace("-", ""));
				} else
					value += Integer.parseInt(dro.amount.replace("-", ""));
			}

			if (value != 0) {
				discountValue = "-" + value;
			}

			if (discountValue != null) {
				symbol.setTypeface(UIUtilities.createRupeeSymbol(this));
				symbol.setText("`");
				discountLayout.setVisibility(View.VISIBLE);
				discount.setText(discountValue);
				ruSymbol.setTypeface(UIUtilities.createRupeeSymbol(this));
				ruSymbol.setText("`");

			}

			tvGrandPrice.setText(mCartItemList.grandTotal);
		} else {
			symbol.setTypeface(UIUtilities.createRupeeSymbol(this));
			symbol.setText("`");
			discount.setText("");
			discountLayout.setVisibility(View.GONE);
			if (mCartItemList == null) {
				tvGrandPrice.setText("");
			} else {
				tvGrandPrice.setText(mCartItemList.grandTotal);
			}

		}

	}

	public void showInsurance(final int pos, final CheckBox cb) {

		String line1 = "1. In case of Settlement, the Sum Insured or the Market Value at the time of loss whichever is less shall be taken";
		String newline = "\n";
		String line2 = "2. Policy Excess will be 0.5% of Sum Insured subject to a minimum of Rs.100/-";
		String line3 = "3. In case of Total Damage due to Road Accident, the necessary documents like FIR, Original Invoice, Estimate of Loss and Final Bill to be submitted";
		String line4 = "4. For all settled claims, a Credit Note will be issued in the Name of the Insured for the Settled Value which can be redeemed in any Univercell Showroom.";
		String msg = line1 + newline + line2 + newline + line3 + newline
				+ line4;

		UIUtilities.showConfirmationAlert(this, R.string.terms_and_condition,
				msg, R.string.dialog_button_Ok,
				R.string.dialog_button_Cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						requestForAddInsurance(pos + "");
					}
				}

				, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						cb.setChecked(false);
					}
				});

	}

	public static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public void showError(String error) {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information, error,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	public void requestForProductDetail(String productCatID) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_PRODUCT_DETAIL), this, this);

		productRequestTask.AddParam(Constants.PARAM_PRODUCT_ID, productCatID);
		productRequestTask.execute();
	}

	public void requestForRemoveItemFromCart(String index) {

		CartItemResponseObject ciro = mCartItemList.cartItems.get(Integer
				.parseInt(index));
		if (ciro.isDeleted == false) {
			ciro.isDeleted = true;
			productRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.GET_MODIFIY_CART), this, this);

			productRequestTask.AddParam(Constants.PARAM_SELECTED_ITEM, index);
			productRequestTask.AddParam(Constants.PARAM_REMOVE_SELECTED,
					Constants.VALUE_TRUE);
			productRequestTask.AddParam(Constants.PARAM_ITEM_INDEX, index);
			productRequestTask.execute();
		} else {

		}
	}

	public void requestForUpdateQTY(String index, String value) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_UPDATE_CART), this, this);

		productRequestTask.AddParam(Constants.PARAM_UPDATE_CART + index, value);

		productRequestTask.AddParam(Constants.PARAM_ITEM_INDEX, index);
		productRequestTask.execute();
	}

	public void requestForApplyPromo(String promo) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ADD_PROMO_CODE), this, this);

		productRequestTask.AddParam(Constants.PARAM_PROMO_CODE_ID, promo);

		productRequestTask.execute();
	}

	public void requestForShowCart() {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_SHOW_CART), this, this);
		productRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		UIUtilities.hideKeyboard(this);

		if (this.msgType == ResponseMesssagType.ModifyCartMessageType
				|| this.msgType == ResponseMesssagType.UpdateCartMessageType
				|| this.msgType == ResponseMesssagType.AddInsuranceMessageType
				|| this.msgType == ResponseMesssagType.RemoveInsuranceMessageType
				|| this.msgType == ResponseMesssagType.ShowCartMessageType) {

			CartItemListResponseObject cilro = (CartItemListResponseObject) items
					.get(0);
			if (cilro.cartItems != null && cilro.cartItems.size() > 0) {
				this.mCartItemList = cilro;
				tvGrandPrice.setText(mCartItemList.grandTotal);
				populateView();
			} else {
				// finish();
				this.mCartItemList = null;
				populateView();
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_information,
						"Your shopping cart is empty!!!",
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
			}

		} else if (this.msgType == ResponseMesssagType.PromoCodeMessageType) {
			CartItemListResponseObject cilro = (CartItemListResponseObject) items
					.get(0);

			if (cilro._EVENT_MESSAGE_ != null) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_information,
						cilro._EVENT_MESSAGE_, R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								requestForShowCart();
							}
						});
			} else {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_information,
						"Promo Code Not Applied", R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								requestForShowCart();
							}
						});
			}

		} else if (this.msgType == ResponseMesssagType.ShowCartMessageType) {
			this.mCartItemList = (CartItemListResponseObject) items.get(0);
			populateView();
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

	public void requestForAddInsurance(String itemId) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ADD_ITEM_INSURANCE), this, this);

		productRequestTask.AddParam(Constants.PARAM_ITEM_INDEX, itemId);

		productRequestTask.execute();

	}

	public void requestForRemoveInsurance(String itemId) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.REMOVE_ITEM_INSURANCE), this, this);

		productRequestTask.AddParam(Constants.PARAM_ITEM_INDEX, itemId);

		productRequestTask.execute();

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub
		requestForUpdateQTY(position + "", message);
	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub
		requestForRemoveItemFromCart(position + "");
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			return true;
		}
		return false;
	}

}
