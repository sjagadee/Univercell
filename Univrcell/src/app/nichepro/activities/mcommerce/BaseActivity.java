/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import android.content.Intent;
import android.view.KeyEvent;
import android.widget.TextView;

public interface BaseActivity {

	public int getWindowFeatures();

	public void setShowProgressTitle(boolean show);

	public void setProgressDialogTitleId(int progressDialogTitleId);

	public void setProgressDialogMsgId(int progressDialogMsgId);

	public boolean handleBackPressed();

	/**
	 * @return true, if the activity is recovering from in interruption (i.e.
	 *         onRestoreInstanceState was called.
	 */
	public boolean isRestoring();

	/**
	 * @return true, if the activity is "soft-resuming", i.e. onResume has been
	 *         called without a prior call to onCreate
	 */
	public boolean isResuming();

	/**
	 * @return true, if the activity is launching, i.e. is going through
	 *         onCreate but is not restoring.
	 */
	public boolean isLaunching();

	/**
	 * Android doesn't distinguish between your Activity being paused by another
	 * Activity of your own application, or by an Activity of an entirely
	 * different application. This function only returns true, if your Activity
	 * is being paused by an Activity of another app, thus hiding yours.
	 * 
	 * @return true, if the Activity is being paused because an Activity of
	 *         another application received focus.
	 */
	// public boolean isApplicationBroughtToBackground();

	/**
	 * Retrieves the current intent that was used to create or resume this
	 * activity. If the activity received a call to onNewIntent (e.g. because it
	 * was launched in singleTop mode), then the Intent passed to that method is
	 * returned. Otherwise the returned Intent is the intent returned by
	 * getIntent (which is the Intent which was used to initially launch this
	 * activity).
	 * 
	 * @return the current {@link Intent}
	 */
	public Intent getCurrentIntent();

	public boolean isLandscapeMode();

	public boolean isPortraitMode();

	public void moveTo(Intent intent);

	public int getClickableResource();

	boolean onEditorAction(TextView v, int actionId, KeyEvent event);

}
