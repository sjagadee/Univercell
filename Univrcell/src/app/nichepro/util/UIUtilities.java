/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ViewStub;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.R;

/**
 * The Class UIUtilities. helper method to be used in user interface
 */
public class UIUtilities {

	/**
	 * Checks if is int number.
	 * 
	 * @param num
	 *            the num
	 * @return true, if is int number
	 */
	public static boolean isIntNumber(String num) {
		if (num == null)
			return false;

		for (int i = 0; i < num.length(); i++) {
			// If we find a non-digit character we return false.
			if (!Character.isDigit(num.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Instantiates a new UI utilities.
	 */
	private UIUtilities() {
	}

	/**
	 * Determine connectivity. a utility method to determine internet
	 * connectivity this is invokedbefore every web request
	 * 
	 * @param ctx
	 *            the ctx
	 * @return true, if successful
	 */
	public static boolean determineConnectivity(Context ctx) {
		ConnectivityManager manager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return info != null && info.getState() == NetworkInfo.State.CONNECTED;
	}

	/**
	 * Inflate view. a utility method to inflate a subview in a given stub in a
	 * view
	 * 
	 * @param view
	 *            the view
	 * @param toInfate
	 *            the to infate
	 * @param inflateIn
	 *            the inflate in
	 */
	public static void inflateView(Context view, int toInfate, int inflateIn) {
		ViewStub tempStub = null;

		tempStub = (ViewStub) ((Activity) view).findViewById(inflateIn);
		if (tempStub != null) {
			if (toInfate != 0) {
				tempStub.setLayoutResource(toInfate);
				tempStub.inflate();
			} else {
				tempStub.setLayoutResource(R.layout.empty);
				tempStub.inflate();
			}
		}
	}

	/**
	 * Raise email intent. a utility method to raise email intent
	 * 
	 * @param ctx
	 *            the ctx
	 * @param address
	 *            the address
	 * @param subject
	 *            the subject
	 * @param bodyText
	 *            the body text
	 * @param title
	 *            the title
	 */
	public static void raiseEmailIntent(Context ctx, String address,
			String subject, String bodyText, String title) {

		final Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822");
		if (address != null)
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { address });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, bodyText);
		ctx.startActivity(Intent.createChooser(emailIntent, title));
	}

	/**
	 * Sets the heading. a utility method to set heading in a screen
	 * 
	 * @param ctx
	 *            the ctx
	 * @param heading1
	 *            the heading1
	 * @param headingId
	 *            the heading id
	 */
	public static void setHeading(Context ctx, String heading1, int headingId) {
		TextView temp = (TextView) ((Activity) ctx).findViewById(headingId);
		if (temp != null && heading1 != null)
			temp.setText(heading1);
	}

	/**
	 * Underline text. a utility method to underline the text in the view
	 * 
	 * @param telNo
	 *            the tel no
	 * @param textView
	 *            the text view
	 */
	public static void underlineText(String telNo, TextView textView) {
		SpannableString content = new SpannableString(telNo);
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		textView.setText(content);
	}

	/**
	 * Show call confirmation alert.
	 * 
	 * @param context
	 *            the context
	 * @param telNo
	 *            the tel no
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 * @param positiveBtn
	 *            the positive btn
	 * @param negetiveBtn
	 *            the negetive btn
	 */
	public static void showCallConfirmationAlert(final Context context,
			final String telNo, int title, int message, int positiveBtn,
			int negetiveBtn) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title)
				.setMessage(message)
				.setCancelable(true)
				.setNegativeButton(negetiveBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(Intent.ACTION_CALL);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.setData(Uri.parse("tel:" + "0" + telNo));
								context.startActivity(intent);
							}
						})
				.setPositiveButton(positiveBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Show confirmation alert.
	 * 
	 * @param context
	 *            the context
	 * @param title
	 *            the title
	 * @param msg
	 *            the msg
	 * @param postivebtnText
	 *            the postivebtn text
	 * @param negativebtnText
	 *            the negativebtn text
	 * @param positiveBtnClickListener
	 *            the positive btn click listener
	 * @param negativeBtnClickListener
	 *            the negative btn click listener
	 */
	public static void showConfirmationAlert(final Context context, int title,
			int msg, int postivebtnText, int negativebtnText,
			DialogInterface.OnClickListener positiveBtnClickListener,
			DialogInterface.OnClickListener negativeBtnClickListener

	) {
		AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
		searchDialog.setTitle(title);
		searchDialog.setMessage(msg);
		searchDialog.setCancelable(true);
		// set a positive/yes button and create a listener
		searchDialog.setNegativeButton(negativebtnText,
				negativeBtnClickListener);
		searchDialog
				.setPositiveButton(postivebtnText, positiveBtnClickListener);
		AlertDialog dialog = searchDialog.create();
		dialog.show();
	}
	
	public static void showConfirmationAlert(final Context context, int title,
			String msg, int postivebtnText, int negativebtnText,
			DialogInterface.OnClickListener positiveBtnClickListener,
			DialogInterface.OnClickListener negativeBtnClickListener

	) {
		AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
		searchDialog.setTitle(title);
		searchDialog.setMessage(msg);
		searchDialog.setCancelable(true);
		// set a positive/yes button and create a listener
		searchDialog.setNegativeButton(negativebtnText,
				negativeBtnClickListener);
		searchDialog
				.setPositiveButton(postivebtnText, positiveBtnClickListener);
		AlertDialog dialog = searchDialog.create();
		dialog.show();
	}

	/**
	 * Show confirmation alert.
	 * 
	 * @param context
	 *            the context
	 * @param title
	 *            the title
	 * @param msg
	 *            the msg
	 * @param btnText
	 *            the btn text
	 * @param okBtnClickListener
	 *            the ok btn click listener
	 * @param onDismissClickListener
	 *            the on dismiss click listener
	 */
	public static void showConfirmationAlert(final Context context, int title,
			int msg, int btnText,
			DialogInterface.OnClickListener okBtnClickListener,
			DialogInterface.OnDismissListener onDismissClickListener) {
		AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
		searchDialog.setTitle(title);
		searchDialog.setMessage(msg);
		searchDialog.setCancelable(true);
		searchDialog.setPositiveButton(btnText, okBtnClickListener);
		AlertDialog dialog = searchDialog.create();
		dialog.setOnDismissListener(onDismissClickListener);
		dialog.show();
	}

	/**
	 * Show confirmation alert.
	 * 
	 * @param context
	 *            the context
	 * @param title
	 *            the title
	 * @param msg
	 *            the msg
	 * @param btnText
	 *            the btn text
	 * @param okBtnClickListener
	 *            the ok btn click listener
	 */
	public static void showConfirmationAlert(final Context context, int title,
			int msg, int btnText,
			DialogInterface.OnClickListener okBtnClickListener) {
		AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
		searchDialog.setTitle(title);
		searchDialog.setMessage(msg);
		searchDialog.setCancelable(true);
		searchDialog.setPositiveButton(btnText, okBtnClickListener);
		AlertDialog dialog = searchDialog.create();
		dialog.show();
	}

	/**
	 * Show confirmation alert.
	 * 
	 * @param context
	 *            the context
	 * @param title
	 *            the title
	 * @param msg
	 *            the msg
	 */
	public static void showConfirmationAlert(final Context context, int title,
			int msg) {
		AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
		searchDialog.setTitle(title);
		searchDialog.setMessage(msg);
		searchDialog.setCancelable(true);
		AlertDialog dialog = searchDialog.create();
		dialog.show();
	}

	public static void showServerError(final Context context) {
		UIUtilities.showConfirmationAlert(context, R.string.dialog_title_error,
				R.string.server_error, R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	public static void showNoResultfound(final Context context) {
		UIUtilities.showConfirmationAlert(context,
				R.string.dialog_title_information, R.string.no_result_found,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	public static void showErrorWithOkButton(final Context context, String msg) {
		UIUtilities.showConfirmationAlert(context, R.string.dialog_title_error,
				msg, R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	public static void showInformationWithOkButton(final Context context,
			String msg) {
		UIUtilities.showConfirmationAlert(context, R.string.dialog_title_information,
				msg, R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	/**
	 * Show confirmation alert.
	 * 
	 * @param context
	 *            the context
	 * @param title
	 *            the title
	 * @param msg
	 *            the msg
	 * @param btnText
	 *            the btn text
	 * @param okBtnClickListener
	 *            the ok btn click listener
	 */
	public static void showConfirmationAlert(final Context context, int title,
			String msg, int btnText,
			DialogInterface.OnClickListener okBtnClickListener) {
		AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
		searchDialog.setTitle(title);
		searchDialog.setMessage(msg);
		searchDialog.setCancelable(true);
		searchDialog.setPositiveButton(btnText, okBtnClickListener);
		AlertDialog dialog = searchDialog.create();
		dialog.show();
	}

	/**
	 * Show confirmation alert.
	 * 
	 * @param context
	 *            the context
	 * @param title
	 *            the title
	 * @param msg
	 *            the msg
	 */
	public static void showConfirmationAlert(final Context context, int title,
			String msg) {
		AlertDialog.Builder searchDialog = new AlertDialog.Builder(context);
		searchDialog.setTitle(title);
		searchDialog.setMessage(msg);
		searchDialog.setCancelable(true);
		AlertDialog dialog = searchDialog.create();
		dialog.show();
	}

	/**
	 * Checks if is phone available.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return true, if is phone available
	 */
	public static boolean isPhoneAvailable(Context ctx) {
		TelephonyManager tManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		int i = tManager.getPhoneType();
		Log.i("Phone", i + "");
		return !(tManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE);
	}

	/**
	 * Scroll list view to top. Method uses refelction to find if a method exist
	 * in the newer api of android and then uses it if exists, otherwise does
	 * nothing
	 * 
	 * @param listView
	 *            the list view
	 */
	public static void scrollListViewToTop(ListView listView) {
		try {
			Class<?> classToInvestigate = Class
					.forName("android.widget.ListView");

			Class<?>[] parTypes = new Class[1];
			parTypes[0] = Integer.TYPE;

			// Get reference of add method
			Method mt = classToInvestigate.getMethod("smoothScrollToPosition",
					parTypes);

			mt.invoke(listView, 0);

		} catch (ClassNotFoundException e) {
			// Class not found!
		} catch (Exception e) {
			// Handle unknown exception!
		}
	}

	/**
	 * Hide always Soft Keyboard
	 * 
	 * @param context
	 *            is current Activity
	 */
	public static void hideKeyboard(Context context, EditText editText) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (editText != null) {
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			editText.clearFocus();
		}
	}

	public static void hideKeyboard(Activity activity) {
		Window window = activity.getWindow();
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
	}

	/**
	 * Show always Soft Keyboard
	 * 
	 * @param context
	 *            is current Activity
	 */
	public static void showKeyboard(Context context, EditText editText) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (editText != null) {
			imm.showSoftInput(editText, 0);
		}
	}

	public static String getUDID(Context context) {
		String udid = "emulator";
		if (Secure.getString(context.getContentResolver(), Secure.ANDROID_ID) != null)
			udid = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);

		return udid;
	}

	/**
	 * Get version for current application
	 * 
	 * @param context
	 *            is current Activity
	 * @param cls
	 *            is class
	 * @return string version
	 */
	public static String getVersionName(Context context, Class<?> cls) {
		try {
			ComponentName componentName = new ComponentName(context, cls);
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(componentName.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			return Constants.Default_app_version;
		}
	}

	/**
	 * Display string.
	 * 
	 * @param inStr
	 *            the in str
	 * @return the string
	 */
	public static final String displayString(String inStr) {
		inStr = inStr.replace("&amp;amp", "&");
		inStr = inStr.replace("&amp;", "&");
		inStr = inStr.replace("&trade;", "(TM)");
		inStr = inStr.replace("&lt;", "<");
		inStr = inStr.replace("&gt;", ">");
		inStr = inStr.replace("&reg;", "¨");
		inStr = inStr.replace("&#039;", "'");

		return inStr;
	}

	public static String getSeed(Context context) {
		SharedPreferences shredPref = context.getSharedPreferences(
				Constants.SHAREDPREF_seed, 0);
		boolean isSeedSet = shredPref.getBoolean(
				Constants.SHAREDPREF_isSeedSet, false);

		if (!isSeedSet) {
			shredPref = context.getSharedPreferences(Constants.SHAREDPREF_seed,
					0);
			SharedPreferences.Editor editor = shredPref.edit();
			editor.clear();
			editor.putBoolean(Constants.SHAREDPREF_isSeedSet, true);
			editor.putString(Constants.TAG_seed,
					getRandomSeedForDevice(context));
			editor.commit();
		}

		return shredPref.getString(Constants.TAG_seed, Constants.Empty_String);
	}

	private static String getRandomSeedForDevice(Context context) {
		StringBuilder sb = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		sb.append(UUID.randomUUID().toString());
		sb.append(dateFormat.format(date));
		return sb.toString();
	}

	/*
	 * *
	 * method is used for checking valid email id format.
	 * 
	 * @param email
	 * 
	 * @return boolean true for valid false for invalid
	 */
	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/*
	 * Creates a UUID specific to the device. There are possibly some instances
	 * where this does not work e.g. in the emulator or if there is no SIM in
	 * the phone.
	 */
	public static void setDeviceUUID(Context context) {
		// final TelephonyManager tm = (TelephonyManager)
		// context.getSystemService(Context.TELEPHONY_SERVICE);
		//
		// final String tmDevice, tmSerial, androidId;
		// tmDevice = "" + tm.getDeviceId();
		// tmSerial = "" + tm.getSimSerialNumber();
		// androidId = "" + Secure.getString(context.getContentResolver(),
		// Secure.ANDROID_ID);
		//
		// HealthCareApp mAppIns = (HealthCareApp)context.
		// deviceMobileNo = tm.getLine1Number();
		//
		// UUID deviceUuid = new UUID(androidId.hashCode(),
		// ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		// deviceUUID = deviceUuid.toString();
	}

	public static Context getContextIfChildActivity(Context context) {
		return context;
	}

	public static Typeface createRupeeSymbol(Context context) {

		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/Rupee_Foradian.ttf");

		return tf;
	}

	/**
	 * Get version for current application
	 * 
	 * @param context
	 *            is current Activity
	 * @return string version
	 */
	public static String getVersionName(Context context) {
		try {
			ComponentName componentName = new ComponentName(context,
					context.getClass());
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(componentName.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			return Constants.Default_app_version;
		}
	}

	public static int ScoreProcent(int questions, int correct) {
		return (int)Math.round((questions * 100.00) / correct);
	}
}