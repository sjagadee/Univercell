/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.shop;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import app.nichepro.model.ImageKeyValueResponseObject;
import app.nichepro.touchview.UrlTouchImageView;

/**
 * Class wraps URLs to adapter, then it instantiates {@link UrlTouchImageView}
 * objects to paging up through them.
 */
public class UrlPagerAdapter extends BasePagerAdapter {

	ImageView mLeftImgView;
	ImageView mRightImgView;
	boolean isFromProductDetail;

	public UrlPagerAdapter(Context context, List<String> resources,
			List<ImageKeyValueResponseObject> images, ImageView left,
			ImageView right) {
		super(context, resources, images);
		this.mLeftImgView = left;
		this.mRightImgView = right;
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);

		if (object != null) {
			((GalleryViewPager) container).mCurrentView = ((UrlTouchImageView) object)
					.getImageView();
			if (((UrlTouchImageView) object).mProductImage != null) {
				mImages.get(position).prodImage = ((UrlTouchImageView) object).mProductImage;
			}

			if (mLeftImgView != null && mRightImgView != null) {

				int count = this.getCount();
				if (position >= 1 && count > 1) {
					mLeftImgView.setVisibility(View.VISIBLE);
				} else {
					mLeftImgView.setVisibility(View.GONE);
				}

				if (position + 1 < count) {
					mRightImgView.setVisibility(View.VISIBLE);
				} else {
					mRightImgView.setVisibility(View.GONE);
				}
				final GalleryViewPager gvp = ((GalleryViewPager) container);
				final int index = position;
				mLeftImgView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						gvp.setCurrentItem(index - 1);
					}
				});

				mRightImgView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						gvp.setCurrentItem(index + 1);
					}
				});

			}
		}
	}

	@Override
	public Object instantiateItem(ViewGroup collection, int position) {

		final UrlTouchImageView iv = new UrlTouchImageView(mContext,isFromProductDetail);

		ImageKeyValueResponseObject ikvro = mImages.get(position);
		if (ikvro.prodImage != null) {
			iv.setImage(ikvro.prodImage);
		} else
			iv.setUrl(mResources.get(position));

		iv.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		collection.addView(iv, 0);
		return iv;
	}
}
