/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.shop;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.fragmenttab.home.ProductListAdapter;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.ProductDetailListResponseObject;
import app.nichepro.model.ProductListResponseObject;
import app.nichepro.model.ProductResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class ProductListActivity extends BaseDefaultActivity implements
		ResponseParserListener, UpdateUiFromAdapterListener,
		OnEditorActionListener,OnItemSelectedListener,OnClickListener {

	ProductListResponseObject mProductList;
	private WebRequestTask productRequestTask;
	private ResponseMesssagType msgType;
	private String mTitle;
	public boolean isKeyWordSearch;
	private ArrayAdapter<ProductResponseObject> mListAdapter;
	private ArrayList<ProductResponseObject> mProductArrayList;
	private ListView mlist;
	int mSelectedIndex;
	MCommerceApp mApp;
	Button btnGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApp = (MCommerceApp) getApplication();
		setContentView(R.layout.special_offers1);
		Intent intent = getIntent();
		mProductList = intent.getParcelableExtra("productlist");
		eSearch = (AutoCompleteTextView) findViewById(R.id.etSearch);
		eSearch.setOnEditorActionListener(this);
		sFilterList = (Spinner) findViewById(R.id.sFilter);
		sFilterList.setOnItemSelectedListener(this);
		sFilterList.setSelection(mApp.iSelectedSearch);
		btnGo = (Button)findViewById(R.id.bGo);
		TextView txtView = (TextView) findViewById(R.id.heading1);
		txtView.setText(mProductList.mTitle);
		mlist = (ListView) findViewById(R.id.lvInfoItems);
		btnGo.setOnClickListener(this);
		mProductArrayList = new ArrayList<ProductResponseObject>();
		populateView();
		UIUtilities.hideKeyboard(this);
		
	}

	public void populateView() {

		// if (mProductList.isKeyWordSearch == 1) {
		// if (mProductList.exactSearchResults != null
		// && mProductList.exactSearchResults.size() > 0) {
		// for (ProductResponseObject element : mProductList.exactSearchResults)
		// {
		// mProductArrayList.add(element);
		// }
		// } else {
		// for (ProductResponseObject element : mProductList.productsList) {
		// mProductArrayList.add(element);
		// }
		// }
		// } else {
		// for (ProductResponseObject element : mProductList.productsList) {
		// mProductArrayList.add(element);
		// }
		// }

		if (mProductList.exactSearchResults != null
				&& mProductList.exactSearchResults.size() > 0) {
			for (ProductResponseObject element : mProductList.exactSearchResults) {
				mProductArrayList.add(element);
			}
			mProductList.isKeyWordSearch = 1;
		} else {
			for (ProductResponseObject element : mProductList.productsList) {
				mProductArrayList.add(element);
			}
			mProductList.isKeyWordSearch = 0;
		}

		mListAdapter = new ProductListAdapter(this, null,
				R.layout.home_list_cell, mProductList, this, mProductArrayList,
				mProductList.isKeyWordSearch);
		for (ProductResponseObject element : mProductArrayList) {
			mListAdapter.add(element);
		}

		mlist.setAdapter(mListAdapter);
		mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				ProductResponseObject pro = mProductArrayList.get(position);
				requestForProductDetail(pro.productId);
			}
		});
		
		
		
	}

	public void requestForProductDetail(String productCatID) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_PRODUCT_DETAIL), this, this);

		productRequestTask.AddParam(Constants.PARAM_PRODUCT_ID, productCatID);
		productRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		UIUtilities.hideKeyboard(this, eSearch);

		if (this.msgType == ResponseMesssagType.KeywordSearchMessageType) {
			ProductListResponseObject clro = (ProductListResponseObject) items
					.get(0);
			if (clro != null
					&& ((clro.exactSearchResults != null && clro.exactSearchResults
							.size() > 0) || (clro.productsList != null && clro.productsList
							.size() > 0))) {
				int vIndex = 0;
				if (clro.viewIndex != null) {
					vIndex = Integer.parseInt(clro.viewIndex);
				}

				if (vIndex > 0) {
					mProductList = clro;
					populateView();

				} else {

					if (clro != null
							&& ((clro.exactSearchResults != null && clro.exactSearchResults
									.size() > 0) || (clro.productsList != null && clro.productsList
									.size() > 0))) {
						Intent prodActivity = new Intent(
								ProductListActivity.this,
								ProductListActivity.class);
						clro.mTitle = eSearch.getText().toString();

						prodActivity.putExtra("productlist", clro);
						prodActivity.putExtra("title", eSearch.getText()
								.toString());
						startActivity(prodActivity);
					} else {
						UIUtilities.showNoResultfound(this);
					}

				}

			} else {
				UIUtilities.showNoResultfound(this);
			}
		} else if (this.msgType == ResponseMesssagType.ProductDetailMessageType) {
			ProductDetailListResponseObject clro = (ProductDetailListResponseObject) items
					.get(0);

			if (clro != null && clro.productDetail != null
					&& clro.productDetail.size() > 0) {
				Intent prodActivity = new Intent(ProductListActivity.this,
						ProductDetailActivity.class);
				prodActivity.putExtra("productdetail", clro);
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

	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub
		int vIndex = 0;
		vIndex = Integer.parseInt(mProductList.viewIndex);
		requestForProductSearchListForNextPage(mTitle, vIndex + 1 + "");
	}

	public void requestForProductSearchListForNextPage(String searchKeyWord,
			String page) {
		UIUtilities.hideKeyboard(this, eSearch);
		int index = sFilterList.getSelectedItemPosition();
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_KEYWORD_SEARCH), this, this);

		productRequestTask.AddParam(Constants.PARAM_SEARCH_CATALOG_ID, "");
		productRequestTask.AddParam(Constants.PARAM_PAGING,
				Constants.VALUE_PAGING_Y);
		productRequestTask.AddParam(Constants.PARAM_SEARCH_STRING,
				mProductList.SEARCH_STRING);
		productRequestTask.AddParam(Constants.PARAM_SEARCH_BY,
				filterCode[index]);
		productRequestTask.AddParam(Constants.PARAM_VIEW_INDEX, page);
		productRequestTask.execute();

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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		mApp.iSelectedSearch = position;

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		requestForProductSearchList(eSearch.getText().toString());
	}
}
