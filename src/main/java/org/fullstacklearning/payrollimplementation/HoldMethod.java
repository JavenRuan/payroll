package org.fullstacklearning.payrollimplementation;

import org.fullstacklearning.payrolldomain.Paycheck;
import org.fullstacklearning.payrolldomain.PaymentMethod;

public class HoldMethod implements PaymentMethod {
	
	public void pay(Paycheck paycheck) {
		paycheck.setField("Disposition", "Hold");
	}

	public String ToString() {
		return "hold";
	}

}
