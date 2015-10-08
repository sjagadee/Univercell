/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

package app.nichepro.fragmenttab.shop;

import java.util.List;

import android.content.Context;
import android.view.ViewGroup;
import app.nichepro.model.ImageKeyValueResponseObject;
import app.nichepro.touchview.FileTouchImageView;

/**
 Class wraps file paths to adapter, then it instantiates {@link FileTouchImageView} objects to paging up through them.
 */
public class FilePagerAdapter extends BasePagerAdapter {
	
	public FilePagerAdapter(Context context, List<String> resources,List<ImageKeyValueResponseObject> images)
	{
		super(context, resources,images);
	}
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ((GalleryViewPager)container).mCurrentView = ((FileTouchImageView)object).getImageView();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position){
        final FileTouchImageView iv = new FileTouchImageView(mContext);
        iv.setUrl(mResources.get(position));
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        
        collection.addView(iv, 0);
        return iv;
    }

}
