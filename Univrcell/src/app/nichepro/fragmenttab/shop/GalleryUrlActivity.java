/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.shop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.fragmenttab.shop.BasePagerAdapter.OnItemChangeListener;
import app.nichepro.model.ImageKeyValueResponseObject;
import app.nichepro.model.ProductResponseObject;

public class GalleryUrlActivity extends BaseDefaultActivity {

	private GalleryViewPager mViewPager;
	private List<ImageKeyValueResponseObject> mImageList;
	private ProductResponseObject mProduct;
	private int imageIndex;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageIndex = 0;
		setContentView(R.layout.zoom_view);
		ImageView leftImgView = (ImageView) findViewById(R.id.leftimg);
		ImageView rightImgView = (ImageView) findViewById(R.id.rightimg);
		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setHomeScreenOnTop();
			}
		});

		if (savedInstanceState == null) {
			Intent intent = getIntent();
			mProduct = intent.getParcelableExtra("image");
			imageIndex = intent.getIntExtra("index", 0);
			mImageList = new ArrayList<ImageKeyValueResponseObject>();
			for (int i = 0; i < mProduct.additionalImages.size(); i++) {
				ImageKeyValueResponseObject ikvro = new ImageKeyValueResponseObject();
				ikvro.link = mProduct.additionalImages.get(i);
				ikvro.prodImage = null;
				mImageList.add(ikvro);
			}
		} else {
			imageIndex = savedInstanceState.getInt("index");
			mProduct = savedInstanceState.getParcelable("product");
			mImageList = savedInstanceState.getParcelableArrayList("images");
		}

		for (int i = 0; i < mProduct.additionalImages.size(); i++) {
			mProduct.additionalImages.set(i, mProduct.additionalImages.get(i)
					.replaceAll(" ", "%20"));
		}

		UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this,
				mProduct.additionalImages, mImageList, leftImgView,
				rightImgView);

		pagerAdapter.setOnItemChangeListener(new OnItemChangeListener() {
			@Override
			public void onItemChange(int currentPosition) {
				// Toast.makeText(GalleryUrlActivity.this,
				// "Current item is " + currentPosition,
				// Toast.LENGTH_SHORT).show();
			}
		});

		mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
		if (leftImgView == null || rightImgView == null) {
			mViewPager.isFromProductDetail = true;
		}
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setCurrentItem(imageIndex);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("product", mProduct);
		outState.putParcelableArrayList("images",
				(ArrayList<? extends Parcelable>) mImageList);
		outState.putInt("index", imageIndex);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}

}