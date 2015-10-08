/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.nichepro.model.OfferProductListResponseObject;
import app.nichepro.model.ProductResponseObject;
import app.nichepro.util.ImageLoader;
import app.nichepro.util.ImageLoaderHandler;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class HomeHorizontalScrollViewAdapter extends BaseAdapter {

	private OfferProductListResponseObject mOfferList;
	private Context mContext;
	UpdateUiFromAdapterListener mUpdateUi;
	ImageView leftImgView;
	ImageView rightImgView;
	boolean isBestDeal;

	public HomeHorizontalScrollViewAdapter(
			OfferProductListResponseObject offerList, Context context,
			UpdateUiFromAdapterListener update, ImageView left,
			ImageView right, boolean bestDeal) {
		// TODO Auto-generated constructor stub
		this.mOfferList = offerList;
		this.mContext = context;
		this.mUpdateUi = update;
		this.leftImgView = left;
		this.rightImgView = right;
		isBestDeal = bestDeal;

	}

	static class ViewHolder {

		ImageView imgView;
		ImageView offerImgView;
		TextView brandName;
		TextView ProductName;
		TextView price;
		TextView offerPrice;
		TextView offerPriceText;
		TextView soldOut;
		TextView priceSymbol;
		TextView offerPromo;
		RelativeLayout image_layout;

		public void reset() {
			imgView.setImageBitmap(null);
			imgView.setVisibility(View.GONE);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mOfferList.productsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		ProductResponseObject pro = mOfferList.productsList.get(arg0);
		mUpdateUi.updateUI(pro.productId);
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.offer_list_cell, null);

			holder = new ViewHolder();
			holder.image_layout = (RelativeLayout) convertView
					.findViewById(R.id.image_layout);

			holder.offerImgView = (ImageView) convertView
					.findViewById(R.id.offer_img);

			// holder.progressBar = (ProgressBar) convertView
			// .findViewById(R.id.progress_bar);
			holder.imgView = (ImageView) convertView.findViewById(R.id.img);
			holder.ProductName = ((TextView) convertView
					.findViewById(R.id.tvProductName));
			holder.brandName = ((TextView) convertView
					.findViewById(R.id.tvBrand));
			holder.price = ((TextView) convertView.findViewById(R.id.tvPrice));
			holder.priceSymbol = ((TextView) convertView
					.findViewById(R.id.tvPriceSymbol));

			holder.offerPriceText = ((TextView) convertView
					.findViewById(R.id.tvOfferText));

			holder.offerPromo = ((TextView) convertView
					.findViewById(R.id.tvOffer));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.reset();

		ProductResponseObject pro = mOfferList.productsList.get(position);
		if (pro.offerFlatAmount != null && pro.price != null
				&& UIUtilities.isIntNumber(pro.price)
				&& UIUtilities.isIntNumber(pro.offerFlatAmount)) {
			holder.offerImgView.setVisibility(View.VISIBLE);
			holder.offerPriceText.setVisibility(View.VISIBLE);
			holder.offerPriceText.setVisibility(View.VISIBLE);
			int p = UIUtilities.ScoreProcent(
					Integer.parseInt(pro.offerFlatAmount),
					Integer.parseInt(pro.price));
			if (p > 0) {
				if (p <= 10) {
					holder.offerImgView.setBackgroundResource(R.drawable.green_circle);
				}else if(p > 10 && p <= 30){
					holder.offerImgView.setBackgroundResource(R.drawable.yellow_circle);
				}else{
					holder.offerImgView.setBackgroundResource(R.drawable.red_circle);
				}
				String text = (UIUtilities.ScoreProcent(
						Integer.parseInt(pro.offerFlatAmount),
						Integer.parseInt(pro.price)))
						+ " %";
				holder.offerPromo.setText(text);
			} else {
				holder.offerPriceText.setVisibility(View.GONE);
				holder.offerPromo.setVisibility(View.GONE);
				holder.offerImgView.setVisibility(View.GONE);
			}
		} else {
			holder.offerPriceText.setVisibility(View.GONE);
			holder.offerPromo.setVisibility(View.GONE);
			holder.offerImgView.setVisibility(View.GONE);
		}

		int iPrice = 0;
		if (pro.price != null) {
			if (UIUtilities.isIntNumber(pro.price)) {
				iPrice = Integer.parseInt(pro.price);
			}
		}
		if (pro.image != null && pro.image.length() > 0) {
			pro.image = pro.image.replaceAll(" ", "%20");
		}

		holder.price.setVisibility(View.VISIBLE);
		holder.ProductName.setText(pro.productName);
		if (iPrice == 0) {
			holder.price.setVisibility(View.INVISIBLE);

		} else {
			holder.price.setTypeface(UIUtilities.createRupeeSymbol(mContext),
					Typeface.BOLD);
			holder.price.setText("`" + pro.price);
		}

		if (pro.prodImg == null) {

			ImageLoader.start(pro.image, position, ihandler);
		} else {
			holder.imgView.setVisibility(View.VISIBLE);
			// holder.progressBar.setVisibility(View.INVISIBLE);
			holder.imgView.setImageBitmap(pro.prodImg);
		}

		if (pro.priceAfterDiscount != null) {
			// holder.price.setTypeface(UIUtilities.createRupeeSymbol(mContext),
			// Typeface.BOLD);
			// holder.price.setPaintFlags(holder.price.getPaintFlags()
			// | Paint.STRIKE_THRU_TEXT_FLAG);
			holder.price.setVisibility(View.INVISIBLE);
		}

		holder.brandName.setText(pro.brandName);
		return convertView;

	}

	/** The ihandler. */
	private ImageLoaderHandler ihandler = new ImageLoaderHandler() {
		@Override
		protected boolean handleImageLoaded(Bitmap bitmap, Message msg,
				int rowIndex, int colIndex, String imageUrl,
				boolean noImageFound) {
			ProductResponseObject pro = mOfferList.productsList.get(rowIndex);
			pro.prodImg = bitmap;
			notifyDataSetChanged();
			return false;
		}
	};
}
