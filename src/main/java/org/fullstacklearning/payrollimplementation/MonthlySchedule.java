package org.fullstacklearning.payrollimplementation;

import java.util.Calendar;
import java.util.Date;

import org.fullstacklearning.payrolldomain.PaymentSchedule;

public class MonthlySchedule implements PaymentSchedule {
	private boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	public boolean isPayDate(Date payDate) {
		return isLastDayOfMonth(payDate);
	}

	public Date GetPayPeriodStartDate(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int month = ca.get(Calendar.MONTH);
		while (ca.get(Calendar.MONTH) == month) {
			ca.add(Calendar.DATE, -1);
		}
		return ca.getTime();
	}

	public String toString() {
		return "monthly";
	}
}
