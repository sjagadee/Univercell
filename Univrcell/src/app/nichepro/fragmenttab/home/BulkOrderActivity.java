package app.nichepro.fragmenttab.home;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import app.nichepro.activities.mcommerce.BaseDefaultActivity;
import app.nichepro.activities.mcommerce.R;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.Log;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class BulkOrderActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener {

	WebRequestTask bulkOrderRequestTask;
	private ResponseMesssagType msgType;

	private String[] stateCode;

	Button confirm;
	Spinner state, country;
	EditText phoneNumber, companyName, name, email, discription;
	String mName, mEmail, mPhoneNumber, mCompanyName, mState, mCountry,
			mDiscription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bulk_orders);

		confirm = (Button) findViewById(R.id.bConfirm);

		companyName = (EditText) findViewById(R.id.etCompanyName);
		name = (EditText) findViewById(R.id.etName);
		phoneNumber = (EditText) findViewById(R.id.etPhoneNumer);
		email = (EditText) findViewById(R.id.etEmailForBulk);
		discription = (EditText) findViewById(R.id.etDiscription);
		state = (Spinner) findViewById(R.id.sStateCode);
		country = (Spinner) findViewById(R.id.sCountryCode);

		setStateCode();

		confirm.setOnClickListener(this);
		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		imgLogo.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgLogo:
			setHomeScreenOnTop();

			break;
		case R.id.bConfirm:
			mName = name.getText().toString();
			mCompanyName = companyName.getText().toString();
			mState = stateCode[state.getSelectedItemPosition()];
			mCountry = country.getSelectedItem().toString();
			mEmail = email.getText().toString();
			mPhoneNumber = phoneNumber.getText().toString();
			mDiscription = discription.getText().toString();

			boolean emailCheck;
			emailCheck = UIUtilities.isEmailValid(mEmail);

			if (mName.matches("") || mCompanyName.matches("")
					|| mState.matches("") || mCountry.matches("")
					|| mEmail.matches("") || mPhoneNumber.matches("")
					|| mDiscription.matches("")) {
				UIUtilities.showConfirmationAlert(this,
						R.string.dialog_title_error, R.string.fill_all_details,
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
			} else {
				if (!emailCheck) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_email, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				} else if (mPhoneNumber.length() < 10
						|| mPhoneNumber.length() > 10) {
					UIUtilities.showConfirmationAlert(this,
							R.string.dialog_title_error,
							R.string.invalid_phone, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
				} else {
					initiateRegistrationRequest();
					clearAllTheDeatails();
				}

			}
			break;
		}

	}

	private void clearAllTheDeatails() {
		name.setText("");
		companyName.setText("");
		email.setText("");
		phoneNumber.setText("");
		discription.setText("");
		state.setSelection(0);

	}

	private void initiateRegistrationRequest() {
		bulkOrderRequestTask = new WebRequestTask(
				WebLinks.getLoginLink(WebLinks.BULK_ORDERS), this, this);

		bulkOrderRequestTask.AddParam(Constants.PARAM_NAME, mName);
		bulkOrderRequestTask.AddParam(Constants.PARAM_MOBILE_NO, mPhoneNumber);
		bulkOrderRequestTask.AddParam(Constants.PARAM_EMAIL_ID, mEmail);
		bulkOrderRequestTask.AddParam(Constants.PARAM_STATE_GEO_ID, mState);
		bulkOrderRequestTask.AddParam(Constants.PARAM_CUST_COUNTRY, mCountry);
		bulkOrderRequestTask.AddParam(Constants.PARAM_CUST_STATE, mState);
		bulkOrderRequestTask.AddParam(Constants.PARAM_COMPANY_NAME,
				mCompanyName);
		bulkOrderRequestTask
				.AddParam(Constants.PARAM_DISCRIPTION, mDiscription);

		bulkOrderRequestTask.AddParam(Constants.PARAM_MAIL_ADDRESS,
				"corporate@univercell.in");
		bulkOrderRequestTask.AddParam(Constants.PARAM_QUOTE_TYPE_ID,
				"PRODUCT_QUOTE");

		bulkOrderRequestTask.AddParam(Constants.PARAM_STATUS_ID, "QUO_CREATED");
		bulkOrderRequestTask.AddParam(Constants.PARAM_CURRENCY_UOMID, "INR");
		bulkOrderRequestTask.AddParam(Constants.PARAM_SALES_CHANNEL_ENUM_ID,
				"MOBILE_SALES_CHANNEL");

		bulkOrderRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.BulkOrderMessageType) {
			UIUtilities
					.showInformationWithOkButton(
							this,
							"Bulk Order Has Been Made Sucessfully, Our Corporate Sales Team Will Be In Touch In a Short While!!");

		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(this, ero.getErrorText());
			} else
				UIUtilities.showServerError(this);

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(this);
		} else {
			UIUtilities.showServerError(this);
		}

		Log.i("Response", "as");

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(this, ex.getMessage());
	}

	public void setStateCode() {
		this.stateCode = getResources()
				.getStringArray(R.array.state_array_code);
	}

}
