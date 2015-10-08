/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.home;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.model.ProductListResponseObject;
import app.nichepro.model.ProductResponseObject;
import app.nichepro.util.ImageLoader;
import app.nichepro.util.ImageLoaderHandler;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class ProductListAdapter extends ArrayAdapter<ProductResponseObject> {
	LayoutInflater minflater;
	ProductListResponseObject mProductList;
	UpdateUiFromAdapterListener mUpdateUi;
	private ArrayList<ProductResponseObject> mProductArrayList;
	int isKeyWordSearch;
	Context mContext;

	static class ViewHolder {

		ProgressBar progressBar;
		ImageView imgView;
		TextView brandName;
		TextView ProductName;
		TextView price;
		TextView offerPrice;
		TextView offerP;
		TextView soldOut;
		TextView priceSymbol;
		Button bLoadMoreProducts;

		public void reset() {
			imgView.setImageBitmap(null);
			imgView.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);

		}
	}

	public ProductListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId, ProductListResponseObject productList,
			UpdateUiFromAdapterListener updateUi,
			ArrayList<ProductResponseObject> productArrayList, int isKeyWS) {
		super(context, textViewResourceId);
		minflater = LayoutInflater.from(context);
		mProductList = productList;
		mUpdateUi = updateUi;
		mProductArrayList = productArrayList;
		isKeyWordSearch = isKeyWS;
		mContext = context;
		ImageLoader.initialize(context);

	}

	/** The ihandler. */
	private ImageLoaderHandler ihandler = new ImageLoaderHandler() {
		@Override
		protected boolean handleImageLoaded(Bitmap bitmap, Message msg,
				int rowIndex, int colIndex, String imageUrl,
				boolean noImageFound) {
			ProductResponseObject pliro = getItem(rowIndex);

			if (pliro != null && pliro.image.compareTo(imageUrl) == 0) {
				pliro.prodImg = bitmap;
				notifyDataSetChanged();
			}
			return false;
		}
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.home_list_cell, null);
			holder = new ViewHolder();
			holder.progressBar = (ProgressBar) convertView
					.findViewById(R.id.progress_bar);
			holder.imgView = (ImageView) convertView.findViewById(R.id.img);
			holder.ProductName = ((TextView) convertView
					.findViewById(R.id.tvProductName));
			holder.brandName = ((TextView) convertView
					.findViewById(R.id.tvBrandName));
			holder.price = ((TextView) convertView.findViewById(R.id.tvPrice));
			holder.priceSymbol = ((TextView) convertView
					.findViewById(R.id.tvPriceSymbol));

			holder.offerPrice = ((TextView) convertView
					.findViewById(R.id.tvOfferPrice));
			holder.bLoadMoreProducts = (Button) convertView
					.findViewById(R.id.load_more_products);

			holder.offerP = ((TextView) convertView
					.findViewById(R.id.tvOfferSymbol));
			holder.soldOut = ((TextView) convertView
					.findViewById(R.id.tvSoldOut));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.reset();

		ProductResponseObject pro = getItem(position);
		if (isKeyWordSearch == 0) {
			int vIndex = 0;
			int hIndex = 0;
			if (mProductList.viewIndex != null
					&& mProductList.highIndex != null) {
				vIndex = Integer.parseInt(mProductList.viewIndex);
				hIndex = Integer.parseInt(mProductList.highIndex);
			}

			if ((mProductArrayList.size() - 1 == position) && (vIndex < hIndex)) {
				holder.bLoadMoreProducts.setVisibility(View.VISIBLE);

			} else {
				holder.bLoadMoreProducts.setVisibility(View.GONE);
			}

		} else {
			holder.bLoadMoreProducts.setVisibility(View.GONE);
		}

		holder.ProductName.setText(pro.productName);
		holder.brandName.setText(pro.brandName);

		if (pro.basePrice.contains(".")) {
			String priceList[] = pro.basePrice.split(".");
			if (priceList != null && priceList.length > 0) {
				int iPrice = Integer.parseInt(priceList[0]);
				if (iPrice > 0) {
					holder.priceSymbol.setVisibility(View.VISIBLE);

					holder.priceSymbol.setTypeface(UIUtilities
							.createRupeeSymbol(mContext));
					holder.priceSymbol.setText("`");
					holder.price.setText(pro.basePrice);
				} else {
					holder.price.setText("Not announced");
					holder.priceSymbol.setVisibility(View.GONE);
				}
			} else {
				holder.price.setText("Not announced");
			}

		} else {
			int iPrice = Integer.parseInt(pro.basePrice);
			if (iPrice > 0) {
				holder.priceSymbol.setVisibility(View.VISIBLE);

				holder.priceSymbol.setTypeface(UIUtilities
						.createRupeeSymbol(mContext));
				holder.priceSymbol.setText("`");
				holder.price.setText(pro.basePrice);
			} else {
				holder.price.setText("Not announced");
				holder.priceSymbol.setVisibility(View.GONE);
			}
		}

		if (pro.prodImg == null) {
			if (pro.image != null && pro.image.length() > 0) {
				pro.image = pro.image.replaceAll(" ", "%20");
			}
			ImageLoader.start(pro.image, position, ihandler);
		} else {
			holder.imgView.setImageBitmap(pro.prodImg);
			holder.imgView.setVisibility(View.VISIBLE);
			holder.progressBar.setVisibility(View.GONE);

		}
		if (pro.salesDiscontinuationDate != null) {
			checkProductAvail(pro.salesDiscontinuationDate, holder.soldOut);
		} else {
			holder.soldOut.setVisibility(View.GONE);
		}

		if (pro.priceAfterDiscount != null) {
			holder.price.setPaintFlags(holder.price.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			
			if (pro.priceAfterDiscount.contains(".")) {
				String priceList[] = pro.priceAfterDiscount.split(".");
				if (priceList != null && priceList.length > 0) {
					int iPrice = Integer.parseInt(priceList[0]);
					if (iPrice > 0) {
						holder.offerP.setVisibility(View.VISIBLE);
						holder.offerPrice.setVisibility(View.VISIBLE);
						holder.offerP.setTypeface(UIUtilities
								.createRupeeSymbol(mContext));
						holder.offerP.setText("`");
						holder.offerPrice.setText(pro.priceAfterDiscount);
					} else {
						holder.offerPrice.setText("Not announced");
						holder.offerP.setVisibility(View.GONE);
					}
				} else {
					holder.offerPrice.setText("Not announced");
				}

			} else {
				int iPrice = Integer.parseInt(pro.priceAfterDiscount);
				if (iPrice > 0) {
					holder.offerP.setVisibility(View.VISIBLE);
					holder.offerPrice.setVisibility(View.VISIBLE);
					holder.offerP.setTypeface(UIUtilities
							.createRupeeSymbol(mContext));
					holder.offerP.setText("`");
					holder.offerPrice.setText(pro.priceAfterDiscount);
				} else {
					holder.offerPrice.setText("Not announced");
					holder.offerP.setVisibility(View.GONE);
				}
			}

		} else {
			holder.offerP.setVisibility(View.GONE);
			holder.offerPrice.setVisibility(View.GONE);
			holder.price.setPaintFlags(holder.price.getPaintFlags()
					& ~Paint.STRIKE_THRU_TEXT_FLAG);
		}

		holder.bLoadMoreProducts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUpdateUi.updateUI(0);

			}
		});

		return convertView;
	}

	public void checkProductAvail(String date, TextView soldOut) {

		String dateList[] = date.split("T");
		String dd = null;
		String mm = null;
		String yy = null;
		if (dateList != null && dateList.length > 0) {
			String dList[] = dateList[0].split("-");
			if (dList != null && dList.length > 2) {
				mm = dList[0];
				dd = dList[1];
				yy = dList[2];
			}
		}
		Calendar setCal = Calendar.getInstance();
		Calendar curCal = Calendar.getInstance();

		if (mm != null && dd != null && yy != null
				&& UIUtilities.isIntNumber(mm) && UIUtilities.isIntNumber(dd)
				&& UIUtilities.isIntNumber(yy)) {
			setCal.set(Calendar.SECOND, 0);
			setCal.set(Calendar.MINUTE, 0);
			setCal.set(Calendar.HOUR_OF_DAY, 0);
			setCal.set(Calendar.MONTH, Integer.parseInt(mm) - 1);
			setCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));
			setCal.set(Calendar.YEAR, Integer.parseInt(yy));
			Log.i("Calender", "Week: " + setCal.get(Calendar.DAY_OF_WEEK));
			Log.i("Calender", "Year: " + setCal.get(Calendar.YEAR));
			Log.i("Calender", "Month: " + setCal.get(Calendar.MONTH));
			Log.i("Calender",
					"Day of Month: " + setCal.get(Calendar.DAY_OF_MONTH));
			Log.i("Calender",
					"Hour of Day: " + setCal.get(Calendar.HOUR_OF_DAY));
			Log.i("Calender", "Minut: " + setCal.get(Calendar.MINUTE));
			
			Log.i("Calender", "Week: " + setCal.get(Calendar.DAY_OF_WEEK));
			Log.i("Calender", "Year: " + setCal.get(Calendar.YEAR));
			Log.i("Calender", "Month: " + setCal.get(Calendar.MONTH));
			Log.i("Calender",
					"Day of Month: " + setCal.get(Calendar.DAY_OF_MONTH));
			Log.i("Calender",
					"Hour of Day: " + setCal.get(Calendar.HOUR_OF_DAY));
			Log.i("Calender", "Minute: " + setCal.get(Calendar.MINUTE));

			long smilis = setCal.getTimeInMillis();
			long cmilis = curCal.getTimeInMillis();

			if (smilis < cmilis) {
				soldOut.setVisibility(View.VISIBLE);
			} else {
				soldOut.setVisibility(View.GONE);
			}

			setCal.set(Calendar.DAY_OF_WEEK, curCal.get(Calendar.DAY_OF_WEEK));
			setCal.set(Calendar.YEAR, curCal.get(Calendar.YEAR));
			setCal.set(Calendar.MONTH, curCal.get(Calendar.MONTH));
			setCal.set(Calendar.DAY_OF_MONTH, curCal.get(Calendar.DAY_OF_MONTH));
			setCal.set(Calendar.HOUR_OF_DAY, curCal.get(Calendar.HOUR_OF_DAY));
			setCal.set(Calendar.MINUTE, curCal.get(Calendar.MINUTE));

			Log.i("Calender", "Week: " + setCal.get(Calendar.DAY_OF_WEEK));
			Log.i("Calender", "Year: " + setCal.get(Calendar.YEAR));
			Log.i("Calender", "Month: " + setCal.get(Calendar.MONTH));
			Log.i("Calender",
					"Day of Month: " + setCal.get(Calendar.DAY_OF_MONTH));
			Log.i("Calender",
					"Hour of Day: " + setCal.get(Calendar.HOUR_OF_DAY));
			Log.i("Calender", "Minute: " + setCal.get(Calendar.MINUTE));
		}
	}
}
