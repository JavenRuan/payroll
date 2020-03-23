package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeClassificationTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.payrolldomain.PaymentSchedule;
import org.fullstacklearning.payrollimplementation.MonthlySchedule;
import org.fullstacklearning.payrollimplementation.SalariedClassification;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {
	private final double salary;

	public ChangeSalariedTransaction(int id, double salary,
			PayrollDatabase database) {
		super(id, database);
		this.salary = salary;
	}

	@Override
	protected PaymentClassification getClassification() {
		return new SalariedClassification(salary);
	}

	@Override
	protected PaymentSchedule getSchedule() {
		return new MonthlySchedule();
	}
}
