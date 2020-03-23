package org.fullstacklearning.payrollimplementation;

import java.util.Date;

public class ServiceCharge {
	private final Date time;
	private final double amount;

	public ServiceCharge(Date time, double amount) {
		this.time = time;
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public double getAmount() {
		return amount;
	}

}