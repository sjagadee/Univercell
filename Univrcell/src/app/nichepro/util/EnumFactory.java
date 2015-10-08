/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

import java.util.HashMap;

/**
 * A factory for creating Enum objects.
 */
public class EnumFactory {

	/**
	 * The Enum ResponseMesssagType.
	 */
	public enum ResponseMesssagType {

		/** The Unknown response message type. */
		UnknownResponseMessageType,

		/** The login response message type. */
		LoginMessageType,

		/** The login response message type. */
		FBLoginMessageType,

		/** The product list response message type. */
		ProductListMessageType,

		RegistrationMessageType,

		/** The catalog response message type. */
		CatalogMessageType,

		/** The category response message type. */
		CategoryMessageType,

		/** The catalog category response message type. */
		CatalogCategoryMessageType,

		/** The product detail response message type. */
		ProductDetailMessageType,

		/** The product detail response message type. */
		KeywordSearchMessageType,

		/** The product detail response message type. */
		OfferListMessageType,

		StoreLocatorListMessageType,

		LiveStoreLocatorListMessageType,

		/** The product detail response message type. */
		AddItemMessageType,

		ServiceCentersListMessageType,

		ForgotPasswordListMessageType,

		/** The product detail response message type. */
		ShowCartMessageType,

		LogoutMessageType,

		/** The product detail response message type. */
		ModifyCartMessageType,

		PromoCodeMessageType,

		/** The product detail response message type. */
		UpdateCartMessageType, BulkOrderMessageType,

		/** The product detail response message type. */
		GetAllStoresMessageType, PhotoCaptionMessageType, PushSyncMessageType, AddInsuranceMessageType, RemoveInsuranceMessageType, GoogleLoginMessageType,
		/** The error response message type. */
		ErrorResponseMessageType,
		CheckoutBillingAddressType,
		CheckoutShippingAddressType;
		/**
		 * Gets the hash map.
		 * 
		 * @return the hash map
		 */
		public static HashMap<ResponseMesssagType, String> getHashMap() {
			HashMap<ResponseMesssagType, String> hm = new HashMap<ResponseMesssagType, String>();
			hm.put(UnknownResponseMessageType, "unknown");
			hm.put(LoginMessageType, "loggedInUser");
			hm.put(ProductListMessageType, "productlist");
			hm.put(CatalogMessageType, "catalog");
			hm.put(CategoryMessageType, "categorylist");
			hm.put(ProductDetailMessageType, "ProductDetail");
			hm.put(CheckoutBillingAddressType, "checkoutBillingAddress");
			hm.put(CheckoutShippingAddressType, "checkoutShippingAddress");
			hm.put(CatalogCategoryMessageType, "catalogCategoryList");
			hm.put(KeywordSearchMessageType, "keywordSearch");
			hm.put(OfferListMessageType, "offerProductsList");
			hm.put(StoreLocatorListMessageType, "showRoomList");
			hm.put(AddItemMessageType, "addItem");
			hm.put(ShowCartMessageType, "showCart");
			hm.put(GetAllStoresMessageType, "allStores");
			hm.put(ServiceCentersListMessageType, "serviceCenterList");
			hm.put(LiveStoreLocatorListMessageType, "liveShowRoomList");
			hm.put(ForgotPasswordListMessageType, "forgotPassword");
			hm.put(RegistrationMessageType, "registered");
			hm.put(LogoutMessageType, "logout");
			hm.put(ModifyCartMessageType, "modifycart");
			hm.put(UpdateCartMessageType, "updatecart");
			hm.put(FBLoginMessageType, "FBUserRegistered");
			hm.put(PhotoCaptionMessageType, "photoCaption");
			hm.put(PushSyncMessageType, "pushSync");
			hm.put(AddInsuranceMessageType, "addItemInsurance");
			hm.put(RemoveInsuranceMessageType, "removeItemInsurance");
			hm.put(GoogleLoginMessageType, "GoogleUserRegistered");
			hm.put(BulkOrderMessageType, "bulkOrder");
			hm.put(PromoCodeMessageType, "addPromoCode");

			return hm;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			HashMap<ResponseMesssagType, String> hm = getHashMap();
			return hm.get(this);
		}

		/**
		 * To enum.
		 * 
		 * @param text
		 *            the text
		 * @return the response header type
		 */
		public static ResponseMesssagType toEnum(String text) {
			HashMap<ResponseMesssagType, String> hm = getHashMap();
			for (ResponseMesssagType item : hm.keySet()) {
				if (hm.get(item).equals(text)) {
					return item;
				}
			}

			return UnknownResponseMessageType;
		}
	}

	public enum LoginType {
		Patient, Physician, Sponsor,
	}

	public enum SponsorshipStatus {
		Pending, Approve, Rejected,
	}

	public enum CommunicationTabs {
		Inbox, Compose, Outbox,
	}

	public enum SymTomType {
		Mild, Moderate, Severe,
	}

	public enum HospitalSearchType {

		GeoLocation(0), PostalCode(1), HospitalName(2), City(3), State(4);
		private int _value;

		HospitalSearchType(int Value) {
			this._value = Value;
		}

		public int getValue() {
			return _value;
		}
	}
}
