package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeClassificationTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.payrolldomain.PaymentSchedule;
import org.fullstacklearning.payrollimplementation.HourlyClassification;
import org.fullstacklearning.payrollimplementation.WeeklySchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
	private final double hourlyRate;

	public ChangeHourlyTransaction(int id, double hourlyRate,
			PayrollDatabase database) {
		super(id, database);
		this.hourlyRate = hourlyRate;
	}

	@Override
	protected PaymentClassification getClassification() {
		return new HourlyClassification(hourlyRate);
	}

	@Override
	protected PaymentSchedule getSchedule() {
		return new WeeklySchedule();
	}
}
