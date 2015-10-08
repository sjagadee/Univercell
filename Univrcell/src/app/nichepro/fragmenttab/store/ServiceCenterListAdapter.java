/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.fragmenttab.store;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.model.ServiceCenterResponseObject;
import app.nichepro.util.UIUtilities;

public class ServiceCenterListAdapter extends
		ArrayAdapter<ServiceCenterResponseObject> {
	LayoutInflater minflater;
	Context mContext;
	static class ViewHolder {

		TextView location;
		TextView address;
		TextView mobile;
		TextView mobileName;
		Button call;

		public void reset() {

		}
	}

	public ServiceCenterListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.store_list_cell, null);
			holder = new ViewHolder();

			holder.location = ((TextView) convertView
					.findViewById(R.id.location));
			holder.address = ((TextView) convertView.findViewById(R.id.address));
			holder.mobile = ((TextView) convertView.findViewById(R.id.mobile));
			holder.mobileName = ((TextView) convertView
					.findViewById(R.id.mobileName));
			holder.call = ((Button) convertView.findViewById(R.id.bCall));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ServiceCenterResponseObject slro = getItem(position);

		holder.location.setText(slro.location);
		holder.address.setText(slro.address);

		if (slro.mobileNumber != null && !slro.mobileNumber.isEmpty()) {
			holder.mobile.setText(slro.mobileNumber);
		} else if (slro.phoneNumber != null && !slro.phoneNumber.isEmpty()) {
			holder.mobile.setText(slro.phoneNumber);
		} else {
			holder.mobileName.setVisibility(View.GONE);
			holder.mobile.setVisibility(View.GONE);
			holder.call.setVisibility(View.GONE);
		}
		
		holder.call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			if (UIUtilities.isPhoneAvailable(mContext)) {
				UIUtilities.showCallConfirmationAlert(mContext,
						holder.mobile.getText().toString(),
						R.string.calling, R.string.callingMessage,
						R.string.nobtn, R.string.yesbtn);

			} else
				
				{
					UIUtilities.showConfirmationAlert(mContext,
							R.string.dialog_title_information, "Your device don't have call facility!",
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {

								}
							});
				}
				
			}
		});

		return convertView;
	}
}