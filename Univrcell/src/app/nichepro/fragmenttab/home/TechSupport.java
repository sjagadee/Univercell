package app.nichepro.fragmenttab.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;

public class TechSupport extends BaseDefaultActivity {

	private LinearLayout mMainLayout;
	private String techSupportData[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tech_support);
		mMainLayout = (LinearLayout) findViewById(R.id.mainLayout);
		setTechCode();
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
		if (techSupportData != null) {
			LayoutInflater factory = LayoutInflater.from(this);
			TableLayout tableLayout = (TableLayout) factory.inflate(
					R.layout.custom_tablelayout_cell, null);

			for (int i = 0; i < techSupportData.length; i++) {
				String keyFeature = techSupportData[i];

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

	public void setTechCode() {
		this.techSupportData = getResources().getStringArray(
				R.array.tech_support_array);
	}

}
