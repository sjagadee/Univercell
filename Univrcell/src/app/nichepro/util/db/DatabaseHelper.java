/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

package app.nichepro.util.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import app.nichepro.model.db.AlarmData;
import app.nichepro.model.db.DoctroAlarmData;
import app.nichepro.model.db.UserLoginInfo;
import app.nichepro.model.db.UserRegistrationInfo;
import app.nichepro.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application
	private static final String DATABASE_NAME = "HealthCare.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;

		
	/** the dao objec to access the user login info */
	private Dao<UserLoginInfo, Integer> userLoginInfoDao = null;
	
	/** the dao objec to access the user login info */
	private Dao<UserRegistrationInfo, Integer> userRegistrationInfoDao = null;
	private Dao<DoctroAlarmData, Integer> doctorAlarmDataInfoDao = null;
	private Dao<AlarmData, Integer> alarmDataDao = null;
	

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTableIfNotExists(connectionSource, UserLoginInfo.class);
			TableUtils.createTableIfNotExists(connectionSource, UserRegistrationInfo.class);
			TableUtils.createTableIfNotExists(connectionSource, DoctroAlarmData.class);
			TableUtils.createTableIfNotExists(connectionSource, AlarmData.class);

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database" + e.toString());
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			// Put commands to properly upgrade the tables
			TableUtils.dropTable(connectionSource, UserLoginInfo.class, true);
			TableUtils.dropTable(connectionSource, UserRegistrationInfo.class, true);
			TableUtils.dropTable(connectionSource, DoctroAlarmData.class, true);
			TableUtils.dropTable(connectionSource, AlarmData.class, true);


			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases" + e.toString());
			throw new RuntimeException(e);
		}
	}

	
	
	/**
	 * Retuns the Database Access Object (DAO) for our UserLoginInfo class. 
	 * it will create it or just give the cached value.
	 * @return
	 */
	public Dao<UserLoginInfo, Integer> getUserLoginInfoDao() throws SQLException {
		if (userLoginInfoDao == null){
			userLoginInfoDao = getDao(UserLoginInfo.class);
		}
		return userLoginInfoDao;
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		
	}

	public Dao<UserRegistrationInfo, Integer> getUserRegistrationInfoDao() throws SQLException{
		if (userRegistrationInfoDao == null){
			userRegistrationInfoDao = getDao(UserRegistrationInfo.class);
		}
		return userRegistrationInfoDao;
	}
	public Dao<DoctroAlarmData, Integer> getDoctroAlarmDataInfoDao() throws SQLException{
		if (doctorAlarmDataInfoDao == null){
			doctorAlarmDataInfoDao = getDao(DoctroAlarmData.class);
		}
		return doctorAlarmDataInfoDao;
	}



	public Dao<AlarmData, Integer> getAlarmDataDao() throws SQLException {
		if (alarmDataDao == null){
			alarmDataDao = getDao(AlarmData.class);
		}
		return alarmDataDao;
	}


}
