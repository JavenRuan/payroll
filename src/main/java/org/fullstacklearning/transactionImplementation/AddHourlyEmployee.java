package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.AddEmployeeTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.payrolldomain.PaymentSchedule;
import org.fullstacklearning.payrollimplementation.HourlyClassification;
import org.fullstacklearning.payrollimplementation.WeeklySchedule;

public class AddHourlyEmployee extends AddEmployeeTransaction {
	private final double hourlyRate;

	public AddHourlyEmployee(int id, String name, String address,
			double hourlyRate, PayrollDatabase database) {
		super(id, name, address, database);
		this.hourlyRate = hourlyRate;

	}

	protected PaymentClassification MakeClassification() {
		return new HourlyClassification(hourlyRate);
	}

	protected PaymentSchedule MakeSchedule() {
		return new WeeklySchedule();
	}
}

