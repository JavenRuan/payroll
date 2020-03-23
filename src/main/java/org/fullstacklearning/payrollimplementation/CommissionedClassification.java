package org.fullstacklearning.payrollimplementation;

import java.util.Date;
import java.util.Hashtable;

import org.fullstacklearning.payrolldomain.Paycheck;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.util.DateUtil;

public class CommissionedClassification extends PaymentClassification {
	private final double baseRate;
	private final double commissionRate;
	private Hashtable<Date, SalesReceipt> salesReceipts = new Hashtable<Date, SalesReceipt>();

	public CommissionedClassification(double baseRate, double commissionRate) {
		this.baseRate = baseRate;
		this.commissionRate = commissionRate;
	}

	public double getBaseRate() {
		return baseRate;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void AddSalesReceipt(SalesReceipt receipt) {
		salesReceipts.put(receipt.getDate(), receipt);
	}

	public SalesReceipt GetSalesReceipt(Date time) {
		return salesReceipts.get(time);
	}

	public double calculatePay(Paycheck paycheck) {
		double salesTotal = 0;
		for (SalesReceipt receipt : salesReceipts.values()) {
			if (DateUtil.IsInPayPeriod(receipt.getDate(),
					paycheck.getPayPeriodStartDate(),
					paycheck.getPayPeriodEndDate()))
				salesTotal += receipt.getSaleAmount();
		}
		return baseRate + (salesTotal * commissionRate * 0.01);
	}

	public String toString() {
		return String.format("%f + %f sales commission", baseRate,
				commissionRate);
	}
}
