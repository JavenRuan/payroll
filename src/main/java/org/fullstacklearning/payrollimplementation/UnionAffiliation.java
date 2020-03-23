package org.fullstacklearning.payrollimplementation;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.fullstacklearning.payrolldomain.Affiliation;
import org.fullstacklearning.payrolldomain.Paycheck;
import org.fullstacklearning.util.DateUtil;

public class UnionAffiliation implements Affiliation {
	private Hashtable<Date, ServiceCharge> charges = new Hashtable<Date, ServiceCharge>();
	private int memberId;
	private final double dues;

	public UnionAffiliation(int memberId, double dues) {
		this.memberId = memberId;
		this.dues = dues;
	}

	public UnionAffiliation() {
		this(-1, 0.0);
	}

	public ServiceCharge GetServiceCharge(Date time) {
		return charges.get(time);
	}

	public void AddServiceCharge(ServiceCharge sc) {
		charges.put(sc.getTime(), sc);
	}

	public double getDues() {
		return dues;
	}

	public int getMemberId() {
		return memberId;
	}

	public double calculateDeductions(Paycheck paycheck) {
		double totalDues = 0;

		int fridays = NumberOfFridaysInPayPeriod(
				paycheck.getPayPeriodStartDate(),
				paycheck.getPayPeriodEndDate());
		totalDues = dues * fridays;

		for (ServiceCharge charge : charges.values()) {
			if (DateUtil.IsInPayPeriod(charge.getTime(),
					paycheck.getPayPeriodStartDate(),
					paycheck.getPayPeriodEndDate()))
				totalDues += charge.getAmount();
		}

		return totalDues;
	}

	private int NumberOfFridaysInPayPeriod(Date payPeriodStart,
			Date payPeriodEnd) {
		Calendar dayCa = Calendar.getInstance();
		dayCa.setTime(payPeriodStart);
		Calendar payPeriodEndCa = Calendar.getInstance();
		dayCa.setTime(payPeriodEnd);

		int fridays = 0;
		while (dayCa.before(payPeriodEndCa)) {
			if (dayCa.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				fridays++;
			}
			dayCa.add(Calendar.DATE, 1);
		}

		return fridays;
	}
}

