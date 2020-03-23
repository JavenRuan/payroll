package org.fullstacklearning.payrollimplementation;

import java.util.Calendar;
import java.util.Date;

import org.fullstacklearning.payrolldomain.PaymentSchedule;

public class BiWeeklySchedule implements PaymentSchedule {
	public boolean isPayDate(Date payDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(payDate);
		return ca.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
				&& ca.get(Calendar.DATE) % 2 == 0;
	}

	public Date GetPayPeriodStartDate(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, -13);
		return ca.getTime();
	}

	public String toString() {
		return "bi-weekly";
	}

}