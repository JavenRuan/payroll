package org.fullstacklearning.payrollimplementation;

import java.util.Date;

public class SalesReceipt {
	private final double saleAmount;
	private final Date date;

	public SalesReceipt(Date date, double saleAmount) {
		this.date = date;
		this.saleAmount = saleAmount;
	}

	public double getSaleAmount() {
		return saleAmount;
	}

	public Date getDate() {
		return date;
	}

}

