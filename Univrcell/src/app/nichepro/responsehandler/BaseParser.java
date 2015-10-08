/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.responsehandler;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import app.nichepro.model.AllStoresListResponseObject;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.CartItemListResponseObject;
import app.nichepro.model.CatalogCategoryListResponseObject;
import app.nichepro.model.CatalogListResponseObject;
import app.nichepro.model.CategoryListResponseObject;
import app.nichepro.model.CheckoutBillingAddressbject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.model.OfferListResponseObject;
import app.nichepro.model.ProductDetailListResponseObject;
import app.nichepro.model.ProductListResponseObject;
import app.nichepro.model.ServiceCenterListResponseObject;
import app.nichepro.model.StoreLoactorListResponseObject;

import com.google.gson.Gson;

public interface BaseParser {
	ArrayList<BaseResponseObject> getParsedData(String data);
}

class UnknownParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		ErrorResponseObject ero = new ErrorResponseObject();
		ero.setErrorText("Server returned and error");
		items.add(ero);
		return items;
	}
}

class ErrorParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		ErrorResponseObject ero = new ErrorResponseObject();
		ero.setErrorText(data);
		items.add(ero);
		return items;
	}
}

class LoginParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		JSONObject json = null;

		try {
			json = new JSONObject(data);

			Gson gson = new Gson();
			LoginResponseObject clro = gson.fromJson(data,
					LoginResponseObject.class);
			items.add(clro);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

}

class RegistrationParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		JSONObject json = null;

		try {
			json = new JSONObject(data);

			if (json.has("userLogin")) {
				Gson gson = new Gson();
				LoginResponseObject clro = gson.fromJson(data,
						LoginResponseObject.class);
				items.add(clro);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

}

class ProductListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("productsList")) {
					Gson gson = new Gson();
					ProductListResponseObject plro = gson.fromJson(data,
							ProductListResponseObject.class);
					plro.isKeyWordSearch = 0;
					items.add(plro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class KeyWordSearchParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("productsList")) {
					Gson gson = new Gson();
					ProductListResponseObject plro = gson.fromJson(data,
							ProductListResponseObject.class);
					plro.isKeyWordSearch = 1;
					items.add(plro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class SuccessParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		BaseResponseObject bro = new BaseResponseObject();
		bro.setResult("success");

		items.add(bro);
		return items;
	}

}

class CategoryListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("response")) {
					Gson gson = new Gson();
					CategoryListResponseObject clro = gson.fromJson(data,
							CategoryListResponseObject.class);
					items.add(clro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class CatalogListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("response")) {
					Gson gson = new Gson();
					CatalogListResponseObject clro = gson.fromJson(data,
							CatalogListResponseObject.class);
					items.add(clro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class CatalogCategoryListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("catalogCategoryList")) {
					Gson gson = new Gson();
					CatalogCategoryListResponseObject clro = gson.fromJson(
							data, CatalogCategoryListResponseObject.class);
					items.add(clro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class ProductDetailParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("productAttributes") && json.has("productDetail")) {
					Gson gson = new Gson();
					ProductDetailListResponseObject clro = gson.fromJson(data,
							ProductDetailListResponseObject.class);
//					if (clro.productDetail != null && clro.productDetail.size() > 0) {
//						ProductResponseObject pro = clro.productDetail.get(0);
//						pro.isProductDetail = 1;
//					}
					items.add(clro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class OfferListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("offersList")) {
					Gson gson = new Gson();
					OfferListResponseObject plro = gson.fromJson(data,
							OfferListResponseObject.class);

					items.add(plro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}


class CheckoutBillingAddressParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				//if (json.has("showRoomsList")) {
					Gson gson = new Gson();
					CheckoutBillingAddressbject sllro = gson.fromJson(data,
							CheckoutBillingAddressbject.class);
					items.add(sllro);
				//}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class StoreListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("showRoomsList")) {
					Gson gson = new Gson();
					StoreLoactorListResponseObject sllro = gson.fromJson(data,
							StoreLoactorListResponseObject.class);
					items.add(sllro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class LiveStoresListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("liveShowRoomsList")) {
					Gson gson = new Gson();
					StoreLoactorListResponseObject sllro = gson.fromJson(data,
							StoreLoactorListResponseObject.class);
					items.add(sllro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class AddItemParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		BaseResponseObject ero = new BaseResponseObject();
		ero.setResult("Item Addedd");
		items.add(ero);
		return items;
	}
}

class GetAllStoresParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("productStoreList")) {
					Gson gson = new Gson();
					AllStoresListResponseObject sllro = gson.fromJson(data,
							AllStoresListResponseObject.class);
					items.add(sllro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class SreviceCenterListParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("serviceCenters")) {
					Gson gson = new Gson();
					ServiceCenterListResponseObject sclro = gson.fromJson(data,
							ServiceCenterListResponseObject.class);
					items.add(sclro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class CartParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();

		JSONObject json = null;

		try {
			json = new JSONObject(data);
			if (json != null) {
				if (json.has("cartItems")) {
					Gson gson = new Gson();
					CartItemListResponseObject sllro = gson.fromJson(data,
							CartItemListResponseObject.class);
					items.add(sllro);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
	}

}

class LogOutParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		BaseResponseObject ero = new BaseResponseObject();
		ero.setResult("Logout");
		items.add(ero);
		return items;
	}
}
class PhotoCaptionParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		BaseResponseObject ero = new BaseResponseObject();
		ero.setResult("Success");
		items.add(ero);
		return items;
	}
}
class PushSyncParser implements BaseParser {

	@Override
	public ArrayList<BaseResponseObject> getParsedData(String data) {
		// TODO Auto-generated method stub
		ArrayList<BaseResponseObject> items = new ArrayList<BaseResponseObject>();
		BaseResponseObject ero = new BaseResponseObject();
		ero.setResult("Success");
		items.add(ero);
		return items;
	}
}