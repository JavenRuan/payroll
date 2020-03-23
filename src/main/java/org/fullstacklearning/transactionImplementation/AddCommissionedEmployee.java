package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.AddEmployeeTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.payrolldomain.PaymentSchedule;
import org.fullstacklearning.payrollimplementation.BiWeeklySchedule;
import org.fullstacklearning.payrollimplementation.CommissionedClassification;

public class AddCommissionedEmployee extends AddEmployeeTransaction {
	private final double commissionRate;
	private final double baseRate;

	public AddCommissionedEmployee(int id, String name, String address,
			double baseRate, double commissionRate, PayrollDatabase database) {
		super(id, name, address, database);
		this.baseRate = baseRate;
		this.commissionRate = commissionRate;
	}

	protected PaymentClassification MakeClassification() {
		return new CommissionedClassification(baseRate, commissionRate);
	}

	protected PaymentSchedule MakeSchedule() {
		return new BiWeeklySchedule();
	}
}
