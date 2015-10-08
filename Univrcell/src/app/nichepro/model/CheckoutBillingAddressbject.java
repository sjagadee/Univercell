package app.nichepro.model;

import com.google.gson.annotations.SerializedName;

public class CheckoutBillingAddressbject extends BaseResponseObject{

	@SerializedName("partyId")
	public String partyId;
	
	@SerializedName("shipping_contact_mech_id")
	public String shipping_contact_mech_id;

	@SerializedName ("MSGTYPE")
	public String MSGTYPE;
	
	public String getMSGTYPE() {
		return MSGTYPE;
	}

	public void setMSGTYPE(String mSGTYPE) {
		MSGTYPE = mSGTYPE;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getShipping_contact_mech_id() {
		return shipping_contact_mech_id;
	}

	public void setShipping_contact_mech_id(String shipping_contact_mech_id) {
		this.shipping_contact_mech_id = shipping_contact_mech_id;
	}
	
}
