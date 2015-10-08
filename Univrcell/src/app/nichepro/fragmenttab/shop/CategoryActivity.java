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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.fragmenttab.home.CategoryListAdapter;
import app.nichepro.model.BaseResponseObject;
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

public class CategoryActivity extends BaseDefaultActivity implements
		ResponseParserListener, OnEditorActionListener {

	ArrayList<String> mSpecialOfferData;
	CategoryListResponseObject mCatResponseList;
	private WebRequestTask productRequestTask;
	private ResponseMesssagType msgType;
	private String mTabName;
	private String mTitle;
	private int mSelectedIndex;
	private String mCatalogId;
	EditText eSearch;
	private String[] filterCode;
	public Spinner sFilterList;
	public String mFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		eSearch = (AutoCompleteTextView) findViewById(R.id.etSearch);
		eSearch.setOnEditorActionListener(this);
		sFilterList = (Spinner) findViewById(R.id.sFilter);

		TextView txtView = (TextView) findViewById(R.id.heading1);
		txtView.setText(mTitle);
		ListView list = (ListView) findViewById(R.id.lvInfoItems);
		ArrayAdapter<CategorysResponseObject> listAdapter = new CategoryListAdapter(
				this, null, R.layout.custom_list_cell);

		for (CategorysResponseObject element : mCatResponseList.response) {
			listAdapter.add(element);

		}
		list.setAdapter(listAdapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				CategorysResponseObject cro = mCatResponseList.response
						.get(position);
				mSelectedIndex = position;
				requestForProductList(cro.productCategoryId);
			}
		});
	}
	
	public void requestForProductList(String productCatID) {
		productRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.GET_ALL_PRODUCT_FROM_CATALOG), this,
				this);

		productRequestTask.AddParam(Constants.PARAM_CURRENT_CATALOG_ID,
				mCatalogId);
		productRequestTask.AddParam(Constants.PARAM_PRODUCT_CAT_ID,
				productCatID);
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
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		if (this.msgType == ResponseMesssagType.KeywordSearchMessageType) {
			ProductListResponseObject clro = (ProductListResponseObject) items
					.get(0);
			if (clro != null
					&& ((clro.exactSearchResults != null && clro.exactSearchResults
							.size() > 0) || (clro.productsList != null && clro.productsList
							.size() > 0))) {
				Intent prodActivity = new Intent(CategoryActivity.this,
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
				CategorysResponseObject cro = mCatResponseList.response
						.get(mSelectedIndex);

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
}
