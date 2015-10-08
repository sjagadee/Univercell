/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

public class Constants {
	// XML Tag constants

	// Common
	/** The Constant TAG_msgtype. */
	public static final String TAG_msgtype = "msgtype";

	// autocomplete
	/** The Constant TAG_items. */
	public static final String TAG_items = "items";

	// links service
	/** The Constant TAG_links. */
	public static final String TAG_links = "links";

	// Sign up Response
	/** The Constant TAG_root. */
	public static final String TAG_root = "root";

	/** The Constant TAG_error. */
	public static final String TAG_error = "error";

	/** The Constant TAG_status. */
	public static final String TAG_status = "status";

	// Category Response
	/** The Constant TAG_item. */
	public static final String TAG_item = "item";

	/** The Constant TAG_name. */
	public static final String TAG_name = "name";

	/** The Constant Default_app_version. */
	public static final String Default_app_version = "1.0";

	// Defaults
	/** The Constant DEFAULT_emptystring. */
	public static final String DEFAULT_emptystring = "";

	/** The Constant SHAREDPREF_seed. */
	public static final String SHAREDPREF_seed = "seed";
	/** The Constant SHAREDPREF_seed. */
	public static final String SHAREDPREF_isSeedSet = "isseedset";

	public static final String TAG_seed = "seed";

	public static final String Empty_String = "";

	public static final String TAB_HOME = "tab_home_identifier";
	public static final String TAB_SHOP = "tab_shop_identifier";
	public static final String TAB_ACCOUNT = "tab_account_identifier";
	public static final String TAB_CART = "tab_cart_identifier";
	public static final String TAB_STORE_LOCATOR = "tab_store_locator_identifier";

	public static final String PARAM_CURRENT_CATALOG_ID = "CURRENT_CATALOG_ID";
	public static final String PARAM_PRODUCT_CAT_ID = "productCategoryId";
	public static final String PARAM_PRODUCT_STORE_ID = "productStoreId";
	public static final String PARAM_SESSION_ID = "jsessionid";

	public static final String PARAM_PRODUCT_ID = "productId";
	public static final String PARAM_PAGING = "PAGING";
	public static final String PARAM_SEARCH_STRING = "SEARCH_STRING";
	public static final String PARAM_SEARCH_BY = "searchBy";
	public static final String PARAM_VIEW_INDEX = "VIEW_INDEX";
	public static final String PARAM_VIEW_SIZE = "VIEW_SIZE";
	public static final String PARAM_SEARCH_CATALOG_ID = "SEARCH_CATALOG_ID";

	public static final String PARAM_CITY = "city";
	public static final String PARAM_ADD_PRODUCT_ID = "add_product_id";
	public static final String PARAM_QUANTITY = "quantity";
	public static final String PARAM_CLEAR_SEARCH = "clearSearch";
	public static final String PARAM_SELECTED_ITEM = "selectedItem";
	public static final String PARAM_UPDATE_CART = "update_";
	public static final String PARAM_REMOVE_SELECTED = "removeSelected";
	public static final String PARAM_PROMO_CODE_ID = "productPromoCodeId";

	public static final String PARAM_IS_FIRST_REQUEST = "isFirstRequest";
	public static final String PARAM_ITEM_INDEX = "itemIndex";
	public static final String PARAM_DATA_FILE = "datafile";
	public static final String PARAM_CAPTION = "caption";
	public static final String PARAM_NO_RECORDS = "noOfRecords";
	public static final String PARAM_FILE_EXTENSION = "img";
	public static final String VALUE_PATIENT = "PATIENT";
	public static final String VALUE_SMART = "SMART";
	public static final String VALUE_DOCTOR = "DOCTOR";
	public static final String VALUE_SPONSOR = "SPONSOR";
	public static final String VALUE_MEDICATION = "MEDICATION";
	public static final String VALUE_DIAGNOSIS = "DIAGNOSIS";
	public static final String VALUE_PROCEDURE = "PROCEDURE";
	public static final String VALUE_VITALSIGN = "VITALSIGN";
	public static final String VALUE_UPCOMING = "upcoming";
	public static final String VALUE_PAST = "past";
	public static final String VALUE_CANCEL = "cancel";
	public static final String VALUE_APPROVE = "approve";
	public static final String VALUE_PAGING_Y = "Y";
	public static final String VALUE_PAGING_N = "N";
	public static final String VALUE_TRUE = "true";
	public static final String VALUE_YES = "yes";
	public static final String VALUE_NO = "no";
	public static final String VALUE_FACEBOOK_USER = "AndroidFacebookUser";
	public static final String VALUE_GOOGLE_USER = "AndroidGmailUser";

	public static final String PARAM_USER_FIRST_NAME = "USER_FIRST_NAME";
	public static final String PARAM_CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
	public static final String PARAM_CUSTOMER_COUNTRY = "CUSTOMER_COUNTRY";
	public static final String PARAM_CUSTOMER_STATE1 = "CUSTOMER_STATE1";
	public static final String PARAM_CUSTOMER_CITY = "CUSTOMER_CITY";
	public static final String PARAM_CUSTOMER_ADDRESS1 = "CUSTOMER_ADDRESS1";
	public static final String PARAM_CUSTOMER_ADDRESS2 = "CUSTOMER_ADDRESS2";
	public static final String PARAM_CUSTOMER_MOBILE_CONTACT = "CUSTOMER_MOBILE_CONTACT";
	public static final String PARAM_CUSTOMER_POSTAL_CODE = "CUSTOMER_POSTAL_CODE";
	public static final String PARAM_PASSWORD = "PASSWORD";
	public static final String PARAM_CONFIRM_PASSWORD = "CONFIRM_PASSWORD";
	public static final String PARAM_USERNAME = "USERNAME";
	public static final String PARAM_EMAIL_PASSWORD = "EMAIL_PASSWORD";
	public static final String PARAM_EMAIL_PRODUCT_STORE_ID = "emailProductStoreId";
	public static final int SUCCESSFULLY_LOGIN_REQUESTED_CODE = 1;
	public static final int SUCCESSFULLY_LOGIN_REQUESTED_FOR_SYNC = 2;
	public static final String PARAM_FB_FIRST_NAME = "firstName";
	public static final String PARAM_FB_MIDDLE_NAME = "middletName";
	public static final String PARAM_FB_LAST_NAME = "lastName";
	public static final String PARAM_FB_GENDER = "gender";
	public static final String PARAM_FB_USER_LOGIN_ID = "userLoginId";
	public static final String PARAM_FB_FBUSERID = "FBUSERID";
	public static final String PARAM_USER_LOGIN_ID = "userLoginId";
	public static final String PARAM_FB_USER_TYPE = "userType";
	public static final String PARAM_GOOGLE_EMAIL = "email";
	public static final String PARAM_GOOGLE_FIRST_NAME = "firstname";
	public static final String PARAM_GOOGLE_LAST_NAME = "lastname";

	public static final String PARAM_GOOGLE_COUNTRY = "country";
	public static final String PARAM_GOOGLE_CITY = "City";
	public static final String PARAM_GOOGLE_ZIPCODE = "ZipCode";
	public static final String PARAM_GOOGLE_ADDRESS = "Address";
	public static final String PARAM_GOOGLE_PHONE = "Phone";

	public static final String KEY_IS_FIRST_TIME = "isFirstTime";
	public static final String VALUE_IS_FIRST_TIME = "true";
	public static final String NAME_KEY = "name";
	public static final String EMAIL_KEY = "email";
	public static final String FIRST_NAME_KEY = "given_name";
	public static final String LAST_NAME_KEY = "family_name";

	public static final String PARAM_STATUS_ID = "statusId";
	public static final String PARAM_CURRENCY_UOMID = "currencyUomId";
	public static final String PARAM_SALES_CHANNEL_ENUM_ID = "salesChannelEnumId";
	public static final String PARAM_QUOTE_TYPE_ID = "quoteTypeId";
	public static final String PARAM_MAIL_ADDRESS = "mailaddress";

	public static final String PARAM_NAME = "name";
	public static final String PARAM_EMAIL_ID = "emailId";
	public static final String PARAM_MOBILE_NO = "mobileno";
	public static final String PARAM_COMPANY_NAME = "quoteName";
	public static final String PARAM_CUST_COUNTRY = "customerCountry";
	public static final String PARAM_CUST_STATE = "customerState";
	public static final String PARAM_STATE_GEO_ID = "stateProvinceGeoId1";
	public static final String PARAM_DISCRIPTION = "description";

	public static final String PARAM_TONAME = "toName";
	public static final String PARAM_ADDRESS1 = "address1";
	public static final String PARAM_DIRECTIONS = "directions";
	public static final String PARAM_POSTALCODE = "postalCode";
	public static final String PARAM_STATE_GEOID = "stateProvinceGeoId";
	public static final String PARAM_COUNTRY_GEOID = "countryGeoId";
	public static final String PARAM_MOBILE = "mobile";
	public static final String PARAM_LANDLINE = "landline";
	public static final String PARAM_CONTACT_MECHID = "contactMechId";
	public static final String PARAM_CONTACT_MEC_PURPOSE_TYPE_ID = "contactMechPurposeTypeId";
	
	public static final String FACEBOOK_USER_ID = "fbid";
	public static final int CART_REFRESH = 10;

	
}
