/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.responsehandler;

import app.nichepro.util.EnumFactory.ResponseMesssagType;

public class ParserFactoryGenerator {
	public static BaseParser createParser(ResponseMesssagType msgType) {
		switch (msgType) {
		case ErrorResponseMessageType:
			return new ErrorParser();
		case GoogleLoginMessageType:
		case RegistrationMessageType:
		case LoginMessageType:
		case FBLoginMessageType:
			return new LoginParser();
		case ProductListMessageType:
			return new ProductListParser();
		case KeywordSearchMessageType:
			return new KeyWordSearchParser();
		case ProductDetailMessageType:
			return new ProductDetailParser();
		case CheckoutBillingAddressType:
			return new CheckoutBillingAddressParser();
		case CheckoutShippingAddressType:
			return new CheckoutBillingAddressParser();
		case CategoryMessageType:
			return new CategoryListParser();
		case CatalogMessageType:
			return new CatalogListParser();
		case CatalogCategoryMessageType:
			return new CatalogCategoryListParser();
		case OfferListMessageType:
			return new OfferListParser();
		case StoreLocatorListMessageType:
			return new StoreListParser();
		case RemoveInsuranceMessageType:
		case AddInsuranceMessageType:
		case AddItemMessageType:
		case ShowCartMessageType:
		case ModifyCartMessageType:
		case UpdateCartMessageType:
		case PromoCodeMessageType:
			return new CartParser();
		case GetAllStoresMessageType:
			return new GetAllStoresParser();
		case ServiceCentersListMessageType:
			return new SreviceCenterListParser();
		case LiveStoreLocatorListMessageType:
			return new LiveStoresListParser();
		case ForgotPasswordListMessageType:
		case BulkOrderMessageType:
		
			return new SuccessParser();
		case LogoutMessageType:
			return new LogOutParser();
		case PhotoCaptionMessageType:
			return new PhotoCaptionParser();
		case PushSyncMessageType:
			return new PushSyncParser();
		default:
			return new UnknownParser();
		}
	}
}
