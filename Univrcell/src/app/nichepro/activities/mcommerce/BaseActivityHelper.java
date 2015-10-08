/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.Window;
import app.nichepro.base.MCommerceApp;

public class BaseActivityHelper {
	public static final String ERROR_DIALOG_TITLE_RESOURCE = "error_dialog_title";

	// FIXME: this method currently doesn't work
	public static int getWindowFeatures(Activity activity) {
		Window window = activity.getWindow();
		if (window == null) {
			return 0;
		}
		try {

			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Creates a new ProgressDialog
	 * 
	 * @param activity
	 * @param progressDialogTitleId
	 *            The resource id for the title. If this is less than or equal
	 *            to 0, a default title is used.
	 * @param progressDialogMsgId
	 *            The resource id for the message.
	 * @return The new dialog
	 */
	public static ProgressDialog createProgressDialog(final Context activity,
			int progressDialogTitleId, int progressDialogMsgId,
			boolean showTitle, final int dialogid) {
		ProgressDialog progressDialog = new ProgressDialog(activity);

		if (showTitle) {
			if (progressDialogTitleId <= 0) {
				progressDialogTitleId = R.string.progress_dialog_title;
			}
			progressDialog.setTitle(progressDialogTitleId);
		}

		if (progressDialogMsgId <= 0) {
			progressDialogMsgId = R.string.progress_dialog_message;
		}

		progressDialog.setMessage(activity.getResources().getString(
				progressDialogMsgId));
		progressDialog.setIndeterminate(true);
		progressDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				((Activity) activity).onKeyDown(keyCode, event);
				return false;
			}
		});
		progressDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				MCommerceApp app = (MCommerceApp) activity
						.getApplicationContext();
			//	app.cleanRunningWebRequestTask(dialogid);
			}
		});
		return progressDialog;
	}

}
