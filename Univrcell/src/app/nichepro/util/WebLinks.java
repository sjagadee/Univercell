/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

public class WebLinks {

	// Option
	// 1) https
	private static String PROTOCOL = "http://";

	// 2) http

//	 private static String SERVER ="174.142.120.249/UnivercellAndroid/control/";
//	
//	 private static String LOGIN_SERVER = "174.142.120.249/control/";

//	public static String SERVER = "192.168.1.78:8080/control/";
//
//	public static String LOGIN_SERVER = "192.168.1.78:8080/control/";

//  private static String SERVER = "uni-app2.cloudapp.net/control/";

//  private static String LOGIN_SERVER = "uni-app2.cloudapp.net/control/";
	
	public static String SERVER = "www.univercell.in/control/";
	private static String LOGIN_SERVER = "www.univercell.in/control/";
	

	public static final String GET_CATALOG_DETAIL = "getCatalogDetail";
	public static final String GET_CATEGORY_DETAIL = "getCategoryDetail";
	public static final String GET_ALL_PRODUCT_FROM_CATALOG = "getAllProductFromCatalog";
	public static final String GET_PRODUCT_DETAIL = "getProductDetail";
	public static final String GET_CATALOG_CATEGORY = "getCatalogCategoryList";
	public static final String GET_KEYWORD_SEARCH = "androidKeywordSearch";
	public static final String GET_OFFERS = "getOffers";
	public static final String GET_ALL_SHOW_ROOMS = "getAllShowRooms";
	public static final String GET_ADD_ITEM = "androidAdditem";
	public static final String GET_SHOW_CART = "androidShowcart";
	public static final String GET_ALL_PAYMENT_METHOD = "getAvailablePaymentMethods";
	public static final String GET_ALL_STORES = "getAllStores";
	public static final String GET_MODIFIY_CART = "androidModifycart";
	public static final String GET_UPDATE_CART = "androidUpdatecart";
	public static final String GET_ALL_SERVICE_CENTERS = "getAllServiceCenters";
	public static final String GET_ALL_LIVE_SHOW_ROOMS = "getAllLiveShowRooms";
	public static final String ADD_PROMO_CODE = "androidAddPromoCode";
	public static final String ADD_ITEM_INSURANCE = "androidAddItemInsurance";
	public static final String REMOVE_ITEM_INSURANCE = "androidRemoveItemInsurance";

	public static final String CREATE_CUSTOMER = "androidCreatecustomer";
	public static final String FORGOT_PASSWORD = "androidForgotPassword";
	public static final String LOGIN = "androidlogin";
	public static final String LOGOUT = "androidlogout";
	public static final String PHOTO_CONTEST_CAPTION = "androidPhotoCaption";
	public static final String GET_PUSH_CONTACTS = "androidPushContacts";
	public static final String GET_PULL_CONTACTS = "androidPullContacts";
	public static final String LOGIN_FACEBOOK = "androidCreateFBUser";
	public static final String REGISTER_AT_INSTALLATION = "androidCreateCustomerOnInstall";
	public static final String LOGIN_GOOGLE = "androidCreateGoogleUser";
	public static final String BULK_ORDERS = "androidBulkOrder";
	public static final String BILL_ADDRESS = "androidCheckoutotionsForBillingAddress";
	public static final String SHIPPIN_ADDRESS = "androidCheckoutotionsForShippingAddress";
	public static final String REVIEW_ORDER_LOGIN = "androidReviewOrderlogin";

	
	
	public static String getLink(String service) {
		return String.format("%s%s%s", PROTOCOL, SERVER, service);
	};

	public static String getLoginLink(String service) {
		return String.format("%s%s%s", PROTOCOL, LOGIN_SERVER, service);
	};
}
