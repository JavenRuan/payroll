package org.fullstacklearning.payrolldomain;

import java.util.Date;
import java.util.Hashtable;

public class Paycheck {
	private Date payDate;
	private final Date payPeriodStartDate;
	private double grossPay;
	private Hashtable<String, String> fields = new Hashtable<String, String>();
	private double deductions;
	private double netPay;

	public Paycheck(Date payPeriodStartDate, Date payDate) {
		this.payDate = payDate;
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public Date getPayPeriodEndDate() {
		return payDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public double getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(double grossPay) {
		this.grossPay = grossPay;
	}

	public void setField(String fieldName, String value) {
		fields.put(fieldName, value);
	}

	public String getField(String fieldName) {
		return fields.get(fieldName);
	}

	public double getDeductions() {
		return deductions;
	}

	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}

	public double getNetPay() {
		return netPay;
	}

	public void setNetPay(double netPay) {
		this.netPay = netPay;
	}

	public Date getPayPeriodStartDate() {
		return payPeriodStartDate;
	}

}

