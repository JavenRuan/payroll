package org.fullstacklearning.payrolldomain;

import java.util.Date;

public interface PaymentSchedule {
	boolean isPayDate(Date payDate);

	Date GetPayPeriodStartDate(Date date);
}