/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.cart;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.model.CartItemResponseObject;
import app.nichepro.util.ImageLoader;
import app.nichepro.util.ImageLoaderHandler;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class CartItemListAdapter extends ArrayAdapter<CartItemResponseObject> {
	LayoutInflater minflater;
	UpdateUiFromAdapterListener mUpdateUi;
	Context mContext;

	static class ViewHolder {

		private ProgressBar progressBar;

		public ProgressBar getProgressBar() {
			return progressBar;
		}

		public void setProgressBar(ProgressBar progressBar) {
			this.progressBar = progressBar;
		}

		public ImageView getImgView() {
			return imgView;
		}

		public void setImgView(ImageView imgView) {
			this.imgView = imgView;
		}

		public TextView getProductName() {
			return ProductName;
		}

		public void setProductName(TextView productName) {
			ProductName = productName;
		}

		public TextView getPrice() {
			return price;
		}

		public void setPrice(TextView price) {
			this.price = price;
		}

		public TextView getAdjustments() {
			return adjustments;
		}

		public void setAdjustments(TextView adjustments) {
			this.adjustments = adjustments;
		}

		public TextView getItemTotal() {
			return itemTotal;
		}

		public void setItemTotal(TextView itemTotal) {
			this.itemTotal = itemTotal;
		}

		public EditText getQty() {
			return qty;
		}

		public void setQty(EditText qty) {
			this.qty = qty;
		}

		public Button getBtnUpdate() {
			return btnUpdate;
		}

		public void setBtnUpdate(Button btnUpdate) {
			this.btnUpdate = btnUpdate;
		}

		public Button getBtnRemove() {
			return btnRemove;
		}

		public void setBtnRemove(Button btnRemove) {
			this.btnRemove = btnRemove;
		}

		private ImageView imgView;
		private TextView ProductName;
		private TextView price;
		private TextView adjustments;
		private TextView itemTotal;
		private EditText qty;
		private Button btnUpdate;
		private Button btnRemove;

		public ViewHolder(ProgressBar pBar, ImageView imgView, TextView pName,
				TextView price, TextView adjust, TextView itemTotal,
				EditText eqty, Button bUpdate, Button bRemove) {
			// TODO Auto-generated constructor stub
			this.progressBar = pBar;
			this.imgView = imgView;
			this.ProductName = pName;
			this.price = price;
			this.adjustments = adjust;
			this.itemTotal = itemTotal;
			this.qty = eqty;
			this.btnUpdate = bUpdate;
			this.btnRemove = bRemove;
		}

		public void reset() {

		}
	}

	public CartItemListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId, UpdateUiFromAdapterListener updateUi) {
		super(context, textViewResourceId);
		mContext = context;
		minflater = LayoutInflater.from(context);
		mUpdateUi = updateUi;
		ImageLoader.initialize(context);

	}

	/** The ihandler. */
	private ImageLoaderHandler ihandler = new ImageLoaderHandler() {
		@Override
		protected boolean handleImageLoaded(Bitmap bitmap, Message msg,
				int rowIndex,int colIndex, String imageUrl, boolean noImageFound) {
			CartItemResponseObject pliro = getItem(rowIndex);

			if (pliro != null && pliro.image.compareTo(imageUrl) == 0) {
				pliro.prodImg = bitmap;
				notifyDataSetChanged();
			}
			return false;
		}
	};

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ProgressBar pBar;
		ImageView imgView;
		TextView ProductName;
		TextView price;
		TextView adjustments;
		TextView itemTotal;
		EditText qty;
		Button btnUpdate;
		Button btnRemove;

		if (convertView == null) {
			convertView = minflater.inflate(R.layout.cart_item_cell, null);
			pBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
			imgView = (ImageView) convertView.findViewById(R.id.img);
			ProductName = ((TextView) convertView
					.findViewById(R.id.tvProductName));
			price = ((TextView) convertView.findViewById(R.id.tvPrice));
			adjustments = ((TextView) convertView
					.findViewById(R.id.adjustments));
			itemTotal = ((TextView) convertView.findViewById(R.id.itemTotal));
			qty = ((EditText) convertView.findViewById(R.id.qty));
			btnUpdate = ((Button) convertView.findViewById(R.id.btnUpdate));
			btnRemove = ((Button) convertView.findViewById(R.id.btnRemove));
			convertView.setTag(new ViewHolder(pBar, imgView, ProductName,
					price, adjustments, itemTotal, qty, btnUpdate, btnRemove));
			qty.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					v.requestFocusFromTouch();

					return false;
				}
			});
			qty.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					CartItemResponseObject pro = getItem(position);
					pro.quantity = s.toString();

				}
			});
			btnUpdate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int index = (Integer) v.getTag();
					View parent = (View) v.getParent();
					EditText q = (EditText) parent.findViewById(R.id.qty);
					mUpdateUi.updateUI(q.getText().toString(), index);

				}
			});

			btnRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int index = (Integer) v.getTag();
					mUpdateUi.updateUI(index);
				}
			});

		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			pBar = holder.getProgressBar();
			imgView = holder.getImgView();
			ProductName = holder.getProductName();
			price = holder.getPrice();
			adjustments = holder.getAdjustments();
			itemTotal = holder.getItemTotal();
			qty = holder.getQty();
			btnUpdate = holder.getBtnUpdate();
			btnRemove = holder.getBtnRemove();
		}

		btnUpdate.setTag(position);
		btnRemove.setTag(position);

		CartItemResponseObject pro = getItem(position);

		ProductName.setText(pro.productName);
		price.setText(pro.unitPrice);
		adjustments.setText(pro.adjustments);
		itemTotal.setText(pro.itemTotal);
		qty.setText(pro.quantity);

		if (pro.prodImg == null) {

			ImageLoader.start(pro.image, position, ihandler);
		} else {
			imgView.setImageBitmap(pro.prodImg);
			imgView.setVisibility(View.VISIBLE);
			pBar.setVisibility(View.GONE);
		}

		return convertView;
	}
}