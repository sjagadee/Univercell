package app.nichepro.fragmenttab.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.util.UIUtilities;

public class MobileInsuranceActivity extends BaseDefaultActivity implements
		OnClickListener {

	private LinearLayout mMainLayout;
	private String mobInsuranceData[];
	private TextView link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mobile_insurance);
		mMainLayout = (LinearLayout) findViewById(R.id.mainLayout);
		link = (TextView) findViewById(R.id.link);
		
		link.setOnClickListener(this);
		setMobCode();
		techSupport();
		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setHomeScreenOnTop();
			}
		});
	}

	private void techSupport() {
		if (mobInsuranceData != null) {
			LayoutInflater factory = LayoutInflater.from(this);
			TableLayout tableLayout = (TableLayout) factory.inflate(
					R.layout.custom_tablelayout_cell, null);

			for (int i = 0; i < mobInsuranceData.length; i++) {
				String keyFeature = mobInsuranceData[i];

				TableRow tableRow = (TableRow) factory.inflate(
						R.layout.key_feature_cell_row, null);
				TextView key = (TextView) tableRow
						.findViewById(R.id.product_value);

				key.setText(keyFeature);
				tableLayout.addView(tableRow);
			}

			mMainLayout.addView(tableLayout);

		}
	}

	public void setMobCode() {
		this.mobInsuranceData = getResources().getStringArray(
				R.array.mobile_insurance_array);
	}

	@Override
	public void onClick(View v) {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_warning, R.string.open_browser,
				R.string.continue2, R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.univercell.in/control/insuranceterms"));
						startActivity(browserIntent);
					}
				}, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						// Canceled.
					}
				});
		
	}
}
