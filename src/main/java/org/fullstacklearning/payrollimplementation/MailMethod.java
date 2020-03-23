package org.fullstacklearning.payrollimplementation;

import org.fullstacklearning.payrolldomain.Paycheck;
import org.fullstacklearning.payrolldomain.PaymentMethod;

public class MailMethod implements PaymentMethod {
	private final String address;

	public MailMethod(String address) {
		this.address = address;
	}

	public void pay(Paycheck paycheck) {
		paycheck.setField("Disposition", "Mail");
	}

	public String ToString() {
		return String.format("mail (%s)", address);
	}

	public String getAddress() {
		return address;
	}

}

