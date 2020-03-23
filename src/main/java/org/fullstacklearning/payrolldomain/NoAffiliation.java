package org.fullstacklearning.payrolldomain;

public class NoAffiliation implements Affiliation {

	public double calculateDeductions(Paycheck paycheck) {
		return 0;
	}
}

