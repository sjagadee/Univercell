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
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.HomeTabListAdapter;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.CatalogCategoryListResponseObject;
import app.nichepro.model.CatalogCategoryResponseObject;
import app.nichepro.model.CatalogListResponseObject;
import app.nichepro.model.CatalogResponseObject;
import app.nichepro.model.CategoryListResponseObject;
import app.nichepro.model.CategorysResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.ProductListResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class ShopActivity extends BaseDefaultActivity implements
		ResponseParserListener, OnEditorActionListener, OnItemSelectedListener {
	WebRequestTask productRequestTask;
	private ResponseMesssagType msgType;
	ArrayAdapter<CatalogResponseObject> mListAdapter;
	ExpandableListView mList;
	CatalogListResponseObject mCatResponseList;
	int mSelectedIndex;
	EditText eSearch;
	private String[] filterCode;
	public Spinner sFilterList;
	public String mFilter;
	private CatalogCategoryListResponseObject mCCListData;
	private String mSelectedCategoryName;
	public MCommerceApp mApp;
	Button btnGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_screen1);
		mApp = (MCommerceApp) getApplication();
		Intent intent = getIntent();
		mCCListData = intent.getParcelableExtra("cataloglist");
		btnGo = (Button)findViewById(R.id.bGo);
		eSearch = (AutoCompleteTextView) findViewById(R.id.etSearch);
		eSearch.setOnEditorActionListener(this);
		sFilterList = (Spinner) findViewById(R.id.sFilter);
		sFilterList.setOnItemSelectedListener(this);
		sFilterList.setSelection(mApp.iSelectedSearch);
		mList = (ExpandableListView) findViewById(R.id.catalogList);
		mListAdapter = new CatalogListAdapter(this, null,
				R.layout.custom_list_cell);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setHomeScreenOnTop();
			}
		});
		
		btnGo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestForProductSearchList(eSearch.getText().toString());
			}
		});

		// populateDataInListView();
		HomeTabListAdapter listAdapter = new HomeTabListAdapter(this,
				mCCListData);

		mList.setAdapter(listAdapter);
		mList.setOnGroupClickListener(myListGroupClicked);
		mList.setOnChildClickListener(myListChildClicked);

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

	public void requestForCategory(String category) {

		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_CATEGORY_DETAIL), this, this);

		productRequestTask.AddParam(Constants.PARAM_CURRENT_CATALOG_ID,
				category);
		productRequestTask.execute();
	}

	public void requestForCatalog() {

		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_CATALOG_DETAIL), this, this);

		productRequestTask.execute();
	}

	public void populateDataInListView() {

		for (CatalogResponseObject element : mCatResponseList.response) {
			mListAdapter.add(element);

		}
		mList.setAdapter(mListAdapter);

		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				CatalogResponseObject cro = mCatResponseList.response
						.get(position);
				mSelectedIndex = position;
				requestForCategory(cro.prodCatalogId);
			}
		});
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
				Intent prodActivity = new Intent(ShopActivity.this,
						ProductListActivity.class);
				clro.mTitle = eSearch.getText().toString();
				prodActivity.putExtra("productlist", clro);
				startActivity(prodActivity);
			} else {
				UIUtilities.showNoResultfound(this);
			}
		} else if (this.msgType == ResponseMesssagType.ProductListMessageType) {

			ProductListResponseObject clro = (ProductListResponseObject) items
					.get(0);
			if (clro != null && clro.productsList != null
					&& clro.productsList.size() > 0) {
				Intent prodActivity = new Intent(ShopActivity.this,
						ProductListActivity.class);
				clro.mTitle = mSelectedCategoryName;
				prodActivity.putExtra("productlist", clro);
				startActivity(prodActivity);
			} else {
				UIUtilities.showNoResultfound(this);
			}

		} else if (this.msgType == ResponseMesssagType.CategoryMessageType) {
			CategoryListResponseObject clro = (CategoryListResponseObject) items
					.get(0);
			if (clro != null && clro.response != null
					&& clro.response.size() > 0) {

			} else {
				// No cate found
			}
		} else if (this.msgType == ResponseMesssagType.CatalogMessageType) {
			CatalogListResponseObject clro = (CatalogListResponseObject) items
					.get(0);
			if (clro != null && clro.response != null
					&& clro.response.size() > 0) {
				this.mCatResponseList = clro;
				populateDataInListView();
				mListAdapter.notifyDataSetChanged();
			} else {
				// No cate found
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

	public ResponseMesssagType getMsgType() {
		return msgType;
	}

	public void setMsgType(ResponseMesssagType msgType) {
		this.msgType = msgType;
	}

	public void setFilterCode() {
		this.filterCode = getResources().getStringArray(
				R.array.filter_value_array);
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

	// our group listener
	private OnGroupClickListener myListGroupClicked = new OnGroupClickListener() {

		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {

			// get the group header

			CatalogCategoryResponseObject ccro = mCCListData.catalogCategoryList
					.get(groupPosition);
			ImageView expandable = (ImageView) v.findViewById(R.id.expandable);
			ImageView collapsable = (ImageView) v
					.findViewById(R.id.collapsable);
			if (ccro.isExpandable()) {
				expandable.setVisibility(View.VISIBLE);
				collapsable.setVisibility(View.GONE);
				ccro.setExpandable(false);
			} else {
				expandable.setVisibility(View.GONE);
				collapsable.setVisibility(View.VISIBLE);
				ccro.setExpandable(true);
			}
			return false;
		}

	};

	private OnChildClickListener myListChildClicked = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			CatalogCategoryResponseObject ccro = mCCListData.catalogCategoryList
					.get(groupPosition);
			CategorysResponseObject cro = ccro.categoryList.get(childPosition);
			mSelectedCategoryName = cro.categoryName;

			requestForProductList(ccro.catalogId, cro.productCategoryId);
			return false;
		}
	};

	public void requestForProductList(String catalogID, String productCatID) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_PRODUCT_FROM_CATALOG), this,
				this);

		productRequestTask.AddParam(Constants.PARAM_CURRENT_CATALOG_ID,
				catalogID);
		productRequestTask.AddParam(Constants.PARAM_PRODUCT_CAT_ID,
				productCatID);
		productRequestTask.execute();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
