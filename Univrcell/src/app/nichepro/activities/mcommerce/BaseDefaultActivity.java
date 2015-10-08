/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import app.nichepro.base.BaseApp;
import app.nichepro.util.UIUtilities;

public class BaseDefaultActivity extends Activity implements BaseActivity {

	private boolean wasCreated, wasInterrupted;

	private int progressDialogTitleId;

	private int progressDialogMsgId;

	private Intent currentIntent;
	public String[] filterCode;
	public AutoCompleteTextView eSearch;
	public Spinner sFilterList;

	public ImageView imgLogo;
	public Boolean noHistory() {
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.wasCreated = true;
		this.currentIntent = getIntent();

		Application application = getApplication();
		if (application instanceof BaseApp) {
			((BaseApp) application).setActiveContext(getClass()
					.getCanonicalName(), this);
		}
		setFilterCode();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		wasInterrupted = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		wasCreated = wasInterrupted = false;
		UIUtilities.hideKeyboard(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.currentIntent = intent;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		return BaseActivityHelper.createProgressDialog(getDialogContext(),
				progressDialogTitleId, progressDialogMsgId, showProgressTitle,
				id);
	}

	public void setProgressDialogTitleId(int progressDialogTitleId) {
		this.progressDialogTitleId = progressDialogTitleId;
	}

	public void setProgressDialogMsgId(int progressDialogMsgId) {
		this.progressDialogMsgId = progressDialogMsgId;
	}

	public int getWindowFeatures() {
		return BaseActivityHelper.getWindowFeatures(this);
	}

	public boolean isRestoring() {
		return wasInterrupted;
	}

	public boolean isResuming() {

		return !wasCreated;
	}

	public boolean isLaunching() {
		return !wasInterrupted && wasCreated;
	}

	public Intent getCurrentIntent() {
		return currentIntent;
	}

	public boolean isLandscapeMode() {
		return getWindowManager().getDefaultDisplay().getOrientation() == 1;
	}

	public boolean isPortraitMode() {
		return !isLandscapeMode();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	private boolean showProgressTitle = false;

	public void setShowProgressTitle(boolean show) {
		this.showProgressTitle = show;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	public Context getDialogContext() {
		return getDialogContext(this);
	}

	public Context getDialogContext(Context cxt) {
		return cxt;
	}

	public void handleActivityResult(int requestCode, int resultCode,
			Intent data) {
		onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean handleBackPressed() {
		return false;
	}

	@Override
	public void moveTo(Intent intent) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getClickableResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFilterCode() {
		this.filterCode = getResources().getStringArray(
				R.array.filter_value_array);
	}

	public void setHomeScreenOnTop() {

		Intent intent = new Intent(this, HomeScreenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_menu, menu);
	    if (this.getClass().getSimpleName().compareTo("HomeScreenActivity") == 0) {
			return false;
		}
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	        case R.id.menu_home:
	        	setHomeScreenOnTop();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
