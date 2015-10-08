/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import app.nichepro.base.MCommerceApp;
import app.nichepro.model.db.AlarmData;
import app.nichepro.model.db.DoctroAlarmData;
import app.nichepro.model.db.UserLoginInfo;
import app.nichepro.model.db.UserRegistrationInfo;
import app.nichepro.util.Log;
import app.nichepro.util.SimpleCrypto;
import app.nichepro.util.UIUtilities;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * The Class DbUtils. Contains utility methods to db operations
 */
public class DbUtils {

	/** The TAG. */
	private static String TAG = DbUtils.class.getSimpleName();
	private static MCommerceApp mAppInstance = null;
	/** The db helper. */
	private static DatabaseHelper dbHelper = null;

	/**
	 * Gets the helper.
	 * 
	 * @param context
	 *            the context
	 * @return the helper
	 */
	public static DatabaseHelper getHelper(Context context) {

		if (dbHelper == null)
			dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(context,
					DatabaseHelper.class);

		return dbHelper;

	}

	public static List<UserLoginInfo> getUserAccountInfo(Context ctx) {
		try {
			List<UserLoginInfo> uli = getHelper(ctx).getUserLoginInfoDao()
					.queryForAll();

			// Read the encrypted password and the write it back into the
			// returned user object
			for (UserLoginInfo user : uli) {
				try {
					user.setPassword(SimpleCrypto.decrypt(
							UIUtilities.getSeed(ctx), user.getPassword()));
				} catch (Exception e) {
					user.setPassword(user.getPassword());
				}
			}

			return uli;
		} catch (SQLException e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
		return null;
	}

	public static void addUserAccountInfo(Context ctx, String userName,
			String password, String securityToken, String type,
			boolean isKeepMeLogin) {
		try {
			// First check if the exist in db
			List<UserLoginInfo> userInfoList = getHelper(ctx)
					.getUserLoginInfoDao().queryForAll();
			UserLoginInfo uli = null;
			for (UserLoginInfo userinfo : userInfoList) {
				if (userinfo.getType().compareTo(type) == 0) {
					uli = userinfo;
					break;
				}
			}

			if (uli != null) {
				// duplicate found delete it
				getHelper(ctx).getUserLoginInfoDao().delete(uli);
			}

			// Here we will encrypt the password before saving it to db
			// add the item to the last
			String encryptedPassword = password;
			try {
				encryptedPassword = SimpleCrypto.encrypt(
						UIUtilities.getSeed(ctx), password);
			} catch (Exception e) {
				// ignoring the error here
			}

			getHelper(ctx).getUserLoginInfoDao().create(
					new UserLoginInfo(userName, encryptedPassword,
							securityToken, type, isKeepMeLogin));
		} catch (SQLException e) {
			// Log.e(TAG, e);
		} catch (Exception e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static void addNewUserAccountInfo(Context ctx,
			UserRegistrationInfo registrationInfo) {
		try {
			// First check if the exist in db
			List<UserRegistrationInfo> userInfoList = getHelper(ctx)
					.getUserRegistrationInfoDao().queryForAll();
			UserRegistrationInfo uli = null;
			for (UserRegistrationInfo userinfo : userInfoList) {
				if (userinfo.getUsername().compareTo(
						registrationInfo.getUsername()) == 0) {
					uli = userinfo;
					break;
				}
			}

			if (uli == null) {
				getHelper(ctx).getUserRegistrationInfoDao().create(
						registrationInfo);
			} else {
				// UIUtilities.showConfirmationAlert(ctx,
				// R.string.dialog_title_information,
				// R.string.user_already_exist, R.string.dialog_button_Ok,
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int which) {
				//
				// }
				// });
			}

		} catch (SQLException e) {
			// Log.e(TAG, e);
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static void addAlarm(Context ctx, AlarmData alarmData) {
		try {

			getHelper(ctx).getAlarmDataDao().create(alarmData);

		} catch (SQLException e) {
			// Log.e(TAG, e);
		} catch (Exception e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static void removeAlarm(Context ctx, AlarmData alarmData) {
		try {
			// First check if the exist in db
			List<AlarmData> alarmList = getHelper(ctx).getAlarmDataDao()
					.queryForAll();
			AlarmData ad = null;
			for (AlarmData alarm : alarmList) {
				if (alarm.getId() == alarmData.getId()) {
					ad = alarm;
					break;
				}
			}

			if (ad != null) {
				// duplicate found delete it
				getHelper(ctx).getAlarmDataDao().delete(ad);
			}
		} catch (SQLException e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static void editAlarm(Context ctx, AlarmData alarmData) {
		try {
			// First check if the exist in db
			List<AlarmData> alarmList = getHelper(ctx).getAlarmDataDao()
					.queryForAll();
			AlarmData ad = null;
			for (AlarmData alarm : alarmList) {
				if (alarm.getId() == alarmData.getId()) {
					ad = alarm;
					break;
				}
			}

			if (ad != null) {
				getHelper(ctx).getAlarmDataDao().update(alarmData);
			}
		} catch (SQLException e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static List<AlarmData> getAlarmList(Context ctx) {
		try {
			// First check if the exist in db
			List<AlarmData> alarmList = getHelper(ctx).getAlarmDataDao()
					.queryForAll();

			return alarmList;

		} catch (SQLException e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
		return null;

	}

	/**
	 * Removes the account.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param accountType
	 *            the account type
	 */
	public static void removeAccount(Context ctx, String accountType) {
		try {
			// First check if the exist in db
			List<UserLoginInfo> userInfoList = getHelper(ctx)
					.getUserLoginInfoDao().queryForAll();
			UserLoginInfo uli = null;
			for (UserLoginInfo userinfo : userInfoList) {
				if (userinfo.getType().compareTo(accountType) == 0) {
					uli = userinfo;
					break;
				}
			}

			if (uli != null) {
				// duplicate found delete it
				getHelper(ctx).getUserLoginInfoDao().delete(uli);
			}
		} catch (SQLException e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static void addDoctorAppAlarm(Context ctx, DoctroAlarmData alarmData) {
		try {

			getHelper(ctx).getDoctroAlarmDataInfoDao().create(alarmData);

		} catch (SQLException e) {
			// Log.e(TAG, e);
		} catch (Exception e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static void removeDoctorAppAlarm(Context ctx,
			DoctroAlarmData alarmData) {
		try {
			// First check if the exist in db
			List<DoctroAlarmData> alarmList = getHelper(ctx)
					.getDoctroAlarmDataInfoDao().queryForAll();
			DoctroAlarmData ad = null;
			for (DoctroAlarmData alarm : alarmList) {
				if (alarm.getId() == alarmData.getId()) {
					ad = alarm;
					break;
				}
			}

			if (ad != null) {
				// duplicate found delete it
				getHelper(ctx).getDoctroAlarmDataInfoDao().delete(ad);
			}
		} catch (SQLException e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
	}

	public static List<DoctroAlarmData> getDoctorAppAlarmList(Context ctx) {
		try {
			// First check if the exist in db
			List<DoctroAlarmData> alarmList = getHelper(ctx)
					.getDoctroAlarmDataInfoDao().queryForAll();

			return alarmList;

		} catch (SQLException e) {
			// Log.e(TAG, e);
		} finally {
			if (dbHelper != null) {
				OpenHelperManager.releaseHelper();
				dbHelper = null;
			}
		}
		return null;

	}

}