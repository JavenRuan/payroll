package org.fullstacklearning.payrollimplementation;

import java.util.Calendar;
import java.util.Date;

import org.fullstacklearning.payrolldomain.PaymentSchedule;

public class WeeklySchedule implements PaymentSchedule {
	@Override
	public String toString() {
		return "weekly";
	}

	public boolean isPayDate(Date payDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(payDate);
		return ca.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}

	public Date GetPayPeriodStartDate(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, -6);
		return ca.getTime();
	}

}
